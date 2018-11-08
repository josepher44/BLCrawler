package blcrawler.commands.toplevelcalls;

import java.util.ArrayList;

import blcrawler.commands.templates.InstantCommand;
import blcrawler.model.ConsoleGUIModel;

/**
 * Command to process individual part xml files with broken html contents in tags, and process
 * into standalone html files with all errors fixed. Functionality should be wrapped into scrape
 * process
 * @author Joe Gallagher
 *
 */
public class FixHTML extends InstantCommand 
{
	/*
	 * Particular fields
	 */
	String partID;			//The part currently being processed
	ArrayList<String> partIDs;		//The list of all parts to be processed, by id
	
	/**
	 * Constructor
	 */
	public FixHTML() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		//Call the database's fix HTML command
		ConsoleGUIModel.getDatabase().fixHTMLs();
		
		//Finalize execution
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
