package blcrawler.model.selenium;

import java.io.BufferedReader;
import org.apache.commons.*;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.*;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

//import org.apache.http.HttpHost;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
import org.apache.commons.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;


import blcrawler.model.ConsoleOutput;
import blcrawler.commands.residuals.Delay;
import blcrawler.model.ConsoleGUIModel;

public class SeleniumModel {
	private ProfilesIni profile;
	private FirefoxProfile myprofile;
	private WebDriver driver;
	private ArrayList<Integer> ownFirefoxIDs;
	//private ArrayList<Integer> otherFirefoxIDs;
	//private ArrayList<Integer> bufferedFirefoxIDs;
	private int id;
	private int socksport;
	private int controlport;
	private SocketAddress socket;
	private Proxy proxy;
	FirefoxOptions firefoxOptions;
	
	
	//String which the page being scraped is SUPPOSED to contain
	private String pageIdentifier;
	
	FileWriter fw;
	BufferedWriter bw;
	
	public SeleniumModel(String ID) {
		//bufferedFirefoxIDs = new ArrayList<>();
		ownFirefoxIDs = new ArrayList<>();
		//otherFirefoxIDs = new ArrayList<>();
		this.id = Integer.parseInt(ID);
		//populateExistingProcesses();
		controlport = Integer.parseInt(ID)-100;
		socksport = Integer.parseInt(ID);
		
		System.out.println("Profile name: "+ID);
		
		
		
		try
		{

			System.out.println("Started making selenium");
			System.setProperty("webdriver.gecko.driver", "C:/Users/Owner/Desktop/Multicircuit Tors/Geckodriver/geckodriver-v0.23.0-win64/geckodriver.exe");

		    String cmd = "C:/Users/Owner/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ";
		    Runtime.getRuntime().exec(cmd);
			//Runtime.getRuntime().exec("C:/Users/Joseph/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ");
			
			profile = new ProfilesIni();
			myprofile = profile.getProfile(ID);

			myprofile.setPreference( "places.history.enabled", false );
			myprofile.setPreference( "privacy.clearOnShutdown.offlineApps", true );
			myprofile.setPreference( "privacy.clearOnShutdown.passwords", true );
			myprofile.setPreference( "privacy.clearOnShutdown.siteSettings", true );
			myprofile.setPreference( "privacy.sanitize.sanitizeOnShutdown", true );
			myprofile.setPreference( "signon.rememberSignons", false );
			myprofile.setPreference( "network.cookie.lifetimePolicy", 2 );
			myprofile.setPreference( "network.dns.disablePrefetch", true );
			myprofile.setPreference( "network.http.sendRefererHeader", 0 );


			myprofile.setPreference( "network.proxy.type", 1 );
			myprofile.setPreference( "network.proxy.socks_version", 5 );
			myprofile.setPreference( "network.proxy.socks", "127.0.0.1" );
			myprofile.setPreference( "network.proxy.socks_port", ID );
			myprofile.setPreference( "network.proxy.socks_remote_dns", true );
			
			//myprofile.setPreference( "permissions.default.image", 2 );
			
	        firefoxOptions = new FirefoxOptions();
	        firefoxOptions.setProfile(myprofile);
			

			System.out.println("StartedFFWindow");
			driver = new FirefoxDriver(firefoxOptions);

			System.out.println("MadeFFWindow");
			driver.manage().window().setPosition(new Point(0+ThreadLocalRandom.current().nextInt(0,100),
					ThreadLocalRandom.current().nextInt(0, 100)));
			driver.manage().window().setSize(new Dimension(150+ThreadLocalRandom.current().nextInt(0, 300),
					150+ThreadLocalRandom.current().nextInt(0, 300)));
			
			//myprofile.set

			//wait(5000);
			//identifyOwnProcesses();

			System.out.println("Finished making selenium");
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		    System.out.println("Module creation failed for module #"+socksport);
			e.printStackTrace();
		}
		

		//ConsoleGUIModel.getGuiView().setIndeterminiteOff();	
	}
	
