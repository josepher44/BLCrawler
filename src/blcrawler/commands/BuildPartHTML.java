package blcrawler.commands;

import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;

/**
 * Takes a single part's previously scraped HTML file, and creates a catalogPart within the master
 * database from this file. Previously hard-coded to 3004, general use case should work now. 
 * Class should mostly be used for testing purposes, BuildPartsFromHTML sweeps entire directory with
 * same functionality. 
 * @author Joe Gallagher
 *
 */
public class BuildPartHTML implements Command {

	/*
	 * Standard fields
	 */
	boolean isFinished;
	int queueID;
	
	/*
	 * Particular fields
	 */
	String partNumber;	//The part to be built
	
	public BuildPartHTML(String partnumber) 
	{
		isFinished = false;
		partNumber = partnumber;
	}
	
	@Override
	public void execute() 
	{
		//Why is this a thread? Anyways, Just calls the database's add part on a new catalogpart, 
		//built using HTML constructor
		//TODO: Should have checking for if the file exists
		Thread thread = new Thread() {
			public void run() 
			{
				String path = "C:/Users/Owner/Documents/BLCrawler/Catalog/HTML/part_"+partNumber+".html";
				ConsoleGUIModel.getDatabase().addCatalogPart(new CatalogPart(path));
				
				//Conclude command call
				done();
				isFinished = true;
			}
		};
		thread.setDaemon(true);
		thread.start();
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
		return true;
	}

	@Override
	public void queue() 
	{
		
	}

	@Override
	public void forceQuit() 
	{
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
	}

	@Override
	public void done() 
	{
		
	}
}
