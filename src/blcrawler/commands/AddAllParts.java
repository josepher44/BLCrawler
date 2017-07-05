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
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class AddAllParts implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> partIDs;
	private int queueID;
	public AddAllParts() {
		
		isFinished = false;


	}
	
	@Override
	public void execute() {
		Thread thread = new Thread() {
			public void run() 
			{
				File dir = new File("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/Parts/");
				partIDs = new ArrayList<>();
				for(File file: dir.listFiles()) 
				{

					if (file.length()<50000)
					{
						partID = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"));
						partIDs.add(partID);
					}
					else
					{
						System.out.println("part_"+file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_"))+"already scraped");
					}
					
					
				}
				
				long seed = System.nanoTime();
				Collections.shuffle(partIDs, new Random(seed));
				for (int i=0; i<partIDs.size(); i++)
				{
					ConsoleGUIModel.getSelenium().addToInstant(new AddPart(partIDs.get(i)));
					System.out.println("Part of ID #"+partIDs.get(i)+" added to instantQueue");
				}
				
				new ConsoleOutput("CommandResult", "Generated add part commands for all parts in catalog");
				isFinished = true;
				
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		

		
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
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

}
