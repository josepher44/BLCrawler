package blcrawler.commands.individualcalls;

import java.util.concurrent.ThreadLocalRandom;

import blcrawler.api.APIModel;
import blcrawler.commands.Command;

/**
 * Scrapes and records the inventory of a single part via the api
 * @author Joe Gallagher
 *
 */
public class InventoryBLToRawDatabase implements Command {

	/*
	 * Standard fields
	 */
	boolean isFinished;
	private long delay;
	private int timeout;
	int queueID;
	
	/*
	 * Particular fields
	 */
	String partNumber;	//The part being accessed
	
	/**
	 * Constructor
	 * @param partnumber The part number to produce the inventory for
	 */
	public InventoryBLToRawDatabase(String partnumber) 
	{
		//Randomize delay and timeout
		int randomNum = ThreadLocalRandom.current().nextInt(0, 1);
		timeout = 1+randomNum;
		delay = 0+randomNum;
		isFinished = false;
		partNumber = partnumber;
	}
	
	@Override
	public void execute() 
	{
		//Create an APIModel and perform getItemInventory
		APIModel api = new APIModel(queueID);
		api.getItemInventory(partNumber);
		
		//Finish execution
		isFinished = true;
		done();
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
