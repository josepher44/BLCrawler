package blcrawler.api;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import blcrawler.model.ConsoleGUIModel;

/**
 * Test class for API actions. Class to be depreciated, functionality split
 * @author Joe Gallagher
 *
 */
public class APIModel
{
	/*
	 * Declare fields
	 */
	BLAuthSigner signer;	//Signer, contains methods for auth generation
	String consumerKey;		//All hashes from bricklink
	String consumerSecret;
	String tokenValue;
	String tokenSecret;
	String consumerSecretTwitter;	//Depreciated
	String tokenSecretTwitter;		//Depreciated
	int queueID;			//ID of selenium queue requests are run through
	
	/**
	 * Provides dummy methods for accessing API
	 * @param QueueID the command Queue the API call is performed within. 
	 * Used for IP-based rate limiting purposes
	 */
	public APIModel(int QueueID) 
	{
		/*
		 * All keys provided by bricklink
		 * URL: https://www.bricklink.com/v2/api/register_consumer.page
		 * Consumer key/secret are account based, Tokens are IP+account based
		 */
		consumerKey = "16AB07C5220C43768822A29EB745FDC1";
		consumerSecret = "F0FE04252666486CB1D954006CA580A3";
		tokenValue = "0FD7279A6760427681A6C37E2AC89768";
		tokenSecret = "986EE49534414CE684D53A59E07FDEEA";
		
		//Um, what was this for again? When did twitter get involved? 
		//Probably super depreciated
		consumerSecretTwitter = "MCD8BKwGdgPHvAuvgvz4EQpqDAtx89grbuNMRd7Eh98";
		tokenSecretTwitter = "J6zix3FfA9LofH0awS24M3HcBYXO5nI1iYe8EfBA";
		
		this.queueID = QueueID;	//QueueID set by constructor via command call
	}
	
	/*
	 * API call sequence pseudocode
	 * buildSigner() -- instantiate signer under signer
	 * set verb
	 * generate the base url from documentation
	 * add any parameters, also including them in the base url
	 * set the write path
	 * run callAPI on baseURL, writePath
	 */
	
	/**
	 * Returns a price guide for a given price guide. Works, 
	 * @param partnumber the part number to evaluate the price guide for
	 * 
	 */
	public void getPriceGuide(String partnumber)
	{
		buildSigner();
		signer.setVerb( "GET" );
		String baseURL = "https://api.bricklink.com/api/store/v1/items/part/"+partnumber+"/price?color_id=11&guide_type=sold";
//		signer.addParameter("type", "part");
//		signer.addParameter("no", partnumber);
		//TODO: change color and guide type to selectable parameters
		signer.addParameter("color_id", "11");
		signer.addParameter("guide_type", "sold");
		//TODO: Add versioning, so that new data doesn't overwrite old
		String writePath = "C:/Users/Owner/Documents/BLCrawler/Catalog/PriceGuides/Parts/part_"+partnumber+".txt";
		signer.setURL(baseURL);	
		callAPI(baseURL, writePath);
	}
	
	/**
	 * Returns the inventory of a multipart part with a color
	 * @param partnumber the part number being assessed
	 * @param colorid the color of the part being assessed
	 */
	public void getColoredItemInventory(String partnumber, int colorid)
	{
		buildSigner();
		signer.setVerb( "GET" );
		String baseURL = "https://api.bricklink.com/api/store/v1/items/part/"+partnumber+"/subsets?color_id="+colorid;
		signer.addParameter("color_id", Integer.toString(colorid));
		signer.setURL( baseURL );
		//TODO: Add versioning, so that new data doesn't overwrite old
		String writePath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Inventories/Parts/part_"+partnumber+".txt";
		callAPI(baseURL, writePath);
	}
	
