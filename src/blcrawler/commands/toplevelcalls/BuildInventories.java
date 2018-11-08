package blcrawler.commands.toplevelcalls;

import blcrawler.commands.abstractcommands.InstantCommand;
import blcrawler.model.ConsoleGUIModel;

/**
 * Class for building into memory the inventories of all parts which have them, from previously
 * scaped data via api
 * @author Joe Gallagher
 *
 */
public class BuildInventories extends InstantCommand
{
	/*
	 * Standard fields
	 */
	boolean isFinished;
	int queueID;
	
	/**
	 * Constructor
	 */
	public BuildInventories() 
	{	
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		System.out.println("reached execute");
		Thread thread = new Thread() 
		{
			public void run() 
			{
				//Call database method to build inventories
				ConsoleGUIModel.getDatabase().buildInventories();
			}
		};
		thread.setDaemon(true);
		thread.start();
		
		//Finish Execution
		isFinished = true;
		done();
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
	public void done() 
	{
		
	}
}
