package blcrawler.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import blcrawler.commands.addpage.AddPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;


/**
 * Top level command for image scraping from bricklink. Auto-generates commands for scraping
 * images from all parts in the database
 * TODO: Expand for sets, minifigs, etc.
 * TODO: Allow path to be set from within executable
 * @author Joe Gallagher
 *
 */
public class AddAllParts implements Command 
{
	/*
	 * Standard fields
	 */
	boolean isFinished;
	
	/*
	 * Particular fields
	 */
	String partID;
	ArrayList<String> partIDs;
	
	//Probably depreciated fields
	int queueID;
	
	/**
	 * Constructor
	 */
	public AddAllParts() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() {
		Thread thread = new Thread() {
			public void run() 
			{
				File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/");
				partIDs = new ArrayList<>();
				for(File file: dir.listFiles()) 
				{
					//Basic error checking -- failed scrapes can generate very small files
					//TODO: Make this a more comprehensive check -- read the last characters maybe?
					if (file.length()<50000)
					{
						partID = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"));
						partIDs.add(partID);
					}
					else
					{
						System.out.println("part_"+file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"))+"already scraped");
					}
					
					
				}
				
				long seed = System.nanoTime();
				Collections.shuffle(partIDs, new Random(seed));
				for (int i=0; i<partIDs.size(); i++)
				{
					ConsoleGUIModel.getSelenium().addToInstant(new AddPart(partIDs.get(i)));
					//System.out.println("Part of ID #"+partIDs.get(i)+" added to instantQueue");
				}
				
				new ConsoleOutput("CommandResult", "Generated add part commands for all parts in catalog");
				isFinished = true;
				
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		

		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceQuit() {
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

}
