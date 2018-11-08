package blcrawler.commands.individualcalls.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import blcrawler.commands.abstractcommands.InstantCommand;
import blcrawler.model.CatalogPart;

/**
 * Class to build an individual CatalogPart class from the data in the master xml database
 * Not currently in use discretely
 * @author Joe Gallagher
 *
 */
public class BuildAPartXML extends InstantCommand 
{
	String partID;
	ArrayList<String> partIDs;
	String partNumber;
	public BuildAPartXML(String partnumber) 
	{
		partNumber = partnumber;
	}
	
	@Override
	public void execute() 
	{
		//Open the master xml database via SAXBuilder into Document doc
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

		//Access every partNumber element in the document, passing each sub-xml as xmlpart
		String query = "//*[@id= '"+partNumber+"']";
		XPathExpression<Element> xpe = XPathFactory.instance().compile(query, Filters.element());
		for (Element xmlpart : xpe.evaluate(doc)) 
		{
			//TODO: Method to export this part, so that this class can be used outside of
					//command tests
			@SuppressWarnings("unused")
			CatalogPart part = new CatalogPart(xmlpart);
		}
		
		//Finalize execution
		isFinished = true;
		done();
	}

	@Override
	public void queue() 
	{
		
	}

	@Override
	public void forceQuit() 
	{
		
	}

	@Override
	public void done() 
	{
		
	}

}
