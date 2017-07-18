package blcrawler.commands.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import blcrawler.api.APIModel;
import blcrawler.commands.Command;
import blcrawler.commands.addpage.AddPart;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class GetPartInventory implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> partIDs;
	private int queueID;
	String partNumber;
	
	private long delay;
	private int timeout;
	
	public GetPartInventory(String partnumber) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 1);
		timeout = 1+randomNum;
		delay = 0+randomNum;
		isFinished = false;
		partNumber = partnumber;


	}
	
	@Override
	public void execute() {
		APIModel api = new APIModel(queueID);
		api.getItemInventory(partNumber); 
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
