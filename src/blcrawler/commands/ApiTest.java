package blcrawler.commands;

import blcrawler.api.APIModel;
import blcrawler.commands.abstractcommands.InstantCommand;

/**
 * Test class for API functionality. Currently, switching between calls is done by changing hard
 * coded line in execute(). To be depreciated and replaced with a suite of specific commands
 * @author Joe Gallagher
 *
 */
public class ApiTest extends InstantCommand
{	
	/*
	 * Particular fields
	 */
	String partNumber;	//The part being queried
	
	/**
	 * Constructor
	 * @param partnumber the part number being queried, first command line argument
	 */
	public ApiTest(String partnumber) 
	{	
		isFinished = false;
		this.partNumber = partnumber;
		//DIRTY TEST, SHOULD NOT BE HARD CODED
		queueID = 9152;
	}
	
	@Override
	public void execute() 
	{
		//Create an APIModel class, and call specified function
		//TODO: Migrate functionality of API model into the execute command, depreciate class
		APIModel api = new APIModel(queueID);
		api.getPriceGuide(partNumber); 
		
		//Conclude command call
		done();
		isFinished = true;
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
