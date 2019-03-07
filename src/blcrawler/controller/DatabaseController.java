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
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import blcrawler.commands.ReadPartsFromXML;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.MoldMaster;
import blcrawler.model.PartInventory;
import blcrawler.primatives.ColorCount;
import blcrawler.primatives.ColorMap;
import blcrawler.primatives.ObjectSpecificColorCount;

public class DatabaseController
{
	LinkedList<CatalogPart> catalogParts;
	Hashtable<String, CatalogPart>catalogPartsByID;
	ArrayList<MoldMaster> masterMolds;
	Hashtable<String, MoldMaster>masterMoldsByID;
	Hashtable<String, String> childToMasterMold;
	ArrayList<String> relationshipBufferTriggers;
	ArrayList<String> relationshipBufferItems;
	ArrayList<PartInventory> partInventories;
	long uniqueColoredParts;
	int xmlAppendCounter;

	Document partsDoc;
	Document moldsDoc;

	ColorMap colormap;
	Comparator<Element> elementComp;

	public DatabaseController()
	{
		catalogParts = new LinkedList<>();
		masterMolds = new ArrayList<>();
		catalogPartsByID = new Hashtable<>();
		masterMoldsByID = new Hashtable<>();
		childToMasterMold = new Hashtable<>();
		relationshipBufferTriggers = new ArrayList<>();
		uniqueColoredParts=0;
		colormap = new ColorMap();
		partsDoc = null;
		xmlAppendCounter = 0;
		partInventories = new ArrayList<>();



		elementComp = new Comparator<Element>() {
		    @Override
		    public int compare(Element left, Element right) {
		        //System.out.println("comparing "+left.getChildText("partnumber")+" to "+right.getChildText("partnumber"));
		    	return left.getChildText("partnumber").compareTo(right.getChildText("partnumber")); // use your logic
		    }

		};

		//readMasterXML();
	}

	public void sortPriceGuides()
	{
		int totalParts = 0;
		int count = 0;
		int nullparts = 0;
		LinkedList<ObjectSpecificColorCount> priceGuides = new LinkedList<>();
		for (CatalogPart p: catalogParts)
		{
			for (ColorCount c: p.getPriceGuides())
			{
				priceGuides.add(new ObjectSpecificColorCount(p.getPartNumber(), c));
				totalParts++;
			}
		}
		Collections.sort(priceGuides);
		
		for (ObjectSpecificColorCount o: priceGuides)
		{
			//System.out.println(o.getCc().getCount()+" Part Number: "+o.getPartNumber()+", Color: "+o.getCc().getColor()+", Quantity "+o.getCc().getCount());
		    
            int listid=0;
            int fscount=0;
            for (ColorCount fscolor: getPart(o.getPartNumber()).getItemsForSale())
            {
                if (fscolor.getColorID() == o.getCc().getColorID())
                {
                    fscount=getPart(o.getPartNumber()).getItemsForSale().get(listid).getCount();
                    break;
                }
                listid++;
            }
		    
		    if (fscount==0)
		    {
		        fscount=1;
		    }
			int V = getPart(o.getPartNumber()).getLengthMill() * getPart(o.getPartNumber()).getWidthMill() * getPart(o.getPartNumber()).getHeightMill();
			int VQ = V*fscount;
			//System.out.println(o.getCc().getCount()*V);
			//System.out.println(VQ+": Part number: " +o.getPartNumber()+" Color: "+o.getCc().getColor()+ " Volume: "+V+" Quantity Volume: "+VQ);
			//System.out.println(V);
			
			double weight = getPart(o.getPartNumber()).getWeight();
			System.out.println(weight);
			
			if(getPart(o.getPartNumber()).getLengthMill()>150 || getPart(o.getPartNumber()).getWidthMill()>150 || getPart(o.getPartNumber()).getHeightMill()>150)
			{
				int V2 = getPart(o.getPartNumber()).getLengthMill() * getPart(o.getPartNumber()).getWidthMill() * getPart(o.getPartNumber()).getHeightMill();

			
				//System.out.println("Oversize part: Part Number: "+o.getPartNumber()+" Volume = "+V2);

			}

			if(getPart(o.getPartNumber()).getLengthMill()==0 || getPart(o.getPartNumber()).getWidthMill()==0 || getPart(o.getPartNumber()).getHeightMill()==0)
			{
				nullparts++;
			}
			


			
			
			
		}
		int mill = 1;
		count = count+nullparts;
		while(count<totalParts)
		{
			int localCount = 0;
			mill=mill+8;
			for (ObjectSpecificColorCount o: priceGuides)
			{
				if((getPart(o.getPartNumber()).getLengthMill()<mill && getPart(o.getPartNumber()).getWidthMill()<mill && getPart(o.getPartNumber()).getHeightMill()<mill)&&
						(getPart(o.getPartNumber()).getLengthMill()>mill-9 || getPart(o.getPartNumber()).getWidthMill()>mill-9 || getPart(o.getPartNumber()).getHeightMill()>mill-9))
				{
					count++;
					localCount++;
				}
			}
			int studs = (mill-1)/4;
			if (localCount>0)
			{
				System.out.println("Parts that fit a "+studs+" footprint: "+localCount);
			}
			//System.out.println("Count: "+count+" Total: "+totalParts);
			
		}
		
		
		System.out.println("Total parts evaluated: "+totalParts);
		System.out.println("Total undersize parts: "+count);
		System.out.println("Total null parts: "+nullparts);
		sortVolume();

	}
	
