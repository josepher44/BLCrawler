package blcrawler.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import blcrawler.commands.ReadPartsFromXML;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.primatives.ColorCount;
import blcrawler.primatives.ColorMap;
import blcrawler.primatives.ObjectSpecificColorCount;

public class DatabaseController
{
	LinkedList<CatalogPart> catalogParts;
	Hashtable<String, CatalogPart>catalogPartsByID;
	ArrayList<String> relationshipBufferTriggers;
	ArrayList<String> relationshipBufferItems;
	long uniqueColoredParts;
	
	Document doc;
	
	ColorMap colormap;
	
	public DatabaseController()
	{
		catalogParts = new LinkedList<>();
		catalogPartsByID = new Hashtable<>();
		relationshipBufferTriggers = new ArrayList<>();
		uniqueColoredParts=0;
		colormap = new ColorMap();
		doc = null;
		
		ReadPartsFromXML command = new ReadPartsFromXML();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
		
		//readMasterXML();
	}
	
	public void sortPriceGuides()
	{
		LinkedList<ObjectSpecificColorCount> priceGuides = new LinkedList<>();
		for (CatalogPart p: catalogParts)
		{
			for (ColorCount c: p.getPriceGuides())
			{
				priceGuides.add(new ObjectSpecificColorCount(p.getPartNumber(), c));
			}
		}
		Collections.sort(priceGuides);
		
		for (ObjectSpecificColorCount o: priceGuides)
		{
			System.out.println(o.getCc().getCount()+" Part Number: "+o.getPartNumber()+", Color: "+o.getCc().getColor()+", Quantity "+o.getCc().getCount());
		}
		
		for (ObjectSpecificColorCount o: priceGuides)
		{
			System.out.println(o.getCc().getCount());
		}
		
	}
	
	public void sortItemsForSale()
	{
		LinkedList<ObjectSpecificColorCount> itemsForSale = new LinkedList<>();
		for (CatalogPart p: catalogParts)
		{
			for (ColorCount c: p.getItemsForSale())
			{
				itemsForSale.add(new ObjectSpecificColorCount(p.getPartNumber(), c));
			}
		}
		Collections.sort(itemsForSale);
		
		for (ObjectSpecificColorCount o: itemsForSale)
		{
			System.out.println(o.getCc().getCount()+" Part Number: "+o.getPartNumber()+", Color: "+o.getCc().getColor()+", Quantity "+o.getCc().getCount());
		}
		
		
	}
	
