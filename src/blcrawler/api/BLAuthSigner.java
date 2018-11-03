package blcrawler.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * Signature generation class for API requests. Derived from API documenation
 * @author Joe Gallagher
 *
 */
public class BLAuthSigner 
{
	private static final String	CHARSET			= "UTF-8";
	private static final String	HMAC_SHA1		= "HmacSHA1";
	private static final String	EMPTY_STRING	= "";
	private static final String	CARRIAGE_RETURN	= "\r\n";

	private String				signMethod		= "HMAC-SHA1";
	private String				version			= "1.0";
	private String				consumerKey;
	private String				consumerSecret;
	private String				tokenValue;
	private String				tokenSecret;

	private String				url;
	private String				verb;

	private Map<String, String>	oauthParameters;
	private Map<String, String>	queryParameters;

	private Timer				timer;

	/**
	 * BLAuthSigner: Constructor for class which generates a hashmap of 
	 * parameters used for authentication with the bricklink API
	 * @param consumerKey hashed consumer key code used for request
	 * @param consumerSecret hashed consumer secret code used for request
	 */
	public BLAuthSigner(String consumerKey, String consumerSecret) 
	{
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.oauthParameters = new HashMap<>();
		this.queryParameters = new HashMap<>();
		this.timer = new Timer();
	}

	/**
	 * Sets the token value and secret used for authentication. Called 
	 * external to this class. Token values are unique to static IP address, 
	 * and generated/provided by the bricklink system
	 * @param tokenValue the token value used for the request
	 * @param tokenSecret the token secret used for the request
	 */
	public void setToken( String tokenValue, String tokenSecret ) 
	{
		this.tokenValue = tokenValue;
		this.tokenSecret = tokenSecret;
	}
	
	/**
	 * Sets the base URL being accessed. URL is generated based on API doc 
	 * and includes a representation of additional request parameters
	 * @param url The url being used for the request with request parameters 
	 * attached
	 */
	public void setURL( String url ) 
	{
		this.url = url;
	}
	
	/**
	 * Sets the action being taken, either GET, PUT, or POST
	 * @param verb the verb of the current request
	 */
	public void setVerb( String verb ) 
	{
		this.verb = verb;
	}

	/**
	 * Attaches a request parameter to the request. This does not update 
	 * the URL, this must be done separately during the call to setURL in 
	 * accordance with the API documentation
	 * @param key The name of the parameter, given as "parameter name" in 
	 * the API documentation
	 * @param value The value being sent. Boolean values are represented as 
	 * "Y" and "N"
	 */
	public void addParameter( String key, String value ) 
	{
		queryParameters.put( key, value );
	}

	/**
	 * Produces a map of parameter names to parameter values, used to 
	 * populate the final request URL
	 * @return a map containing the signature, nonce, version, consumer key, 
	 * sig method, token, and timestamp, labeled accordingly
	 * @throws Exception if all values cannot be computed
	 */
	public Map<String, String> getFinalOAuthParams( ) throws Exception 
	{
		String signature = computeSignature();
		
		Map<String, String> params = new HashMap<>();
		params.putAll( oauthParameters );
		params.put( OAuthConstants.SIGNATURE, signature);

		return params;
	}
	
	/**
	 * Computes the unique request signature from all request parameters 
	 * Signature is a checksum, depends on all other values.
	 * @return The signature based on all other auth parameters
	 * @throws Exception if signature cannot be computed
	 */
	public String computeSignature( ) throws Exception 
	{
		addOAuthParameter( OAuthConstants.VERSION, version );
		addOAuthParameter( OAuthConstants.TIMESTAMP, getTimestampInSeconds() );
		addOAuthParameter( OAuthConstants.NONCE, getNonce() );
		addOAuthParameter( OAuthConstants.TOKEN, tokenValue );
		addOAuthParameter( OAuthConstants.CONSUMER_KEY, consumerKey );
		addOAuthParameter( OAuthConstants.SIGN_METHOD, signMethod );

		String baseString = getBaseString();
		String keyString = OAuthEncoder.encode( consumerSecret ) + '&' + OAuthEncoder.encode( tokenSecret );
		String signature = doSign( baseString, keyString );
		return signature;
	}
	