	/**
	 * Returns the inventory of a multipart part
	 * @param partnumber the part number being assessed
	 */
	public void getItemInventory(String partnumber)
	{
		buildSigner();
		signer.setVerb( "GET" );
		String baseURL = "https://api.bricklink.com/api/store/v1/items/part/"+partnumber+"/subsets?break_minifigs=Y";
		signer.addParameter("break_minifigs", "Y");
		signer.setURL( baseURL );
		//TODO: add versioning, so that new data doesn't overwrite old
		String writePath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Inventories/Parts/part_"+partnumber+".txt";
		callAPI(baseURL, writePath);
	}
	
	/**
	 * Consolidates the keys under a single signer object
	 */
	public void buildSigner()
	{
		//Seriously, what's up with the twitter stuff?
		boolean twitter = false;
		
		
		if (twitter)
		{
			signer = new BLAuthSigner( consumerKey, consumerSecretTwitter );
			signer.setToken( tokenValue, tokenSecretTwitter );
		}
		else
		{
			signer = new BLAuthSigner( consumerKey, consumerSecret );
			signer.setToken( tokenValue, tokenSecret );
		}
	}
	
	/**
	 * Method containing generic call API functionality, which builds the 
	 * correct authentication URL
	 * @param baseURL URL to access as described in Bricklink API doc. NOT
	 * the same as used in authSigner
	 * @param path the path to write outputs to
	 */
	public void callAPI(String baseURL, String path)
	{
		Map<String, String> params = Collections.emptyMap();
		
		//read all authentication parameters to the params map
		try 
		{
			params = signer.getFinalOAuthParams();
		} 
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
		
		//Take each element of params, write to named variable
		String Signature = params.get("oauth_signature");
		String Nonce = params.get("oauth_nonce");
		String Version = params.get("oauth_version");
		String ConsumerKey = params.get("oauth_consumer_key");
		String SigMethod = params.get("oauth_signature_method");
		String Token = params.get("oauth_token");
		String Timestamp = params.get("oauth_timestamp");
		
		//Filter out + characters, can cause confusion with URL formatting
		if(Signature.contains("+"))
		{
			System.out.println("Suspected bad signature, "+Signature+", regen attempt");
			callAPI(baseURL, path);
		}
		else
		{
			//Assemble complete call URL from base plus auth parameters
			String fullURL = baseURL+
							"&Authorization=%7B%22oauth_signature%22%3A%22"+
							Signature+
							"%22%2C%22oauth_nonce%22%3A%22"+
							Nonce+
							"%22%2C%22oauth_version%22%3A%22"+
							Version+
							"%22%2C%22oauth_consumer_key%22%3A%22"+
							ConsumerKey+
							"%22%2C%22oauth_signature_method%22%3A%22"+
							SigMethod+
							"%22%2C%22oauth_token%22%3A%22"+
							Token+
							"%22%2C%22oauth_timestamp%22%3A%22"+
							Timestamp+
							"%22%7D";
			
			//Replace HTML special char codes
			fullURL = fullURL.replace("%22", "\"");
			fullURL = fullURL.replace("%2C", ",");
			fullURL = fullURL.replace("%3A", ":");
			fullURL = fullURL.replace("%7B", "{");
			fullURL = fullURL.replace("%7D", "}");				
			
			//Send the full URL to the selenium distributor, responds via
			//console and file writes
			ConsoleGUIModel.getSelenium().getURLHTTP(fullURL, queueID, path);
		}
	}
	
	/**
	 * Read HTML to a string, via direct HTTP connection. Test method only, 
	 * use getURLHTTP() in the selenium distributor for Tor masking
	 * @param urlToRead URL to read
	 * @return String containing the raw HTOM of the page
	 * @throws Exception
	 */
	public static String getHTML(String urlToRead) throws Exception 
	{
	StringBuilder result = new StringBuilder();
	URL url = new URL(urlToRead);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
	conn.setRequestMethod("GET");
	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String line;
	while ((line = rd.readLine()) != null) 
	{
	   result.append(line);
	}
	rd.close();
	return result.toString();
	}
}
