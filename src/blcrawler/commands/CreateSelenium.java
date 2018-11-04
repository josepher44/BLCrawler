package blcrawler.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class CreateSelenium implements Command {

	private int queueID;
	Integer count;

	public CreateSelenium(String value) {
		count = Integer.valueOf(value);
	}
	
	
	@Override
	public void execute() {


		ConsoleGUIModel.getSelenium().createSeleniums(count);

		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
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
