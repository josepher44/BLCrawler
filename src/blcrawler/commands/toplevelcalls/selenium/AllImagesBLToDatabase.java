package blcrawler.commands.toplevelcalls.selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;

import blcrawler.commands.Command;
import blcrawler.commands.individualcalls.scraping.AddImage;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.primatives.ColorMap;

/**
 * Top level command for image scraping from bricklink. Auto-generates commands for scraping
 * images from all parts in the database
 * TODO: Expand for sets, minifigs, etc.
 * TODO: Allow path to be set from within executable
 * @author Joe Gallagher
 *
 */

public class AllImagesBLToDatabase implements Command 
{

	/*
	 * Standard fields
	 */
	boolean isFinished;
	
	/*
	 * Particular fields
	 */
	private ArrayList<String> urls;		//List of image urls to create commands for
	private Hashtable<String, String> pathMap; //Maps urls (key) to database paths
	private String basepath;	//The master directory all files are saved in
	private String path;		//Path for a specific part. Iterated repeatedly
	
	//probably depreciated fields
	//String partID;
	int queueID;
	
	/**
	 * Constructor
	 */
	public AllImagesBLToDatabase() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		//Define the directory used to save images
		//TODO: Settable via application
		basepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Images/";
		
		//TODO: Verify that this kind of action benefits from threading
		//Define thread
		Thread addImagesThread = new Thread() 
		{
			public void run() 
			{
				/*
				 * Initialize values. Colormap provides conversions between string and ID
				 */
				ColorMap colormap = ConsoleGUIModel.getDatabase().getColormap();
				urls = new ArrayList<>();
				pathMap = new Hashtable<>();
				
				/*
				 * Run on every part in the master database
				 */
				for(CatalogPart part: ConsoleGUIModel.getDatabase().getCatalogParts()) 
				{
					
					/*
					 * Run on every known color the part comes in
					 * TODO: Checking for cases where the menu does not represent all actual colors
					 */
					for(String color : part.getKnownColorsBLMenu())
					{
						/*
						 * Generate the url to the image, and the path it should be saved. Puts
						 * them into pathMap and urls
						 */
						path = basepath+part.getPartNumber() + "_" + color + ".png";
						urls.add(	"https://img.bricklink.com/ItemImage/PN/"+
									colormap.idFromName(color)+"/"+part.getPartNumber()+".png"
									);
						pathMap.put("https://img.bricklink.com/ItemImage/PN/"+colormap.idFromName(color)+"/"+part.getPartNumber()+".png",
								path);
					
					}
					
				}
				//Read out number of images to scrape
				System.out.println("Added "+urls.size()+" images to queue");
				
				//Randomize the order of the urls
				long seed = System.nanoTime();
				Collections.shuffle(urls, new Random(seed));
				
				//Reads the list of parts which do not have images into memory
				//TODO: WAY better memory management at this step, this is a heckin' massive string
				//TODO: Figure out a way to periodically update this
				String notfound = "";
				try
				{
					notfound = new String(Files.readAllBytes(Paths.get(basepath+"notfound.txt")));
				}
				catch (IOException e)	//Catch -- if notfound.txt isn't there
				{
					System.out.println("Exception thrown: notfound.txt is...um...not found. This "+
									   "file should contain a list of all urls, seperated by"+
									   "newlines, which do not have images on bricklink");
					e.printStackTrace();
				}

				int i = 1;
				int size = urls.size();
				//For each URL, create a scrape command spread through open seleniums
				for (String geturl : urls)
				{
					//Create file to write to
					File filepath = new File(pathMap.get(geturl));
					
					//TODO: There is no way that this is the most efficient way to do this
					//At the very least, should be by part number+color code, not full URL
					//Execution of this command is way slower than it should be, probable cause
					if (notfound.contains(geturl))	//If the part has no image according to file
					{
						System.out.println("(" + i + " of " + size + ") Already scraped "+geturl+","
								+ " image does not exist");
					}
					else if (!filepath.exists())//If the part needs to be scraped
					{
						System.out.println("(" + i + " of " + size + "Scraping " + geturl);
						ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new AddImage(geturl,
								pathMap.get(geturl)));
					}
					else						//If the part has already been successfully scraped	
					{
						//System.out.println("(" + i + " of " + size + ") Already scraped "+geturl+","
						//		+ " file in to folder");
					}
					i++;
				}
				
				//Conclude top level command call
				done();
				isFinished = true;
				
			}
		};
		
		//Launch thread
		addImagesThread.setDaemon(true);
		addImagesThread.start();		
	}

	@Override
	public boolean executeImmediately() 
	{
		return true;
	}

	@Override
	public boolean executeNext() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() 
	{
		return 0;
	}

	@Override
	public int getTimeout() 
	{
		return 0;
	}

	@Override
	public boolean isFinished() 
	{
		return isFinished;
	}

	@Override
	public void queue() 
	{
	}

	@Override
	public void forceQuit() {
		// TODO Auto-generated method stub
	}
	
	//TODO: Explicitly assign queue id for instant queue commands
	@Override
	public void setQueueID(int id)
	{
		this.queueID=id;
	}

	@Override
	public void done() 
	{
		new ConsoleOutput("CommandResult", "Generated get image commands for all parts in"
			+ " catalog");
	}
}
