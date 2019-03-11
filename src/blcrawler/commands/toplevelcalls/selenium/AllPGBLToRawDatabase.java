package blcrawler.commands.toplevelcalls.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import blcrawler.commands.PullPGFromSite;
import blcrawler.commands.individualcalls.scraping.PartBLToRawDatabase;
import blcrawler.commands.templates.Command;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;


/**
 * Top level command for price guide scraping from bricklink. Auto-generates commands for scraping
 * price guide html for all parts and valid color combinations in database
 * TODO: Expand for sets, minifigs, etc.
 * TODO: Allow path to be set from within executable
 * @author Joe Gallagher
 *
 */
public class AllPGBLToRawDatabase implements Command 
{
	/*
	 * Standard fields
	 */
	boolean isFinished;
	
	/*
	 * Particular fields
	 */
	String partID;					//The individual part being processed at the moment
	ArrayList<String> pseudonames;		//List of all part ids
	
	//Probably depreciated fields
	int queueID;
	
	/**
	 * Constructor
	 */
	public AllPGBLToRawDatabase() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		//Queue parts in a seperate thread
		Thread addAllPartsThread = new Thread() 
		{
			public void run() 
			{
				//Set directory and initialize fields
				//TODO: Settable via application
				pseudonames = new ArrayList<>();
				
				/* 
                 * For each catalogpart in the database, pull its valid colors, and add to a list
                 * of parts to be scraped
				 */
				
				for(CatalogPart part: ConsoleGUIModel.getDatabase().getCatalogParts()) 
				{
					for (Integer colorid : part.getKnownColorsComposite())
					{
		                String builtname = part.getPartNumber()+"~~"+part.getBricklinkInternalID()+"%%"+colorid;
		                pseudonames.add(builtname);
					}
				}
				
				//Randomize part order, so calls to site are random
				long seed = System.nanoTime();
				Collections.shuffle(pseudonames, new Random(seed));
				
				/*
				 * For each part, create an addPart command.
				 * NOTE: AddPart does NOT directly contain the scrape command, and as a result, 
				 * the instant queue is the correct place for this even though it feels wrong
				 * TODO: Refactor this so that it's more straightforwards and intuitive
				 */
				int i=0;
				System.out.println("Total parts to scrape: "+pseudonames.size());
				for (String root : pseudonames)
				{
					CatalogPart part = ConsoleGUIModel.getDatabase().getPart(root.substring(0,root.indexOf("~~")));
					int internalid = Integer.valueOf(root.substring(root.indexOf("~~")+2, root.indexOf("%%")));
					int colorid = Integer.valueOf(root.substring(root.indexOf("%%")+2));
					
					File f = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/PriceGuides/Parts/part_"+part.getPartNumber()+"_color_"+colorid+".txt");
					if (f.exists())
					{
	                    i--;
	                    if (i%100==0)
	                    {
					    System.out.println("part_"+part.getPartNumber()+"_color_"+colorid+".txt already exists. Actual total is "+(pseudonames.size()-i));
	                    }
	                }
					else
					{
				    
    				    ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new PullPGFromSite(internalid, colorid, part));
    					//System.out.println("Command built for part #"+part.getPartNumber()+", color "+ConsoleGUIModel.getDatabase().getColormap().nameFromID(colorid)+". "+i+" out of "+pseudonames.size());

					}
				}
				
				//Finish execution
				isFinished = true;
				done();
				
			}
		};
		
		addAllPartsThread.setDaemon(true);
		addAllPartsThread.start();
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
		// TODO Auto-generated method stub	
	}

	@Override
	public void forceQuit() 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setQueueID(int id)
	{
		this.queueID=id;
	}

	@Override
	public void done() 
	{
		new ConsoleOutput("CommandResult", "Generated add part commands for all parts in catalog");
	}

}
