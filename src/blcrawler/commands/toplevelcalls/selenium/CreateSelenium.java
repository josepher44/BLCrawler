package blcrawler.commands.toplevelcalls.selenium;

import blcrawler.commands.templates.InstantCommand;
import blcrawler.model.ConsoleGUIModel;

/**
 * Launch a number of selenium modules at once
 * TODO: Error checking against profiles/tors actually set up
 * @author Joe Gallagher
 *
 */
public class CreateSelenium extends InstantCommand 
{
	/*
	 * Particular fields
	 */
	Integer count;		//The number of modules to create

	public CreateSelenium(String value) 
	{
		count = Integer.valueOf(value);
	}
	
	@Override
	public void execute() 
	{
		//Create the number of selenium modules specified by arguments
		ConsoleGUIModel.getSelenium().createSeleniums(count);	
	}
	
	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceQuit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

}
