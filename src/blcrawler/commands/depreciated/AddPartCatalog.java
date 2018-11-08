package blcrawler.commands.depreciated;

import blcrawler.model.ConsoleOutput;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.PartCatalog;

/**
 * Create a depreciated partcatalog class, which accesses an individual page of the catalog 
 * menu. Do not use
 * @author Joe Gallagher
 *
 */
@Deprecated
public class AddPartCatalog implements Command {
	
	/*
	 * Standard Fields
	 */
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	int queueID;
	
	/*
	 * Particular fields
	 */
	private final String url;
	
	/**
	 * Constructor
	 * @param url The url of the catalog page being accessed
	 */
	public AddPartCatalog(String url) 
	{
		timeout=30;
		delay=20;
		isFinished=false;
		this.url=url;
	}

	@Override
	public void execute() 
	{
		//Check if the page has already been stored, if not, create a new PartCatalog which will
		//scrape the page
		if (ConsoleGUIModel.getPageManager().partCatalogFileMap.containsValue(url))
		{
			new ConsoleOutput("PageManager", "Partcatalog page of url "+url+" already stored.");
		
		}
		else
		{
			new PartCatalog(url);
			new ConsoleOutput("CommandResult", "Page of type Partcatalog at url=" +url+ " successfully accessed and recorded");
		}
		
		//Finish execution
		isFinished=true;
		done();
	}

	@Override
	public boolean executeImmediately() 
	{
		return false;
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

	@Override
	public int getTimeout() 
	{
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
		if (ConsoleGUIModel.getTaskTimer().queue.size() > 0)
		{
			new ConsoleOutput("CommandResult", "Addition of url " + url + " added to queue. " + 
					ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued, standby");
		}
	}

	@Override
	public void forceQuit() 
	{
		
	}
	
	@Override
	public void setQueueID(int id)
	{
		queueID=id;
	}

	@Override
	public void done() 
	{
			
	}
}
