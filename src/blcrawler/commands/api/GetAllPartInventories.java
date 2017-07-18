package blcrawler.commands.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import blcrawler.commands.Command;
import blcrawler.commands.addpage.AddPart;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.primatives.ColorMap;

public class GetAllPartInventories implements Command {

	boolean isFinished;
	String partID;
	ArrayList<CatalogPart> parts;
	private int queueID;
	private String path;
	private String basepath;
	public GetAllPartInventories() {
		
		isFinished = false;


	}
	
	@Override
	public void execute() {
		basepath = "C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/Inventories/Parts/";
		
		Thread thread = new Thread() {
			public void run() 
			{
				
				ColorMap colormap = ConsoleGUIModel.getDatabase().getColormap();
				parts = new ArrayList<>();
				for(CatalogPart part: ConsoleGUIModel.getDatabase().getCatalogParts()) 
				{
					if(part.getHasInventory())
					{
						parts.add(part);
					
					}
					
				}
				System.out.println("Added "+parts.size()+" parts to queue for inventory generation");
				
				long seed = System.nanoTime();
				Collections.shuffle(parts, new Random(seed));


				for (CatalogPart part : parts)
				{
	//				ConsoleGUIModel.getSelenium().addToInstant(new AddPart(partIDs.get(i)));
					String fullpath = basepath+"part_"+part.getPartNumber()+".txt";
					File filepath = new File(fullpath);
					if(!filepath.exists())
					{
						ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new GetPartInventory(part.getPartNumber()));
					}
					else			
					{
						System.out.println("Already scraped "+part.getPartNumber()+", file in folder");
					}
					
					//System.out.println("Part of ID #"+partIDs.get(i)+" added to instantQueue");
				}
				
				new ConsoleOutput("CommandResult", "Generated get image commands for all parts in catalog");
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
