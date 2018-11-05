package blcrawler.commands.depreciated;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.PartBrowse;

/**
 * Create a depreciated partbrowse class, which accesses an individual page of the browse parts
 * for sale menu. Do not use
 * @author Joe Gallagher
 *
 */
@Deprecated
public class AddPartBrowse implements Command 
{
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
	 * @param url The url of the part browse page being accessed
	 */
	public AddPartBrowse(String url) 
	{
		timeout=30;
		delay=20;
		isFinished=false;
		this.url=url;
	}

	@Override
	public void execute() 
	{
		//Check if the page has already been stored, if not, create a new PartBrowse which will
		//scrape the page
		if (ConsoleGUIModel.getPageManager().partBrowseFileMap.containsValue(url))
		{
			new ConsoleOutput("PageManager", "Partbrowse page of url "+url+" already stored.");
		
		}
		else
		{
			new PartBrowse(url);
			new ConsoleOutput("CommandResult", "Page of type PartBrowse at url=" +url+ " successfully accessed and recorded");
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
