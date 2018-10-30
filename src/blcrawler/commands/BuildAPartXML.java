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
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import blcrawler.commands.addpage.AddPart;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class BuildAPartXML implements Command {

	boolean isFinished;
	String partID;
	ArrayList<String> partIDs;
	private int queueID;
	String partNumber;
	public BuildAPartXML(String partnumber) {
		
		isFinished = false;
		partNumber = partnumber;


	}
	
	@Override
	public void execute() {
		File masterXML = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml");
		
		SAXBuilder builder3 = new SAXBuilder();
		Document doc = null;
		try
		{
			doc = builder3.build(masterXML);
		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		
		
		Element rootElement = doc.getRootElement();
		
		String query = "//*[@id= '"+partNumber+"']";
		XPathExpression<Element> xpe = XPathFactory.instance().compile(query, Filters.element());
		for (Element urle : xpe.evaluate(doc)) 
		{
			CatalogPart part = new CatalogPart(urle);
		}
		

		
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
