package blcrawler.commands;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.queue.TaskTimer;


//Command used to initialize the current command field. Should not be used otherwise
public class InitialCommand implements Command {
	private TaskTimer timer;
	private int timeout;
	private int delay;
	private boolean isFinished;
	private int queueID;
	
	public InitialCommand() {
		timeout=0;
		delay=0;
		isFinished=true;

	}

	
	
	@Override
	public void execute() {

		isFinished=true;
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeNext() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return delay;
	}



	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return timeout;
	}



	@Override
	public boolean isFinished() {

		// TODO Auto-generated method stub
		return isFinished;
	}



	@Override
	public void queue() {
		
		
	}



	@Override
	public void forceQuit() {
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
