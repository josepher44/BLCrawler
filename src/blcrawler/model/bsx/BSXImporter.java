package blcrawler.model.bsx;

import java.io.File;

import java.util.AbstractMap;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.bsx.inventorylot.DrawerDivision;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import blcrawler.model.bsx.inventorylot.InventoryLot;
import blcrawler.view.imsgui.IMSGUIView;


//TODO: THIS FILE IS GROSS. Contains whole bunches of stuff which have nothing to do with BSX
//importing. It was just a convenient place you knew would run on startup. Major refactor ASAP
public class BSXImporter
{
	String path;
	File file;
	Inventory inventory;
	ObservableList<InventoryLot> inventoryLotList = FXCollections.observableArrayList();
	ObservableList<InventoryLocation> inventoryLocationList = FXCollections.observableArrayList();
	Hashtable<String,DrawerDivision> drawerDivisionTable = new Hashtable<>();
	ArrayList<DrawerDivision> drawerDivisionList = new ArrayList<>();
	ArrayList<String> validCodes = new ArrayList<>();

	String csvpath;
	String partsummarypath;
	File csvfile;
	File partsummaryfile;


	public Hashtable<String, DrawerDivision> getDrawerDivisionTable()
	{
		return drawerDivisionTable;
	}

	public void setDrawerDivisionTable(Hashtable<String, DrawerDivision> drawerDivisionTable)
	{
		this.drawerDivisionTable = drawerDivisionTable;
	}



	public BSXImporter(String filelocation)
	{
		this.path=filelocation;
		file = new File(path);
		readBSX();

		this.csvpath = "C:/Users/Owner/Documents/BLCrawler/XMLDrawerDivisions.xml";
		csvfile = new File(csvpath);

		this.partsummarypath = "C:/Users/Owner/Documents/BLCrawler/parts.xml";
		partsummaryfile = new File(partsummarypath);







	}

