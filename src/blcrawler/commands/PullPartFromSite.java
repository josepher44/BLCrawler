package blcrawler.commands;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.commands.individualcalls.scraping.PartBLToRawDatabase;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.Part;

public class PullPartFromSite implements Command {

	boolean isFinished;
	String partID;
	private int queueID;
	
	private long delay;
	private int timeout;
	
	private Part parent;
	public PullPartFromSite(String part, Part parent) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, 15);
		timeout = 15+randomNum;
		delay = 10+randomNum;
		isFinished = false;
		this.partID = part;
		this.parent = parent;

	}
	
	@Override
	public void execute() {
		String url = "https://www.bricklink.com/v2/Catalog//Catalog/item.page?P="+
				partID.substring(partID.indexOf('_')+1, partID.indexOf(".xml"))+"#T=C";
		
		parent.setPageHTML(ConsoleGUIModel.getSelenium().getURL(url, queueID));
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
