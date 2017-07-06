package blcrawler.model.selenium;

import java.io.BufferedReader;
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

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;


import blcrawler.model.ConsoleOutput;
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
	
	public SeleniumModel(String id) {
		//bufferedFirefoxIDs = new ArrayList<>();
		ownFirefoxIDs = new ArrayList<>();
		//otherFirefoxIDs = new ArrayList<>();
		this.id = Integer.parseInt(id);
		//populateExistingProcesses();
		controlport = Integer.parseInt(id)-100;
		socksport = Integer.parseInt(id);
		
		System.out.println("Profile name: "+id);
		
		
		
		try
		{
			System.setProperty("webdriver.gecko.driver", "C:/Users/Joseph/Desktop/Multicircuit Tors/Geckodriver/geckodriver.exe");

		    String cmd = "C:/Users/Joseph/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ";
		    Runtime.getRuntime().exec(cmd);
			//Runtime.getRuntime().exec("C:/Users/Joseph/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ");
			
			profile = new ProfilesIni();
			myprofile = profile.getProfile(id);

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
			myprofile.setPreference( "network.proxy.socks_port", id );
			myprofile.setPreference( "network.proxy.socks_remote_dns", true );
			
			//myprofile.setPreference( "permissions.default.image", 2 );
			
			
			driver = new FirefoxDriver(myprofile);
			driver.manage().window().setPosition(new Point(0+ThreadLocalRandom.current().nextInt(0,100),
					ThreadLocalRandom.current().nextInt(0, 100)));
			driver.manage().window().setSize(new Dimension(150+ThreadLocalRandom.current().nextInt(0, 300),
					150+ThreadLocalRandom.current().nextInt(0, 300)));
			
			//myprofile.set

			//wait(5000);
			//identifyOwnProcesses();
			

			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
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
	/*
	public void getProcessIDs() 
	{
		try {
		    String line;
		    String pid;
		    CharSequence firefox = "firefox.exe";
		    Process p = Runtime.getRuntime().exec
		    	    (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		    	//System.out.println(line);
		        if (line.contains(firefox))
		        {
		        	pid = line.substring(29,line.indexOf("Console", 0)-1);
		        	pid = pid.replaceAll("\\s+","");
					bufferedFirefoxIDs.add(Integer.valueOf(pid));
					System.out.println("Added process ID "+ pid+ "to buffer for selenium module "+socksport);
		        }
		    }
		    input.close();
		} catch (Exception err) {
		    err.printStackTrace();
		}
		
		
		
	}
	
	
	public void populateExistingProcesses()
	{
		getProcessIDs();
		otherFirefoxIDs = bufferedFirefoxIDs;
		for (int i=0; i<otherFirefoxIDs.size(); i++)
		{
			System.out.println(otherFirefoxIDs.get(i).toString());
		}
		bufferedFirefoxIDs.clear();
	}
	
	public void identifyOwnProcesses()
	{
		getProcessIDs();
		ownFirefoxIDs = bufferedFirefoxIDs;
		for (int i=0; i<ownFirefoxIDs.size(); i++)
		{
			if (otherFirefoxIDs.contains(ownFirefoxIDs.get(i)))
			{
				ownFirefoxIDs.remove(i);
				System.out.println("Process already running");
			}
			else
			{
				System.out.println("Added process id #"+String.valueOf(ownFirefoxIDs.get(i))+" as process of Selenium module" + socksport);
			}
			
		}
		System.out.println("Completed process ID for selenium module "+socksport);
		bufferedFirefoxIDs.clear();
	}
	
	
	*/
	
	public String getHTML() {
		return driver.getPageSource();
	}
	
	public void saveImage()
	{
		
	}
	
	public void httpProxyRequest(String url)
	{	
		InputStream in = null;
    	FileOutputStream out = null;
		SocketAddress addr = new InetSocketAddress("127.0.0.1", socksport);
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
		try
		{
			URL myURL = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection(proxy);
			
			print_https_cert(conn);
		    try 
		    {
		        in = conn.getInputStream();
		        out = new FileOutputStream("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/TestFolder/2780.png");
		        int c;
		        byte[] b = new byte[1024];
		        while ((c = in.read(b)) != -1)
		            out.write(b, 0, c);
		        System.out.println("file written");
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
		
//	    CloseableHttpClient httpclient = HttpClients.createDefault();
//	    try {
//	        HttpHost target = new HttpHost(url, 443, "https");
//	        HttpHost proxy = new HttpHost("127.0.0.1", socksport, "socks");
//
//	        RequestConfig config = RequestConfig.custom()
//	                .setProxy(proxy)
//	                .build();
//	        HttpGet request = new HttpGet("/");
//	        request.setConfig(config);
//
//	        System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);
//
//	        CloseableHttpResponse response = httpclient.execute(target, request);
//	        try {
//	            System.out.println("----------------------------------------");
//	            System.out.println(response.getStatusLine());
//	            EntityUtils.consume(response.getEntity());
//	        }
//			catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//	            try
//				{
//					response.close();
//				}
//				catch (IOException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        }
//	    }
//		catch (ClientProtocolException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		catch (IOException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} finally {
//	        try
//			{
//				httpclient.close();
//			}
//			catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
	}
	
	
//	DefaultHttpClient httpclient = new DefaultHttpClient();
//	try {
//	    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//
//	    HttpHost target = new HttpHost("www.google.com", 80, "http");
//	    HttpGet req = new HttpGet("/");
//
//	    System.out.println("executing request to " + target + " via " + proxy);
//	    HttpResponse rsp = httpclient.execute(target, req);
//	    ...
//	} finally {
//	    // When HttpClient instance is no longer needed,
//	    // shut down the connection manager to ensure
//	    // immediate deallocation of all system resources
//	    httpclient.getConnectionManager().shutdown();
//	}
	
	
	
	
	public void gotoURL(String url) {
		driver.get(url);
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
		Runtime.getRuntime().exec("C:/Users/Joseph/Desktop/Multicircuit Tors/"+controlport+"_"+socksport+"/Browser/firefox.exe /C START /MIN ");
		
		driver = new FirefoxDriver(myprofile);
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
