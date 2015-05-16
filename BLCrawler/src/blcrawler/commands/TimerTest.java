package blcrawler.commands;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;
import blcrawler.model.queue.TaskTimer;

public class TimerTest implements Command {
	private TaskTimer timer;
	private int timeout;
	private int delay;
	private boolean isFinished;
	
	public TimerTest() {
		timeout=10;
		delay=5;
		isFinished=false;

	}

	
	
	@Override
	public void execute() {

		new ConsoleOutput("CommandResult", "Timer complete");
		isFinished=true;
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queue() {
		new ConsoleOutput("CommandResult", "Timer started. Standby. " + GUIModel.getTaskTimer().queue.size() + " tasks queued");
		
	}

	@Override
	public void stop() {
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
	public long getDelay() {
		// TODO Auto-generated method stub
		return delay;
	}
	
	

}
