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

public class BuildPartsFromHTML implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> partIDs;
	private int queueID;
	public BuildPartsFromHTML() {
		
		isFinished = false;


	}
	
	@Override
	public void execute() {
		Thread thread = new Thread() {
			public void run() 
			{
				File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/HTML/");
				partIDs = new ArrayList<>();
				int i=0;
				for (CatalogPart part : ConsoleGUIModel.getDatabase().getCatalogParts())
				{
					System.out.println("Part "+part.getPartNumber()+"exists");
				}
				System.out.println("Done with parts that exist, moving on");
				for(File file: dir.listFiles()) 
				{
					
					String partNumber="";
					try
					{
						partNumber = file.getAbsolutePath().substring(file.getAbsolutePath().indexOf('_')+1, file.getAbsolutePath().indexOf(".html"));
						if (!ConsoleGUIModel.getDatabase().partExists(partNumber))
						
						{
							ConsoleGUIModel.getDatabase().addCatalogPart(new CatalogPart(file.getAbsolutePath()));
							i++;

							//if (i%100==0)
							//{
								System.out.println("Built part# "+partNumber+". "+i+" of "+dir.listFiles().length);
							//}
						}
						
						
					}
					catch (Exception e)
					{
						if (!partNumber.equals(""))
						{
							e.printStackTrace();
							System.out.println("failure at part "+partNumber);
						}
						else
						{
							e.printStackTrace();
							System.out.println("failure at file "+file.getAbsolutePath()+"");
						}
						
					}
				}
				ConsoleGUIModel.getDatabase().buildMasterXML();
				isFinished = true;
//				String partNumber = "3004.html";
//				ConsoleGUIModel.getDatabase().addCatalogPart(new CatalogPart(partNumber));

				
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
