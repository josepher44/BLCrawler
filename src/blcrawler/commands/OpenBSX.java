package blcrawler.commands;

import blcrawler.commands.templates.Command;

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
