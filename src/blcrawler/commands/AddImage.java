package blcrawler.commands;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.commands.addpage.AddPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.Part;

public class AddImage implements Command {

	boolean isFinished;
	String url;
	private int queueID;
	
	private long delay;
	private int timeout;
	
	private String path;
	public AddImage(String URL, String Path) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
		timeout = 15+randomNum;
		delay = 0+randomNum;
		isFinished = false;
		this.url = URL;
		this.path = Path;
	}
	
	@Override
	public void execute() {
		
		ConsoleGUIModel.getSelenium().getImage(url, queueID, path);
		//new ConsoleOutput("CommandResult", "Directed Selenium module to parse data for url "+url);
		isFinished = true;
		
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
		// TODO Auto-generated method stub
		
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