package blcrawler.commands.templates;

/**
 * Abstract representation of a command with a non-zero delay and timeout, called via a delay queue
 * executeImmediately, executeNext, getDelay, getTimeout, isFinished, setQueueID all inherited
 * TODO: Force quit will likely have some mostly-standard behaviors when that is implemented
 * @author Joe Gallagher
 *
 */
public abstract class QueueCommandStandard implements Command {
	/*
	 * Standard fields
	 */
	public int timeout;
	public int delay;
	public boolean isFinished;
	public int queueID;
	
	/**
	 * Constructor
	 */
	public QueueCommandStandard() 
	{
		
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
		return false;
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
