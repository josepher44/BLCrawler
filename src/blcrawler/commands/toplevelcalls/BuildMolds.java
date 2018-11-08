package blcrawler.commands.toplevelcalls;

import blcrawler.commands.templates.InstantCommand;
import blcrawler.model.ConsoleGUIModel;

/**
 * Creates the master mold XML file from part number based inferences, using the part database
 * xml as a source. At the moment, contains no provisions for adding/preserving manually added
 * associations
 * @author Joe Gallagher
 *
 */
public class BuildMolds extends InstantCommand
{

	/**
	 * Constructor
	 */
	public BuildMolds() 
	{	
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		Thread thread = new Thread() 
		{
			public void run() 
			{
				//Call the database's build molds method
				ConsoleGUIModel.getDatabase().buildMoldXML();
			}
		};
		thread.setDaemon(true);
		thread.start();
		
		//Finish execution
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
