package blcrawler.model.selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import blcrawler.commands.templates.Command;
import blcrawler.commands.toplevelcalls.admin.CancelScrape;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.queue.DelayQueue;
import blcrawler.model.queue.InstantQueue;

public class SeleniumDistributor
{
	public Hashtable<Integer,DelayQueue> delayQueueMap;
	public LinkedList<DelayQueue> delayQueueList;
	public LinkedList<Integer> processIDs;
	public Hashtable<Integer, Integer>processIDtoModule;
	public int currentModuleCreationProcess;
	public InstantQueue consoleQueue;
	public int lastcount;
	public int queued;
	public boolean relaunchable;
	
	public SeleniumDistributor()
	{
		queued = 0;
		lastcount = 0;
		processIDs = new LinkedList<Integer>();
		processIDtoModule = new Hashtable<Integer, Integer>();
		currentModuleCreationProcess = 0;
		relaunchable=true;
		
		Thread thread = new Thread() {
			public void run() {
				while(true)
				{
					try {
						sleep(200);
						//scanProcesses();
					} catch (InterruptedException e) {
						System.out.println("ERR: Timer thread interrupted");
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
		delayQueueMap = new Hashtable<>();
		delayQueueList = new LinkedList<>();
		consoleQueue = new InstantQueue();


		
//		for (int i=0;i<10;i++)
//		{
//			currentModuleCreationProcess = 9152+i;
//			System.out.println("Created Selenium number "+(i+1));
//			delayQueueMap.put(9152+i, new DelayQueue(9152+i));
//			delayQueueList.add(delayQueueMap.get(9152+i));
//			updateProcessLinks();
//		}
	}
	


	public void createSeleniums(int count)
	{
		
		
		
		Thread thread = new Thread() {
			public void run() {
				
				System.out.println("count is "+count);
				for (int i=0; i<count ; i++)
				{
					System.out.println("Running creation loop, i is "+i);
					currentModuleCreationProcess = 9152+i;
					System.out.println("Created Selenium numberxor "+(i+1));
					
					delayQueueMap.put(9152+i, new DelayQueue(9152+i));
					delayQueueList.add(delayQueueMap.get(9152+i));

					System.out.println("ID to create is "+(9152+i));
					//delayQueueMap.
					try
					{
						Thread.sleep(500);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//updateProcessLinks();
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();	
	}
	
	//
	public String getURL(String url, int id)
	{
		delayQueueMap.get(id).getSelenium().gotoURL(url);
		String outputs = delayQueueMap.get(id).getSelenium().getHTML();
		
		if (outputs.contains("quota"))
		{
		    System.out.println("WARNING: BL QUOTA EXCEEDED");
            System.out.println("Error occured at module "+id);
            System.out.println("Error occured at url "+url);

            delayQueueMap.get(id).getSelenium().gotoURL("https://api.ipify.org/");
            String ipad = delayQueueMap.get(id).getSelenium().getHTML();
            String ipshort = ipad.substring(ipad.indexOf("<pre>")+5, ipad.indexOf("</pre>"));
            
            try {
                Files.write(Paths.get("C:/Users/Owner/Documents/BLCrawler/Catalog/log/ipblacklist.txt"), (ipshort+System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            
            
            
            System.out.println("Tor IP was "+ipad);
            System.out.println("Pausing scrape operation");
            try
            {
                Thread.sleep(120000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            outputs = getURL(url, id);
            //ConsoleGUIModel.getSelenium().addToInstant(new CancelScrape());
            
		}
		return outputs;
		
	}
	
	
	   //
    public String getURLWithCheck(String url, int id, String checkstring)
    {
        delayQueueMap.get(id).getSelenium().gotoURL(url);
        String outstring = delayQueueMap.get(id).getSelenium().getHTML();
        if (outstring.contains(checkstring)&& outstring.contains("social-link__follow"))
        {
            return outstring;
        }
        else
        {
            System.out.println("Checkstring is "+checkstring);
            System.out.println("String mismatch, recursing in module "+id);
            return getURLWithCheckRecursive(url, id, checkstring);
        }
    }
    
    //
 public String getURLWithCheckRecursive(String url, int id, String checkstring)
 {
     String outstring="";
     int i=0;
     int recurse=0;

     outstring = delayQueueMap.get(id).getSelenium().getHTML();
     while(!(outstring.contains(checkstring) && outstring.contains("social-link__follow")))
     {
         i++;
         if (i>=100000)
         {
             i=0;
             recurse++;
             outstring = delayQueueMap.get(id).getSelenium().getHTML();
             System.out.println("String mismatch, recursing in module "+id+"recursion: "+recurse);
         }
     }
     return outstring;
 }
	
	
	public void getURLHTTP(String url, int id, String path)
	{
		//Diagnostic
		System.out.println("id: "+id);
		Set<Integer> keys = delayQueueMap.keySet();
        for(Integer key: keys){
            System.out.println("valid key: "+key);
        }
        System.out.println("done printing keys");
        System.out.println("url: "+url);
        System.out.println("path: "+path);
        
		delayQueueMap.get(id).getSelenium().httpProxyHTML(url, path);
	}
	
	public void getImage(String url, int id, String path)
	{
		delayQueueMap.get(id).getSelenium().httpProxyImage(url, path);	
	}
	
	
	public void addToInstant(Command command)
	{
		consoleQueue.addToQueue(command);
	}
	
	public void removeProcessID(Integer id)
	{
		if (processIDs.remove(id))
		{
			processIDs.remove(id);
			System.out.println("removed ID "+id);
		}
		else
		{
			System.out.println("Failed to remove id "+id);
		}
		processIDtoModule.remove(id);
		
	}
	
	public void distributeToSmallestQueue(Command command)
	{
		int k=-1;
		DelayQueue out = null;
		//System.out.println("Delay Queue size: "+delayQueueList.size());
		for (int i=0; i<delayQueueList.size(); i++)
		{
			if (k<0||delayQueueList.get(i).getQueueSize()<k)
			{
				k=delayQueueList.get(i).getQueueSize();
				out = delayQueueList.get(i);
			}
		}
		out.add(command);
		command.setQueueID(out.getId());
		//System.out.println("Added command to queue #"+out.getId()+", queue size of "+out.getQueueSize());
	}
	
	public void addToSpecificQueueBeginning(Command command, int queueid)
	{
	    delayQueueList.get(queueid).addToBeginning(command);
	}
	
	public void updateProcessLinks()
	{
		for (int i=0; i<processIDs.size(); i++)
		{
			try
			{
				delayQueueMap.get(processIDtoModule.get(processIDs.get(i))).getSelenium().addProcessID(Integer.valueOf(processIDs.get(i)));
			}
			catch (Exception e)
			{
				System.out.println("Loop: "+i);
				e.printStackTrace();
			}
		}
	}
	
	public void relaunch(int module)
	{
	    relaunchable=false;
		currentModuleCreationProcess = module;
		for (int i=0; i<delayQueueList.size(); i++)
		{
			if (delayQueueList.get(i).getLimit()-delayQueueList.get(i).getCommandsExecuted()<35)//10 for part scraping
			{
				delayQueueList.get(i).setCommandsExecuted(delayQueueList.get(i).getCommandsExecuted()-
					9-ThreadLocalRandom.current().nextInt(25, 45));	//7, 3-7 for part scraping
			}
			if (delayQueueList.get(i).getCommandsExecuted()<0)
			{
				delayQueueList.get(i).setCommandsExecuted(0);
			}
		}
		delayQueueMap.get(module).setCommandsExecuted(0);
		try
		{
			delayQueueMap.get(module).getSelenium().relaunchTor();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateProcessLinks();
		relaunchable=true;
		
	}
	
	public boolean isRelaunchable()
    {
        return relaunchable;
    }



    public void setRelaunchable(boolean relaunchable)
    {
        this.relaunchable = relaunchable;
    }



    //Called on program exit
	public void killAllProcesses()
	{
		for (int i=0; i<processIDs.size();i++)
		{
			try
			{
				//String cmd = "taskkill /F /PID " + processIDs.get(i);
			    String cmd = "taskkill /IM firefox.exe /F";
				Runtime.getRuntime().exec(cmd);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				System.out.println("Error killing process "+processIDs.get(i)+". Operation failed");
			}
		}

	}
	

	public void executeAllQueues()
	{
		
		queued = 0;
		//new ConsoleOutput("SeleniumDistro", "Queue Step, executing Selenium Queues");
		for (int i = 0; i<delayQueueList.size(); i++)
		{
			delayQueueList.get(i).setExecute(true);
			queued = queued + delayQueueList.get(i).getQueueSize();
		}
		if (queued>0&&queued!=lastcount)
		{
			System.out.println(queued+" tasks remaining.");
		}
		lastcount = queued;
	}
	
	public void clearAllQueues()
	{
		for (DelayQueue queue : delayQueueList)
		{
			queue.empty();
		}
		queued = 0;
		ConsoleGUIModel.getDatabase().buildMasterXML();
		System.out.println("All Queues cleared, XML up to date");
		killAllProcesses();
		delayQueueMap.clear();
		delayQueueList.clear();
		processIDs.clear();
		processIDtoModule.clear();
		queued = 0;
		lastcount = 0;
		currentModuleCreationProcess = 0;
	}
	
	public void scanProcesses()
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
					if (!processIDs.contains(Integer.valueOf(pid)))
					{
			        	processIDs.add(Integer.valueOf(pid));
			        	System.out.println("Added process id #"+pid+" at module number "+ currentModuleCreationProcess);
			        	processIDtoModule.put(Integer.valueOf(pid), currentModuleCreationProcess);
			        	
			    		System.out.println("ProcessIDs size: "+processIDs.size());
			    		System.out.println("processIDstoModule size: "+processIDs.size());
			        	//processIDs.add(Integer.valueOf(pid));
						//queueMap.get(currentModuleCreationProcess).getSelenium().addProcessID(Integer.valueOf(pid));

											
					}
		        }
		    }
		    input.close();
		} catch (Exception err) {
		    err.printStackTrace();
		}
	}



	public int getQueued()
	{
		return queued;
	}



	public void setQueued(int queued)
	{
		this.queued = queued;
	}
	
	
	
	
}
