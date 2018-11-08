package blcrawler.commands.toplevelcalls.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import blcrawler.commands.individualcalls.scraping.InventoryBLToRawDatabase;
import blcrawler.commands.templates.Command;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

/**
 * Top level command to request the inventories of all parts which have them via the API. Generates
 * a series of InventoryBLToRawDatabase commands
 * TODO: Equivalent for colored parts, both top and sub level
 * @author Joe Gallagher
 *
 */
public class AllInventoriesBLToRawDatabase implements Command {

	/*
	 * Standard fields
	 */
	private boolean isFinished;
	int queueID;
	
	/*
	 * Particular fields
	 */
	String partID;		//The part ID currently being processed
	ArrayList<CatalogPart> parts;		//List of all parts with inventories
	private String basepath;		//The directory to save all outputs to
	
	/**
	 * Constructor
	 */
	public AllInventoriesBLToRawDatabase() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{	
		//Set directory to save outputs in
		//TODO: Configurable from within application
		basepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Inventories/Parts/";
		
		
		Thread scrapeInventoriesThread = new Thread() 
		{
			public void run() 
			{
				//Collect list of all parts which have inventory, from the master database
				parts = new ArrayList<>();
				for(CatalogPart part: ConsoleGUIModel.getDatabase().getCatalogParts()) 
				{
					if(part.getHasInventory())
					{
						parts.add(part);
					
					}
					
				}
				System.out.println("Added "+parts.size()+" parts to queue for inventory generation");
				
				//Shuffle request order
				long seed = System.nanoTime();
				Collections.shuffle(parts, new Random(seed));

				//Generate a InventoryBLToRawDatabase command for each unscraped part on list
				for (CatalogPart part : parts)
				{
					String fullpath = basepath+"part_"+part.getPartNumber()+".txt";
					File filepath = new File(fullpath);
					if(!filepath.exists())
					{
						ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new InventoryBLToRawDatabase(part.getPartNumber()));
						System.out.println("Part of ID #"+part.getPartNumber()+" added to Queue");
					}
					else			
					{
						System.out.println("Already scraped "+part.getPartNumber()+", file in folder");
					}
				}
				
				//Finish Execution
				isFinished = true;
				done();
			}
		};
		scrapeInventoriesThread.setDaemon(true);
		scrapeInventoriesThread.start();
	}

	@Override
	public boolean executeImmediately() 
	{
		return true;
	}

	@Override
	public boolean executeNext() 
	{
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
	public void forceQuit() 
	{
		
	}
	
	@Override
	public void setQueueID(int id)
	{
		this.queueID=id;	
	}

	@Override
	public void done() 
	{				
		new ConsoleOutput("CommandResult", "Generated get image commands for all parts in catalog");
	}
}
