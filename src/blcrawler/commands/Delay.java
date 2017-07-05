package blcrawler.commands;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.queue.TaskTimer;

public class Delay implements Command {
	private TaskTimer timer;
	private int timeout;
	private int delay;
	private boolean isFinished;
	private int queueID;
	
	public Delay(int n_delay) {
		timeout=n_delay;
		delay=n_delay;
		isFinished=false;

	}

	
	
	@Override
	public void execute() {

		//new ConsoleOutput("CommandResult", "Timer complete");
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
		//new ConsoleOutput("CommandResult", "Timer started. Standby. " + ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued");
		
	}



	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}
	
	

}
