package blcrawler.commands;

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

import blcrawler.commands.addpage.AddPart;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.primatives.ColorMap;

public class AddAllImages implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> urls;
	Hashtable<String, String> pathMap;
	private int queueID;
	private String path;
	private String basepath;
	public AddAllImages() {
		
		isFinished = false;


	}
	
	@Override
	public void execute() {
		basepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Images/";
		
		Thread thread = new Thread() {
			public void run() 
			{
				
				ColorMap colormap = ConsoleGUIModel.getDatabase().getColormap();
				urls = new ArrayList<>();
				pathMap = new Hashtable<>();
				for(CatalogPart part: ConsoleGUIModel.getDatabase().getCatalogParts()) 
				{
					for(String color : part.getKnownColorsBLMenu())
					{
						path = basepath+part.getPartNumber()+"_"+color+".png";
						urls.add("https://img.bricklink.com/ItemImage/PN/"+colormap.idFromName(color)+"/"+part.getPartNumber()+".png");
						pathMap.put("https://img.bricklink.com/ItemImage/PN/"+colormap.idFromName(color)+"/"+part.getPartNumber()+".png",
								path);
					
					}
					
				}
				System.out.println("Added "+urls.size()+" images to queue");
				
				long seed = System.nanoTime();
				Collections.shuffle(urls, new Random(seed));
				String notfound = "";
				try
				{
					notfound = new String(Files.readAllBytes(Paths.get(basepath+"notfound.txt")));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (String geturl : urls)
				{
	//				ConsoleGUIModel.getSelenium().addToInstant(new AddPart(partIDs.get(i)));
					File filepath = new File(pathMap.get(geturl));
					if (notfound.contains(geturl))
					{
						//System.out.println("Already scraped "+geturl+", image does not exist");
					}
					else if (!filepath.exists())
					{
						ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new AddImage(geturl, pathMap.get(geturl)));
					}
					else			
					{
						//System.out.println("Already scraped "+geturl+", file added to folder");
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
