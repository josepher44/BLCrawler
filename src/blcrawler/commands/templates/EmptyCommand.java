package blcrawler.commands.templates;

/**
 * Template empty command. Should not be actually used. See interface for documentation of use
 * @author Joe Gallagher
 *
 */
public class EmptyCommand implements Command 
{

	int queueID;
	public EmptyCommand() 
	{
		//Initialize any variables here
	}
	
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
		return 0;
	}

	@Override
	public int getTimeout() 
	{
		return 0;
	}

	@Override
	public boolean isFinished() 
	{
		return false;
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
		queueID=id;	
	}

	@Override
	public void done() 
	{
		
	}
}
