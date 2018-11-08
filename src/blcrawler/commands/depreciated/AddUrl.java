package blcrawler.commands.depreciated;

import blcrawler.commands.templates.InstantCommand;
import blcrawler.model.ConsoleOutput;

/**
 * Depreciated command for URL processing. Should not be used
 * @author Joe Gallagher
 *
 */
@Deprecated
public class AddUrl extends InstantCommand
{
	/*
	 * Standard Fields
	 */
	private String url;
	int queueID;
	
	/**
	 * Constructor
	 * @param url the url to process
	 */
	public AddUrl(String url) 
	{
		isFinished = false;
		this.url=url;
	}

	@Override
	public void execute() 
	{
		sortUrl();
		isFinished = true;
		done();
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