	//What were you thinking putting this in this file?
	public void readDrawerDivisions()
	{
		System.out.println("Reading drawer layout and empty locations from csv");

		this.csvpath = "C:/Users/Owner/Documents/BLCrawler/XMLDrawerDivisions.xml";
		csvfile = new File(csvpath);

		SAXBuilder builder2 = new SAXBuilder();
		Document readDoc2 = null;

		try
		{
			readDoc2 = builder2.build(csvfile);
		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		Element rootElement = readDoc2.getRootElement();

		List list = rootElement.getChildren("row");
		for (int i = 0; i < list.size(); i++)
		{
			Element node = (Element) list.get(i);
			//System.out.println("Drawer: "+node.getChildText("drawer"));
			//System.out.println("Slot: "+node.getChildText("slot"));
			//System.out.println("Size: "+node.getChildText("size"));
			//System.out.println("Created drawer ID "+i);

			DrawerDivision division = new DrawerDivision(	node.getChildText("drawer"),
					node.getChildText("slot"),
					node.getChildText("size"),
					node.getChildText("empty"),
					i);


			drawerDivisionTable.put(division.getRawRemarks(),division);
			drawerDivisionList.add(division);

			//System.out.println(division.getRawRemarks());

		}

		System.out.println("CSV import complete, beginning BSX import");
	}

	//Why on earth is this here?
	public void readBLCatalogSummary()
	{
		System.out.println("Reading summary data for all parts in bricklink catalog");

		this.partsummarypath = "C:/Users/Owner/Documents/BLCrawler/parts.xml";
		partsummaryfile = new File(partsummarypath);

		SAXBuilder builder3 = new SAXBuilder();
		Document readDoc3 = null;


		try
		{
			readDoc3 = builder3.build(partsummaryfile);
		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}


		Element rootElement = readDoc3.getRootElement();

		List list = rootElement.getChildren("ITEM");

		ArrayList<String> partsDone = new ArrayList<String>();


		int k=0;
		File f;
		for (int i = 0; i < list.size(); i++)
		{

			Element node = (Element) list.get(i);
			//System.out.println("Reading part "+(i+1)+" of "+list.size());

			f = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/"
					+"part_"+node.getChildText("ITEMID")+".xml");
			if(f.exists())
			{

				//System.out.println("file already exists for part "+node.getChildText("ITEMID"));
			}
			else
			{
			    try
				{
					Element part = new Element("part");
					Document subdoc = new Document();
					subdoc.setRootElement(part);

					Element blid = new Element("blid");
					blid.setText(node.getChildText("ITEMID"));



					subdoc.getRootElement().addContent(blid);

					// new XMLOutputter().output(doc, System.out);
					XMLOutputter xmlOutput = new XMLOutputter();

					// display nice nice
					xmlOutput.setFormat(Format.getPrettyFormat());
					xmlOutput.output(subdoc, new FileWriter("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/"
							+"part_"+node.getChildText("ITEMID")+".xml"));

					System.out.println(node.getChildText("ITEMID")+" File Saved!");
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

//			if (node.getChildText("ITEMID").contains("p"))
//			{
//				if (partsDone.contains(node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p'))))
//				{
//					System.out.println("Patterned entry for part number "+node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p'))
//							+" added, pattern count at "+k);
//					k++;
//				}
//				else
//				{
//					partsDone.add(node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p')));
//				}
//			}
//			else
//			{
//				if (partsDone.contains(node.getChildText("ITEMID")))
//				{
//					System.out.println("Patterned entry for part number "+node.getChildText("ITEMID")
//							+" added, pattern count at "+k);
//					k++;
//				}
//				else
//				{
//					partsDone.add(node.getChildText("ITEMID"));
//				}
//
//			}

			//System.out.println("Slot: "+node.getChildText("slot"));
			//System.out.println("Size: "+node.getChildText("size"));
		}
	}

	public ObservableList<InventoryLot> readBSX()
	{
		System.out.println("Creating database");

		try
		{
			readDrawerDivisions();
			//readBLCatalogSummary();
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SAXBuilder builder = new SAXBuilder();
		Document readDoc = null;
		try
		{
			readDoc = builder.build(file);

		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		Element root = readDoc.getRootElement().getChild("Inventory");
		//System.out.println("Root: " + readDoc.getRootElement().getChild("Inventory").getChild("Item").getChildText("ItemID"));
		int i=0;
		int multis=0;
		for (Element currentElement : root.getChildren("Item"))
		{

			InventoryLot lot = new InventoryLot();
			try
			{

				try
				{
					lot.setCabinet(Short.parseShort(currentElement.getChildText("Remarks").substring(0, 3)));
				}
				catch (Exception e)
				{
					lot.setCabinet((short) 0);
				}
				lot.setCategoryID(Short.parseShort(currentElement.getChildText("CategoryID")));
				lot.setCategoryName(currentElement.getChildText("CategoryName"));
				lot.setColorID(Short.parseShort(currentElement.getChildText("ColorID")));
				lot.setColorName(currentElement.getChildText("ColorName"));
				lot.setComments(currentElement.getChildText("Comments"));
				lot.setCondition(currentElement.getChildText("Condition").charAt(0));
				lot.setQty(Integer.parseInt(currentElement.getChildText("Qty")));
				lot.setPrice(Double.parseDouble(currentElement.getChildText("Price")));
				lot.setItemID(currentElement.getChildText("ItemID"));
				lot.setItemName(currentElement.getChildText("ItemName"));
				lot.setItemType(currentElement.getChildText("ItemType"));
				lot.setRemarks(currentElement.getChildText("Remarks"));
				lot.deriveAllRemarks(currentElement.getChildText("Remarks"));
				try
				{
					lot.setLotID(Integer.parseInt(currentElement.getChildText("LotID")));
				}
				catch (Exception e)
				{
					lot.setLotID(0);
				}
				//lot.generateImageLocation();





				lot.setIndex(i);
				if (currentElement.getChildText("Remarks").contains(" 0"))
				{
					lot.setMultiLocated(true);
					String input = currentElement.getChildText("Remarks");
					int count = 0;
				    int idx = 0;

				    while ((idx = input.indexOf(" 0", idx)) != -1)
				    {
				       idx++;
				       count++;

				    }




					for (int k = 0; k<count; k++)
					{
						InventoryLocation location = new InventoryLocation(lot, input.substring(0,input.indexOf(" 0")));
					    input = input.substring(input.indexOf(" 0")+1, input.length());
					    inventoryLocationList.add(location);
					    System.out.println("Added a multi drawer for lotID #"+ location.getLotID()+", Loop iteration #"+k + ", With remark "+ location.getRemarks());
				       multis++;
				       //System.out.println("Multi count: "+multis);
					}
					InventoryLocation location = new InventoryLocation(lot, input);
				    //location.setRemarks(input);
				    inventoryLocationList.add(location);
				    System.out.println("Added a multi drawer for lotID #"+ location.getLotID()+", Final loop iteration, With remark "+ location.getRemarks());
				    multis++;
				    //System.out.println("Multi count: "+multis);


				}
				else
				{
					lot.setMultiLocated(false);
					inventoryLocationList.add(new InventoryLocation(lot));
				}


				inventoryLotList.add(lot);
				//System.out.println(lot.getCabinet()+lot.getCategoryID()+lot.getCategoryName()+lot.getColorID()+lot.getColorName()+lot.getComments()+lot.getRemarks());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.out.println(i);
				e.printStackTrace();
			}



			i++;
		}
		System.out.println("Inventory total lots: "+ inventoryLotList.size());
		IMSGUIView.getCurrentInventory().setInventoryLotList(inventoryLotList);
		IMSGUIView.getCurrentInventory().setInventoryLocationList(inventoryLocationList);


		return inventoryLotList;


	}

	public ObservableList<InventoryLot> getInventoryLotList()
	{
		return inventoryLotList;
	}

	public void setInventoryLotList(ObservableList<InventoryLot> inventoryLotList)
	{
		this.inventoryLotList = inventoryLotList;
	}

	public ObservableList<InventoryLocation> getInventoryLocationList()
	{
		return inventoryLocationList;
	}

	public void setInventoryLocationList(ObservableList<InventoryLocation> inventoryLocationList)
	{
		this.inventoryLocationList = inventoryLocationList;
	}

	public ArrayList<DrawerDivision> getDrawerDivisionList()
	{
		return drawerDivisionList;
	}

	public void setDrawerDivisionList(ArrayList<DrawerDivision> drawerDivisionList)
	{
		this.drawerDivisionList = drawerDivisionList;
	}




}
