package blcrawler.commands.individualcalls.scraping;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.Part;

/**
 * Scrapes a part from bricklink into an XML file containing the raw HTML code and timestamp
 * information. This is done by creating a "Part" class, which triggers this action in its
 * constructor.
 * @author Joe Gallagher
 *
 */
public class PartBLToRawDatabase implements Command {
	
	/*
	 * Standard fields
	 */
	private boolean isFinished;
	int queueID;
	
	/*
	 * Particular fields
	 */
	private final String value;	//The value to base the part on. Can be either a URL or part number
	
	/**
	 * Constructor
	 * @param value The part to be created. Either in the format of a direct bricklink link, or 
	 * the format "part_#####.xml". The URL format is considered deprecated and should not be used.
	 */
	public PartBLToRawDatabase(String value) 
	{
		isFinished=false;
		this.value=value;
	}

	@Override
	//TODO: Incorporate functionality of Part class directly here, instead of as a seperate class
	public void execute() 
	{
		//Create a Part class which then scrapes the part's page in the constructor
		new Part(value);
		System.out.println("Execution of part "+value);
		
		//Finish execution
		isFinished=true;
		done();	
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
		if (ConsoleGUIModel.getTaskTimer().queue.size() > 0)
		{
			//TODO: Update more fully formed print statem
			//new ConsoleOutput("CommandResult", "Addition of url " + value + " added to queue. " +
			//ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued, standby");
			System.out.println("Part "+value+" queued");
		}
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
		
	}
}
