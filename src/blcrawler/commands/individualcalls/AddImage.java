package blcrawler.commands.individualcalls;

import java.util.concurrent.ThreadLocalRandom;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleGUIModel;

/**
 * Low level command for adding an image to the database for a specific part, called via URL.
 * This command is typically called via AddAllImages instead of directly by the user
 * TODO: Expand for sets, minifigs, etc.
 * TODO: Create variant which takes a part number as an argument instead of a url, for manual use
 * @author Joe Gallagher
 *
 */
public class AddImage implements Command {


	/*
	 * Standard fields
	 */
	boolean isFinished;
	private long delay;
	private int timeout;
	private int queueID;
	
	/*
	 * Particular fields
	 */
	String url;				//The url being accessed
	private String path;	//The path the data will be saved to
	
	/**
	 * Constructor
	 * @param URL The URL the target image is located at
	 * @param Path The path the data will be saved to
	 */
	public AddImage(String URL, String Path) 
	{
		//Initialize values
		isFinished = false;
		this.url = URL;
		this.path = Path;
		
		//Initialize delay and timeout, with random added offset. Timeout occurs 3 seconds later
		//TODO: See timeout note below
		int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
		timeout = 15+randomNum;
		delay = 0+randomNum;
	}
	
	@Override
	public void execute() 
	{
		//Send data to the selenium distributor for scraping
		ConsoleGUIModel.getSelenium().getImage(url, queueID, path);
		//new ConsoleOutput("CommandResult", "Directed Selenium module to parse data for url "+url);
		
		//Finish execution
		isFinished = true;
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
		return delay;
	}

	/*TODO: Implement timeout behavior. Should retry command a few times, then shift itself to a
	 *different selenium queue and try a few more times, then give up, logging the error.
	 */
	@Override
	public int getTimeout() 
	{
		// TODO Auto-generated method stub
		return timeout;
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
	}
}