	public void readMasterXML()
	{
		File masterXML = new File("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/part_database.xml");
		
		SAXBuilder builder3 = new SAXBuilder();
		doc = null;
		try
		{
			doc = builder3.build(masterXML);
		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		
		
		Element rootElement = doc.getRootElement();
		System.out.println("Built master xml database "+rootElement.getChildren().size());

		for (Element currentElement : rootElement.getChildren())
		{
			catalogParts.add(new CatalogPart(currentElement));
		}
		
		
	}
	
	
	public void buildMasterXML()
	{
		Thread thread = new Thread() {
			public void run() 
			{
				Element PartsXML = new Element("partsxml");
				Document doc = new Document();
				doc.setRootElement(PartsXML);
				int i=0;
				for(CatalogPart part : catalogParts)
				{
					
					doc.getRootElement().addContent(part.buildXML());
					System.out.println("XML appended for part "+part.getPartNumber()+", part "+i+" of "+catalogParts.size());
					i++;
				}
				
				XMLOutputter xmlOutput = new XMLOutputter();

				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				try
				{
					xmlOutput.output(doc, new FileWriter
							("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/part_database.xml"));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		};
		thread.setDaemon(true);
		thread.start();
		
			
		
		
	}
	
	public void incrementColoredParts(int value)
	{
		uniqueColoredParts = uniqueColoredParts+value;
		if (value!=0)
		{
			//System.out.println("Unique colored parts so far: "+uniqueColoredParts);	
		}
		
	}
	
	public void addRelationshipBufferTrigger(String partID)
	{
		relationshipBufferTriggers.add(partID);
	}
	
	public void addRelationshipBufferItem(String partID)
	{
		relationshipBufferItems.add(partID);
	}
	
	public void updateFileLists()
	{
		
	}
	
	public void addCatalogPart(CatalogPart part)
	{
		catalogParts.add(part);
		catalogPartsByID.put(part.getPartNumber(), part);
	}
	
	public CatalogPart getPart(String partNumber)
	{
		return catalogPartsByID.get(partNumber);
	}
	
	public boolean partExists(String partNumber)
	{
		if (catalogPartsByID.containsKey(partNumber))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public void fixHTML(String oldpath)
	{
		byte[] encoded;
		String fileContents = "";
		
		String path = oldpath;
		File partFile = new File(path);
		
		try
		{
			encoded = Files.readAllBytes(Paths.get(partFile.getAbsolutePath()));
			fileContents = new String(encoded, StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			fileContents = fileContents.substring(fileContents.indexOf("<HTML>")+6, fileContents.indexOf("</HTML>"));
			fileContents = fileContents.replace("&lt;", "<");
			fileContents = fileContents.replace("&gt;", ">");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println("Null pointer at file "+partFile.getAbsolutePath()+", no HTML tags found");
		}
		String HTMLpath = partFile.getAbsolutePath();
		HTMLpath = HTMLpath.replace("Parts", "HTML");
		HTMLpath = HTMLpath.replace(".xml", ".html");
		
		System.out.println(HTMLpath);
		
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(HTMLpath));
		    out.write(fileContents);  //Replace with the string 
		                                             //you are trying to write  
		    System.out.println("Successfully converted HTML file for path "+HTMLpath);
		    
		    out.close();
		}
		catch (IOException e)
		{
		    System.out.println("Exception ");

		}
	}
	
	public void fixHTMLs()
	{

		Thread thread = new Thread() 
		{
			public void run() 
			{
				int i=0;
				File dir = new File("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/Parts/");
				ArrayList partIDs = new ArrayList<>();
				for(File file: dir.listFiles()) 
				{
					i++;
					byte[] encoded;
					String fileContents = "";
					try
					{
						encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
						fileContents = new String(encoded, StandardCharsets.UTF_8);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try
					{
						fileContents = fileContents.substring(fileContents.indexOf("<HTML>")+6, fileContents.indexOf("</HTML>"));
						fileContents = fileContents.replace("&lt;", "<");
						fileContents = fileContents.replace("&gt;", ">");
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						System.out.println("Null pointer at file "+file.getAbsolutePath()+", no HTML tags found");
					}
					String path = file.getAbsolutePath();
					path = path.replace("Parts", "HTML");
					path = path.replace(".xml", ".html");
					
					System.out.println(path);
					
					try {
					    BufferedWriter out = new BufferedWriter(new FileWriter(path));
					    out.write(fileContents);  //Replace with the string 
					                                             //you are trying to write  
					    System.out.println("Successfully wrote file "+i+" of " +dir.listFiles().length);
					    
					    out.close();
					}
					catch (IOException e)
					{
					    System.out.println("Exception ");
		
					}
				}
			}
			
		};
		thread.setDaemon(true);
		thread.start();
	}

	public ColorMap getColormap()
	{
		return colormap;
	}

	public void setColormap(ColorMap colormap)
	{
		
		this.colormap = colormap;
	}
	
	public ArrayList<String> getPartCategories()
	{
		ArrayList<String> out = new ArrayList<>(); 
		for (CatalogPart part : catalogParts)
		{
			
			if (!out.contains(part.getCategoryName()))
			{
				out.add(part.getCategoryName());
			}
			
		}
		out.sort(String::compareToIgnoreCase);
		return out;
	}

	public LinkedList<CatalogPart> getCatalogParts()
	{
		return catalogParts;
	}

	public void setCatalogParts(LinkedList<CatalogPart> catalogParts)
	{
		this.catalogParts = catalogParts;
	}
	
	
}

