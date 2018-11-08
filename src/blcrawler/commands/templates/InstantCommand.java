package blcrawler.commands.templates;

/**
 * Abstract representation of an instantely executed command.
 * executeImmediately, executeNext, getDelay, getTimeout, isFinished, setQueueID all inherited
 * @author Joe Gallagher
 *
 */
public abstract class InstantCommand implements Command 
{
	/*
	 * Standard fields
	 */
	public boolean isFinished;
	public int queueID;
	
	/**
	 * Constructor
	 */
	public InstantCommand() 
	{	
		//Usually specified by extending class, contains base code
		isFinished = false;
	}
	
	/**
	 * Always specified by extending class
	 */
	@Override
	public void execute() 
	{
	}
	
	@Override
	public boolean executeImmediately() 
	{
		//Instant commands are always executed immedately
		return true;
	}

	@Override
	public boolean executeNext() 
	{
		//Since execute immediately is true, executeNext must be false
		return false;
	}

	@Override
	public long getDelay() 
	{
		//Immediate commands execute with no delay
		return 0;
	}

	@Override
	public int getTimeout() 
	{
		//Immediate commands do not take actions which can timeout
		return 0;
	}

	@Override
	public boolean isFinished() 
	{
		//Instant commands use simple finished checking
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
	
	public void setQueueID(int id)
	{
		//Instant commands all go to the same queue, the instantqueue
		this.queueID=id;
	}

	@Override
	public void done() 
	{
		
	}

}
