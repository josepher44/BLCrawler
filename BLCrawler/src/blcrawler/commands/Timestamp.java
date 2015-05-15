package blcrawler.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.model.ConsoleOutput;

public class Timestamp implements Command {

	public Timestamp() {
		execute();
	}
	
	@Override
	public void execute() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		new ConsoleOutput("CommandResult", dateFormat.format(date));

		
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
