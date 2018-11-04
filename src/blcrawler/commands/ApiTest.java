package blcrawler.commands;

import blcrawler.api.APIModel;

/**
 * Test class for API functionality. Currently, switching between calls is done by changing hard
 * coded line in execute(). To be depreciated and replaced with a suite of specific commands
 * @author Joe Gallagher
 *
 */
public class ApiTest implements Command 
{
	/*
	 * Standard fields
	 */
	boolean isFinished;
	int queueID;
	
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
		this.queueID = 9152;
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

	//TODO: This command should absolutely have a timeout working
	@Override
	public int getTimeout() 
	{
		return 0;
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
