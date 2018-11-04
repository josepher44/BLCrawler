package blcrawler.commands;

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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import blcrawler.commands.addpage.AddPart;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class SortPriceGuides implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> partIDs;
	private int queueID;
	String partNumber;
	public SortPriceGuides() {
		
		isFinished = false;


	}
	
	@Override
	public void execute() {
		System.out.println("reached execute");
		Thread thread = new Thread() {
			public void run() 
			{
				ConsoleGUIModel.getDatabase().sortPriceGuides();

				
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
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
