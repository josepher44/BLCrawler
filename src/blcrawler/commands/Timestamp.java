package blcrawler.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleOutput;

public class Timestamp implements Command {

	private int queueID;

	public Timestamp() {

	}
	
	@Override
	public void execute() {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();

		new ConsoleOutput("CommandResult", dateFormat.format(date));

		
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
