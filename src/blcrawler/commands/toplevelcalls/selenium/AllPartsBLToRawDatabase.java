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

import blcrawler.commands.individualcalls.scraping.PartBLToRawDatabase;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;


/**
 * Top level command for part scraping from bricklink. Auto-generates commands for scraping
 * part html for all parts in database
 * TODO: Expand for sets, minifigs, etc.
 * TODO: Allow path to be set from within executable
 * @author Joe Gallagher
 *
 */
public class AllPartsBLToRawDatabase implements Command 
{
	/*
	 * Standard fields
	 */
	boolean isFinished;
	
	/*
	 * Particular fields
	 */
	String partID;					//The individual part being processed at the moment
	ArrayList<String> partIDs;		//List of all part ids
	
	//Probably depreciated fields
	int queueID;
	
	/**
	 * Constructor
	 */
	public AllPartsBLToRawDatabase() 
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
				File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/");
				partIDs = new ArrayList<>();
				
				/* For each file in the directory, perform completion check -- failed scrapes 
				 * can generate very small files, and empty files are generated from the downloaded
				 * bricklink catalog summary. Add parts with no file or incomplete file in the form
				 * "part_#####.xml", print statement and skip parts already scraped.
				 */
				for(File file: dir.listFiles()) 
				{
					//TODO: Make this a more comprehensive check -- read the last characters maybe?
					if (file.length()<99000)
					{

                        partID = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"));
                        partIDs.add(partID);
                        String partidNumeric = partID.substring(5, partID.indexOf(".xml"));
					    
					    
					    try
		                {
		                    Element part = new Element("part");
		                    Document subdoc = new Document();
		                    subdoc.setRootElement(part);
		    
		                    Element blid = new Element("blid");
		                    blid.setText(partidNumeric);
		    
		    
		    
		                    subdoc.getRootElement().addContent(blid);
		    
		                    // new XMLOutputter().output(doc, System.out);
		                    XMLOutputter xmlOutput = new XMLOutputter();
		    
		                    // display nice nice
		                    xmlOutput.setFormat(Format.getPrettyFormat());
		                    xmlOutput.output(subdoc, new FileWriter(file.getAbsolutePath()));
		    
		                    System.out.println("File overwritten for part "+partID);
		                }
		                catch (IOException e)
		                {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
					    
						
						
					}
					else
					{
						System.out.println(file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"))+"already scraped");
					}
				}
				
				//Randomize part order, so calls to site are random
				long seed = System.nanoTime();
				Collections.shuffle(partIDs, new Random(seed));
				
				/*
				 * For each part, create an addPart command.
				 * NOTE: AddPart does NOT directly contain the scrape command, and as a result, 
				 * the instant queue is the correct place for this even though it feels wrong
				 * TODO: Refactor this so that it's more straightforwards and intuitive
				 */
				for (int i=0; i<partIDs.size(); i++)
				{
					ConsoleGUIModel.getSelenium().addToInstant(new PartBLToRawDatabase(partIDs.get(i)));
					//System.out.println("Part of ID #"+partIDs.get(i)+" added to instantQueue");
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
