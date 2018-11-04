package blcrawler.commands.depreciated;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;

/**
 * Depreciated command for URL processing. Should not be used
 * @author Joe Gallagher
 *
 */

public class AddUrl implements Command 
{
	
	private String url;
	int queueID;
	
	public AddUrl(String url) 
	{
		this.url=url;
	}

	@Override
	public void execute() 
	{
		sortUrl();
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
		return false;
	}

	@Override
	public void queue() 
	{
		
	}
	
	@Override
	public void forceQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQueueID(int id)
	{
		queueID=id;
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}
	
	//Returns the standard part of a bricklink part catalog page URL as a console output?
	public void sortUrl() 
	{
		String sortStringA = url.substring(0, 50);	//length of http://www.bricklink.com/browseList.asp?itemType=P
		new ConsoleOutput("URL Interpretation", sortStringA);
	}

}