	public void sortVolume()
	{
		LinkedList<Integer> parts = new LinkedList<>();
		int max = 0;
		int count = 0;
		int totalParts = 0;
		for (CatalogPart p: catalogParts)
		{
			totalParts++;
			parts.add(p.getHeightMill()*p.getWidthMill()*p.getLengthMill());
			if (p.getHeightMill()*p.getWidthMill()*p.getLengthMill()>max)
			{
				max = p.getHeightMill()*p.getWidthMill()*p.getLengthMill();
				System.out.println("New max part#: "+p.getPartNumber()+" Volume is: "+max);
			}
			if ((p.getHeightMill()<17&&p.getWidthMill()<17&&p.getLengthMill()<17)&&p.getHeightMill()*p.getWidthMill()*p.getLengthMill()>0)
			{
				count++;
			}
		}
		Collections.sort(parts);
		//System.out.println("Parts under half inch in all directions: "+count);
		//System.out.println("Parts evaluated: "+totalParts);
		for (Integer p: parts)
		{
			//System.out.println(p);
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
			int V = getPart(o.getPartNumber()).getLengthMill() * getPart(o.getPartNumber()).getWidthMill() * getPart(o.getPartNumber()).getHeightMill();
			//System.out.println(o.getCc().getCount()+" Part Number: "+o.getPartNumber()+", Color: "+o.getCc().getColor()+", Quantity "+o.getCc().getCount());
			System.out.println(o.getCc().getCount()*V);
		}


	}



	public void readMasterXML()
	{
		File masterXML = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml");

		SAXBuilder builder3 = new SAXBuilder();
		partsDoc = null;
		try
		{
			partsDoc = builder3.build(masterXML);
			Element rootElement = partsDoc.getRootElement();
			//doc.getRootElement().sortChildren(elementComp);
			System.out.println("Built master xml database "+rootElement.getChildren().size());
			int i=0;
			for (Element currentElement : rootElement.getChildren())
			{
				CatalogPart part = new CatalogPart(currentElement);
				catalogParts.add(part);
				catalogPartsByID.put(part.getPartNumber(), part);
				i++;
			}
			System.out.println("total parts imported: "+i);

		}
		catch (JDOMException | IOException e)
		{
			System.out.println("Master xml database does not exist, creating empty one");

			Element PartsXML = new Element("partsxml");
			partsDoc = new Document();
			partsDoc.setRootElement(PartsXML);

			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			try
			{
				xmlOutput.output(partsDoc, new FileWriter
						("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml"));
			}
			catch (IOException en)
			{
				// TODO Auto-generated catch block
				en.printStackTrace();
			}

		}
		
		//Might break everything, or might save gobs of memory
		//partsDoc = null;




	}
	
	public void replacePartMasterXML(CatalogPart part)
	{
		partsDoc.getRootElement().addContent(part.buildXML());
		//partsDoc.getRootElement().getChildren().
	}
	

