package blcrawler.model.selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