	/**
	 * Adds an authentication parameter to the class' hashtable
	 * @param key the ID of the parameter being added
	 * @param value the value of the parameter being added
	 */
	private void addOAuthParameter( String key, String value ) 
	{
		oauthParameters.put( key, value );
	}

	/**
	 * Computes the timestamp of the current request
	 * @return the timestamp, in seconds
	 */
	private String getTimestampInSeconds( ) 
	{
		Long ts = timer.getMilis();
		return String.valueOf( TimeUnit.MILLISECONDS.toSeconds( ts ) );
	}
	
	/**
	 * Generates the nonce, a random string value computed for each request
	 * @return the nonce string
	 */
	private String getNonce( ) 
	{
		Long ts = timer.getMilis();
		return String.valueOf( ts + Math.abs( timer.getRandomInteger() ) );
	}

	/**
	 * Generates the base string used to generate the signature. 
	 * Only for internal use
	 * @return
	 * @throws Exception
	 */
	private String getBaseString( ) throws Exception 
	{
		List<String> params = new ArrayList<>();

		for( Entry<String, String> entry : oauthParameters.entrySet() ) 
		{
			String param = OAuthEncoder.encode( entry.getKey() ).concat( "=" ).concat( entry.getValue() );
			params.add( param );
		}

		for( Entry<String, String> entry : queryParameters.entrySet() ) 
		{
			String param = OAuthEncoder.encode( entry.getKey() ).concat( "=" ).concat( entry.getValue() );
			params.add( param );
		}

		Collections.sort( params );

		StringBuilder builder = new StringBuilder();
		for( String param : params ) 
		{
			builder.append( '&' ).append( param );
		}

		String formUrlEncodedParams = OAuthEncoder.encode( builder.toString().substring( 1 ) );
		String sanitizedURL = OAuthEncoder.encode( url.replaceAll( "\\?.*", "" ).replace( "\\:\\d{4}", "" ) );
		
		return String.format( "%s&%s&%s", verb, sanitizedURL, formUrlEncodedParams );
	}
	
	/**
	 * Computes the signature for a complete base string
	 * @param toSign base string to be signed, generated by method 
	 * getBaseString
	 * @param keyString The concatenated consumer and token secrets, encoded 
	 * using the oauthencoder class. See computeSignature for full 
	 * implementation
	 * @return The signed output
	 * @throws Exception
	 */
	private String doSign( String toSign, String keyString ) throws Exception 
	{
		SecretKeySpec key = new SecretKeySpec( (keyString).getBytes( CHARSET ), HMAC_SHA1 );
		Mac mac = Mac.getInstance( HMAC_SHA1 );
		mac.init( key );
		byte[] bytes = mac.doFinal( toSign.getBytes( CHARSET ) );
		return bytesToBase64String( bytes ).replace( CARRIAGE_RETURN, EMPTY_STRING );
	}

	/**
	 * Converts raw binary data into a string format, used internally to doSign
	 * @param bytes the binary data to be converted
	 * @return String representation of the data
	 * @throws Exception
	 */
	private String bytesToBase64String( byte[] bytes ) throws Exception 
	{
		return new String( Base64.encodeBase64( bytes ), "UTF-8" );
	}

	/**
	 * Simple timer class used for RNG
	 * @author Joe Gallagher
	 *
	 */
	static class Timer 
	{
		private final Random	rand	= new Random();

		Long getMilis( ) 
		{
			return System.currentTimeMillis();
		}

		Integer getRandomInteger( ) 
		{
			return rand.nextInt();
		}
	}

	/**
	 * Encoder class which performs URL encoding where needed
	 * @author Joe Gallagher
	 *
	 */
	static class OAuthEncoder 
	{
		private static final Map<String, String>	ENCODING_RULES;

		static 
		{
			Map<String, String> rules = new HashMap<String, String>();
			rules.put( "*", "%2A" );
			rules.put( "+", "%20" );
			rules.put( "%7E", "~" );
			ENCODING_RULES = Collections.unmodifiableMap( rules );
		}

		public static String encode( String plain ) throws UnsupportedEncodingException 
		{
			String encoded = URLEncoder.encode( plain, CHARSET );

			for( Map.Entry<String, String> rule : ENCODING_RULES.entrySet() ) 
			{
				encoded = encoded.replaceAll( Pattern.quote( rule.getKey() ), rule.getValue() );
			}
			return encoded;
		}
	}
}