	public void appendToMasterXML(CatalogPart part)
	{

		partsDoc.getRootElement().addContent(part.buildXML());
		xmlAppendCounter++;
		//doc.getRootElement().sortChildren(elementComp);
		if(xmlAppendCounter>0||ConsoleGUIModel.getSelenium().getQueued()<=2)
		{
			xmlAppendCounter = 0;
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try
			{
				xmlOutput.output(partsDoc, new FileWriter
						("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ConcurrentModificationException e)
			{
				System.out.println("WARNING: Concurrent modification exception thrown for part number "+part.getPartNumber()+". "
						+ "Part likely not correctly written to XML, will try again on next append");
			}
		}
	}

	public void removeFromMasterXML(CatalogPart part)
	{
		List<Element> children = partsDoc.getRootElement().getChildren("part");
		Iterator itr = children.iterator();
		while (itr.hasNext()) {
		  Element child = (Element) itr.next();
		  String att = child.getAttributeValue("id");
		  if( att.equals(part.getPartNumber())){
		    itr.remove();
		  }
		}

	}

	public void readMoldXML()
	{

	}



	public void buildMoldXML()
	{
		Document moldxml = new Document();
		Element root = new Element("partmolds");
		moldxml.setRootElement(root);
		Hashtable<String, String> partsdone = new Hashtable<>();
		int k = 0;
		int m = 0;
		int enn = 0;
		Pattern p = Pattern.compile("(p|pb|px)0?0?0?([0-9])([0-9])?([0-9])?([0-9])?");
		for(CatalogPart part : catalogParts)
		{
		String str = part.getPartNumber();



			if (part.getHasInventory())
			{

				if (part.getKnownColorsBL().size()>1)
				{
					enn=enn+part.getKnownColorsBL().size();
					//System.out.println("Part "+part.getPartNumber()+" has inventory, inventory count is "+n+", size is "+part.getKnownColorsBL().size());
				}
				else
				{
					enn++;
					//System.out.println("Part "+part.getPartNumber()+" has inventory, inventory count is "+n);
				}

			}

			else if (str.matches("(973p).*"))
			{
				k++;
				String strnew = "973";
				System.out.println(part.getPartNumber()+" has p, index "+k+". New string is "+strnew);
				if (partsdone.containsKey(strnew))
				{
					m++;
					System.out.println("Added "+str+" to already existing part "+partsdone.get(strnew)+", consolidation count "+m);
					addToMold(partsdone.get(strnew), str);
				}
				else
				{
					partsdone.put(strnew, str);
					addMasterMold(new MoldMaster(str));
					//System.out.println("Part "+str+" does not exist, creating new mold master of value "+str+". Mold count is "+masterMolds.size());
				}
			}

			else if (str.matches(".*(p|pb|px)0?0?0?([0-9])([0-9])?([0-9])?([0-9])?.*"))
			{
				k++;
				Matcher match = p.matcher(str);
				String strnew = match.replaceAll("");
				//System.out.println(part.getPartNumber()+" has p, index "+k+". New string is "+strnew);
				if (partsdone.containsKey(strnew))
				{
					m++;
					System.out.println("Added "+str+" to already existing part "+partsdone.get(strnew)+", consolidation count "+m);
					addToMold(partsdone.get(strnew), str);
				}
				else
				{
					partsdone.put(strnew, str);
					addMasterMold(new MoldMaster(str));
					//System.out.println("Part "+str+" does not exist, creating new mold master of value "+str+". Mold count is "+masterMolds.size());
				}



			}


			else
			{
				partsdone.put(str, str);
				addMasterMold(new MoldMaster(str));
				//System.out.println("Part "+str+" does not exist, creating new mold master of value "+str+". Mold count is "+masterMolds.size());
			}



			//doclocal.getRootElement().addContent(part.buildXML());

		}
		System.out.println("Mold count is "+masterMolds.size());
		buildInventories();
		ArrayList<PartInventory> masterInventories = new ArrayList<>();
		masterInventories.addAll(partInventories);

		System.out.println(masterInventories.size()+"Size of master inventories");

		Boolean foundamatch = false;
		for (int i=0; i<masterInventories.size(); i++)
		{
			String partA = masterInventories.get(i).moldNormalizedInventory().toString();
			if (masterInventories.get(i).getPartNumber().contains("973"))
			{
				System.out.println("Comparisons begin for "+masterInventories.get(i).getPartNumber()+" , "+partA);
			}
			foundamatch = false;
			for (int p1=i+1; p1<masterInventories.size(); )
			{
				String partB = masterInventories.get(p1).moldNormalizedInventory().toString();

				if (partA.equals(partB))
				{
					System.out.println("Part # "+masterInventories.get(i).getPartNumber()+"has an identical inventory to part"
							+masterInventories.get(p1).getPartNumber()+", merging now");


					if (partsdone.containsKey(masterInventories.get(i).getPartNumber()))
					{
						addToMold(partsdone.get(masterInventories.get(i).getPartNumber()), masterInventories.get(p1).getPartNumber());
						System.out.println("Did the positive if, added part number "+masterInventories.get(p1).getPartNumber()+" to part number "+masterInventories.get(i).getPartNumber());
						foundamatch = true;
					}
					else
					{
						String str = masterInventories.get(i).getPartNumber();
						partsdone.put(str, str);
						MoldMaster mold = new MoldMaster(masterInventories.get(i).getPartNumber());
						addMasterMold(mold);
						addToMold(partsdone.get(masterInventories.get(i).getPartNumber()), masterInventories.get(p1).getPartNumber());
						System.out.println("Part "+str+" does not exist, creating new mold master of value "+str+". Mold count is "+masterMolds.size());
						foundamatch = true;
					}

					masterInventories.remove(masterInventories.get(p1));

				}
				else
				{
					p1++;

				}
			}
			if (!foundamatch)
			{
				String str = masterInventories.get(i).getPartNumber();
				partsdone.put(str, str);
				addMasterMold(new MoldMaster(masterInventories.get(i).getPartNumber()));
				System.out.println("Part "+str+" has no matching assemblies, creating new mold master of value "+str+". Mold count is "+masterMolds.size());
			}
		}

		System.out.println("Reduced down to "+masterInventories.size()+" inventory parts from "+partInventories.size());

		//Mold masters all built, now write to xml
		for(MoldMaster mold : masterMolds)
		{
			Element e_mold = new Element("mold");

			Element master = new Element("masterpart");
			master.setText(mold.getMasterPartNumber());
			e_mold.addContent(master);

			Element submolds = new Element("subparts");
			for (String sub : mold.getSubPartNumbers())
			{
				Element s = new Element("part");
				s.setText(sub);
				submolds.addContent(s);
			}
			e_mold.addContent(submolds);

			Element verified = new Element("verified");
			verified.setText("false");
			e_mold.addContent(verified);

			Element smallDrawerEmpirical = new Element("smalldrawerempirical");
			e_mold.addContent(smallDrawerEmpirical);

			Element dimSlaves = new Element("dimslaves");
			e_mold.addContent(dimSlaves);

			Element dimMaster = new Element("dimmaster");
			dimMaster.setText("false");
			e_mold.addContent(dimMaster);

			moldxml.getRootElement().addContent(e_mold);
		}


		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try
		{
			xmlOutput.output(moldxml, new FileWriter
					("C:/Users/Owner/Documents/BLCrawler/Catalog/mold_database.xml"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Successfully wrote all mold data to xml");

	}

	public void buildInventories()
	{
		String basepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Inventories/Parts";
		File dir = new File(basepath);

		for(File file : dir.listFiles())
		{
			//System.out.println("Building part from file "+file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("part_")));
			partInventories.add(new PartInventory(file.getAbsolutePath()));

		}
		System.out.println("Files done: "+dir.listFiles().length);
	}

	public void buildMasterXML()
	{
		Thread thread = new Thread() {
			public void run()
			{
				Element PartsXML = new Element("partsxml");
				Document doclocal = new Document();
				doclocal.setRootElement(PartsXML);
				int i=0;
				for(CatalogPart part : catalogParts)
				{
					doclocal.getRootElement().addContent(part.buildXML());
					//System.out.println("XML appended for part "+part.getPartNumber()+", part "+i+" of "+catalogParts.size());
					i++;
				}

				//doc = doclocal;

				XMLOutputter xmlOutput = new XMLOutputter();

				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				try
				{
					xmlOutput.output(doclocal, new FileWriter
							("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml"));
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Successfully wrote all parts in memory to XML");
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
		    //System.out.println("Successfully converted HTML file for path "+HTMLpath);

		    out.close();
		}
		catch (IOException e)
		{
		    System.out.println("Exception ");

		}
		CatalogPart part = new CatalogPart(HTMLpath);
		addCatalogPart(part);
		appendToMasterXML(part);
	}







	public void fixHTMLs()
	{

		Thread thread = new Thread()
		{
			public void run()
			{
				int i=0;
				File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/");
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
					    //System.out.println("Successfully wrote file "+i+" of " +dir.listFiles().length);

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

	public void addMasterMold(MoldMaster mold)
	{
		masterMolds.add(mold);
		masterMoldsByID.put(mold.getMasterPartNumber(), mold);
	}

	public void addToMold(String master, String child)
	{
		MoldMaster Moldmaster = masterMoldsByID.get(master);
		Moldmaster.addSubPartNumber(child);
		childToMasterMold.put(child, master);
	}

	public String getMasterPartNumber(String child)
	{
		return childToMasterMold.get(child);
	}

	public Hashtable<String, String> getChildToMasterMold()
	{
		return childToMasterMold;
	}

	public void setChildToMasterMold(Hashtable<String, String> childToMasterMold)
	{
		this.childToMasterMold = childToMasterMold;
	}

	public MoldMaster getMold(String pn)
	{
		if (masterMoldsByID.containsKey(pn))
		{
			return masterMoldsByID.get(pn);
		}
		else
		{
			return (masterMoldsByID.get(childToMasterMold.get(pn)));
		}
	}
	



}