	public void wait(int millis)
	{
		Thread thread = new Thread() {
			public void run() {
				
				try {
					sleep(millis);
						
				} catch (InterruptedException e) {
						System.out.println("ERR: Timer thread interrupted");
						e.printStackTrace();
					
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		System.gc();
	}

	
	public String getHTML() {
		return driver.getPageSource();
	}
	
	public void saveImage()
	{
		
	}
	
	public void createProxy()
	{
		socket = new InetSocketAddress("127.0.0.1", socksport);
		proxy = new Proxy(Proxy.Type.SOCKS, socket);
	}
	
	public void httpProxyHTML(String url, String path)
	{	
		InputStream in = null;
    	FileOutputStream out = null;
    	createProxy();
		try
		{
			StringBuilder result = new StringBuilder();
			URL myURL = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection(proxy);
			//print_https_cert(conn);
		    try 
		    {
		        BufferedReader input = new BufferedReader(new InputStreamReader(
	                    conn.getInputStream()));
		        String line;
			      while ((line = input.readLine()) != null) {
			         result.append(line);
			      }

			      input.close();
			      if(result.toString().contains("BAD_OAUTH_REQUEST"))
			      {
			    	  System.out.println("Bad Oauth request at url "+url+", will retry later");
			    	  System.out.println(result.toString());
			      }
			      else
			      {
				      System.out.println("URL "+url+"generated successful response "+result.toString());
				      FileUtils.writeStringToFile(new File(path), result.toString());
			      }
		        //System.out.println("file written from url "+url);
		    } 
		    
		    finally 
		    {
		        if (in != null)
		            in.close();
		        if (out != null)
		            out.close();
		    }
			

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public void httpProxyImage(String url, String path)
	{	
		InputStream in = null;
    	FileOutputStream out = null;
    	createProxy();
		try
		{
			URL myURL = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection(proxy);
			//print_https_cert(conn);
		    try 
		    {
		        in = conn.getInputStream();
		        out = new FileOutputStream(path);
		        int c;
		        byte[] b = new byte[1024];
		        while ((c = in.read(b)) != -1)
		            out.write(b, 0, c);
		        System.out.println("file written from url "+url);
		    } 
		    
		    finally 
		    {
		        if (in != null)
		            in.close();
		        if (out != null)
		            out.close();
		    }
			
//	        BufferedReader in = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));
//			String inputLine;
//			while ((inputLine = in.readLine()) != null) 
//			System.out.println(inputLine);
//			in.close();
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("404: No file for url: "+url);

			try
			{
				File file = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Images/notfound.txt");

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// true = append file
				fw = new FileWriter(file.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);

				bw.write(System.lineSeparator()+url);
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


     }

   private void print_https_cert(HttpsURLConnection con)
   {
	
		    if(con!=null){
	
		      try {
	
			System.out.println("Response Code : " + con.getResponseCode());
			System.out.println("Cipher Suite : " + con.getCipherSuite());
			System.out.println("\n");
	
			Certificate[] certs = con.getServerCertificates();
			for(Certificate cert : certs){
			   System.out.println("Cert Type : " + cert.getType());
			   System.out.println("Cert Hash Code : " + cert.hashCode());
			   System.out.println("Cert Public Key Algorithm : "
		                                    + cert.getPublicKey().getAlgorithm());
			   System.out.println("Cert Public Key Format : "
		                                    + cert.getPublicKey().getFormat());
			   System.out.println("\n");
			}
	
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
	    }
   }
		

	
	public void gotoURL(String url) {
	    try {

	        driver.get(url);
	        driver.manage().deleteAllCookies();
	    }
	    catch( Exception e)
	    {
	        System.out.println("WARNING: Scrape exception thrown in module "+socksport
	                +", restarting module");
	        try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
	        
	        gotoURL(url);
	    }
		//new ConsoleOutput("Selenium: ", "Page title is: " + driver.getTitle());
	}
	
	public void relaunchTor() throws InterruptedException, IOException {
		for (int i=0; i<ownFirefoxIDs.size(); i++)
		{
			killProcess(ownFirefoxIDs.get(i));
			ConsoleGUIModel.getSelenium().removeProcessID(ownFirefoxIDs.get(i));
		}
		ownFirefoxIDs.clear();
		Thread.sleep(1000);
		Runtime.getRuntime().exec("C:/Users/Owner/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ");
		
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().setPosition(new Point(0+ThreadLocalRandom.current().nextInt(0,100),
				ThreadLocalRandom.current().nextInt(0, 100)));
		driver.manage().window().setSize(new Dimension(150+ThreadLocalRandom.current().nextInt(0, 300),
				150+ThreadLocalRandom.current().nextInt(0, 300)));
	}
	
	public void addProcessID(int id)
	{
		if (!ownFirefoxIDs.contains(id))
		{
			ownFirefoxIDs.add(id);
			System.out.println("Selenium Module # "+socksport+" associated with process ID # "+id);
		}
	}
	
	public void killProcess(int processID)
	{
		try
		{
			String cmd = "taskkill /F /PID " + processID;
			Runtime.getRuntime().exec(cmd);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("Error killing process "+processID+". Operation failed");
		}
	}
	
	

}
