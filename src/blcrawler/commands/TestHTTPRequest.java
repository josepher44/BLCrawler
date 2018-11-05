package blcrawler.commands;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.commands.individualcalls.PartBLToRawDatabase;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.Part;

public class TestHTTPRequest implements Command {

	boolean isFinished;
	String url;
	private int queueID;
	
	private long delay;
	private int timeout;
	
	public TestHTTPRequest(String URL) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, 15);
		timeout = 15+randomNum;
		delay = 10+randomNum;
		isFinished = false;
		this.url = URL;
		this.queueID = 9152;

	}
	
	@Override
	public void execute() {

		ConsoleGUIModel.getSelenium().getImage(url, queueID, "C:/Users/Owner/Documents/BLCrawler/Catalog/test.png");
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
