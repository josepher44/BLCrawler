package blcrawler.commands;

import blcrawler.commands.templates.Command;

/**
 * Command to parse a bsx file into memory for use in the imsgui system. Currently empty, function
 * achieved by the overdone OpenBSX class, to be refactored
 * TODO: Move key methods of OpenBSX here, or at least put function calls in execute
 * @author Joe Gallagher
 *
 */
public class OpenBSX implements Command
{

	private int queueID;

	@Override
	public void execute()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean executeImmediately()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeNext()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTimeout()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void queue()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceQuit()
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

}
