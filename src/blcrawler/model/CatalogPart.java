package blcrawler.model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import blcrawler.primatives.ColorCount;
import blcrawler.primatives.ColorMap;

public class CatalogPart
{
	private String partNumber;
	private int bricklinkInternalID;


    private Date dateAdded;
	private int categoryID;
	private String categoryName;
	private String name;
	private ArrayList<String> altNumbers;
	private int firstYear;
	private int lastYear;
	private double weight;
	private double lengthStuds;
	private double widthStuds;
	private double heightStuds;
	private int lengthMill;
	private int widthMill;
	private int heightMill;
	private ArrayList<CatalogPart> consistsOfParts;
	private ArrayList<CatalogSet> setInventories;
	private ArrayList<CatalogPart> partInventories;
	private ArrayList<CatalogMinifig> minifigInventories;
	private ArrayList<CatalogGear> gearInventories;
	private ArrayList<CatalogBook> bookInventories;
	private String notes;

	private ArrayList<String> similarMold;
	private ArrayList<String> pairsWith;
	private ArrayList<String> similarPattern;
	private ArrayList<String> doorFrame;
	private ArrayList<String> door;
	private ArrayList<String> wheel;
	private ArrayList<String> tire;
	private ArrayList<String> sameSticker;
	private ArrayList<String> duplicate;
	private ArrayList<String> window;
	private ArrayList<String> windowFrame;

	private ArrayList<CatalogPart> similarMoldParts;
	private ArrayList<CatalogPart> pairsWithParts;
	private ArrayList<CatalogPart> similarPatternParts;
	private ArrayList<CatalogPart> doorFrameParts;
	private ArrayList<CatalogPart> doorParts;
	private ArrayList<CatalogPart> wheelParts;
	private ArrayList<CatalogPart> tireParts;
	private ArrayList<CatalogPart> sameStickerParts;
	private ArrayList<CatalogPart> duplicateParts;
	private ArrayList<CatalogPart> windowParts;
	private ArrayList<CatalogPart> windowFrameParts;

	private ArrayList<ColorCount> itemsForSale;
	private ArrayList<ColorCount> wantedLists;
	private ArrayList<ColorCount> priceGuides;
	private ArrayList<ColorCount> knownColorsBL;
	private ArrayList<String> knownColorsBLMenu;

	private ArrayList<String> knownColorsVerified;

	private Boolean hasInventory;

	String txtRep;

	private ArrayList<CatalogPartColored> coloredSubParts;



	public CatalogPart(Element e)
	{
		partNumber = e.getChildText("partnumber");
		DateFormat dateFormat = new SimpleDateFormat("EEE MMMM dd HH:mm:ss z yyyy");
		try
		{
			dateAdded = dateFormat.parse(e.getChildText("date"));
		}
		catch (ParseException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		altNumbers = new ArrayList<>();
		similarMold = new ArrayList<>();
		similarMoldParts = new ArrayList<>();
		similarPattern = new ArrayList<>();
		similarPatternParts = new ArrayList<>();
		sameSticker = new ArrayList<>();
		sameStickerParts = new ArrayList<>();
		duplicate = new ArrayList<>();
		duplicateParts = new ArrayList<>();
		door = new ArrayList<>();
		doorParts = new ArrayList<>();
		doorFrame = new ArrayList<>();
		doorFrameParts = new ArrayList<>();
		window = new ArrayList<>();
		windowParts = new ArrayList<>();
		windowFrame = new ArrayList<>();
		windowFrameParts = new ArrayList<>();
		tire = new ArrayList<>();
		tireParts = new ArrayList<>();
		wheel = new ArrayList<>();
		wheelParts = new ArrayList<>();
		pairsWith = new ArrayList<>();
		pairsWithParts = new ArrayList<>();
		itemsForSale = new ArrayList<>();
		wantedLists = new ArrayList<>();
		priceGuides = new ArrayList<>();
		knownColorsBL = new ArrayList<>();
		knownColorsBLMenu = new ArrayList<>();


		categoryID = Integer.parseInt(e.getChildText("categoryid"));
		categoryName = e.getChildText("categoryname");
		name = e.getChildText("name");
		firstYear = Integer.parseInt(e.getChildText("firstyear"));
		lastYear = Integer.parseInt(e.getChildText("lastyear"));
		weight = Double.parseDouble(e.getChildText("weight"));
		lengthStuds = Double.parseDouble(e.getChildText("lengthstuds"));
		widthStuds = Double.parseDouble(e.getChildText("widthstuds"));
		heightStuds = Double.parseDouble(e.getChildText("heightstuds"));
		lengthMill = Integer.parseInt(e.getChildText("lengthmill"));
		widthMill= Integer.parseInt(e.getChildText("widthmill"));
		heightMill = Integer.parseInt(e.getChildText("heightmill"));
		notes = e.getChildText("notes");

		if (e.getChild("hasInventory")!=null)
		{
			if (e.getChildText("hasInventory").equals("true"))
			{
				hasInventory = true;
			}
			else
			{
				hasInventory = false;
			}
		}
//		else
//		{
//			String path = "C:/Users/Owner/Documents/BLCrawler/Catalog/HTML/part_"+partNumber+".html";
//
//			ConsoleGUIModel.getDatabase().removeFromMasterXML(this);
//			try
//			{
//				byte[] encoded = Files.readAllBytes(Paths.get(path));
//				txtRep = new String(encoded, "UTF-8");
//			}
//			catch (UnsupportedEncodingException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			catch (IOException e1)
//			{
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//			if (txtRep.contains("www.bricklink.com/Catalog/ItemInv.asp?P"))
//			{
//				hasInventory = true;
//				System.out.println("Part "+partNumber+" has an inventory");
//				ConsoleGUIModel.getDatabase().appendToMasterXML(this);
//			}
//			else
//			{
//				hasInventory = false;
//				System.out.println("No inventory for part "+partNumber);
//			}
//			txtRep = "";
//
//
//
//
//
//		}

		if (e.getChild("similarmolds")!=null)
		{
			for(Element ele: e.getChild("similarmolds").getChildren())
			{
				similarMold.add(ele.getText());
				//System.out.println("Similar mold: "+ele.getText());
			}
		}

		if (e.getChild("pairswiths")!=null)
		{
			for(Element ele: e.getChild("pairswiths").getChildren())
			{
				pairsWith.add(ele.getText());
				//System.out.println("Pairs with: "+ele.getText());
			}
		}

		if (e.getChild("altnumbers")!=null)
		{
			for(Element ele: e.getChild("altnumbers").getChildren())
			{
				altNumbers.add(ele.getText());
				//System.out.println("Alt Number: "+ele.getText());
			}
		}

		if (e.getChild("similarpatterns")!=null)
		{
			for(Element ele: e.getChild("similarpatterns").getChildren())
			{
				similarPattern.add(ele.getText());
				//System.out.println("Similar Pattern: "+ele.getText());
			}
		}

		if (e.getChild("doorframes")!=null)
		{
			for(Element ele: e.getChild("doorframes").getChildren())
			{
				doorFrame.add(ele.getText());
				//System.out.println("Door Frame: "+ele.getText());
			}
		}

		if (e.getChild("doors")!=null)
		{
			for(Element ele: e.getChild("doors").getChildren())
			{
				door.add(ele.getText());
				//System.out.println("Door: "+ele.getText());
			}
		}

		if (e.getChild("wheels")!=null)
		{
			for(Element ele: e.getChild("wheels").getChildren())
			{
				wheel.add(ele.getText());
				//System.out.println("Wheel: "+ele.getText());
			}
		}

		if (e.getChild("tires")!=null)
		{
			for(Element ele: e.getChild("tires").getChildren())
			{
				tire.add(ele.getText());
				//System.out.println("Tire: "+ele.getText());
			}
		}

		if (e.getChild("samestickers")!=null)
		{
			for(Element ele: e.getChild("samestickers").getChildren())
			{
				sameSticker.add(ele.getText());
				//System.out.println("Same Sticker: "+ele.getText());
			}
		}

		if (e.getChild("duplicates")!=null)
		{
			for(Element ele: e.getChild("duplicates").getChildren())
			{
				duplicate.add(ele.getText());
				//System.out.println("Duplicate: "+ele.getText());
			}
		}


		if (e.getChild("itemsforsale")!=null)
		{
			for(Element ele: e.getChild("itemsforsale").getChildren())
			{
				itemsForSale.add(new ColorCount(Integer.valueOf(ele.getChildText("count")), ele.getChildText("color")));
			}
		}

		if (e.getChild("wantedlists")!=null)
		{
			for(Element ele: e.getChild("wantedlists").getChildren())
			{
				wantedLists.add(new ColorCount(Integer.valueOf(ele.getChildText("count")), ele.getChildText("color")));
			}
		}

		if (e.getChild("priceguides")!=null)
		{
			for(Element ele: e.getChild("priceguides").getChildren())
			{
				priceGuides.add(new ColorCount(Integer.valueOf(ele.getChildText("count")), ele.getChildText("color")));
				//System.out.println("Price guide with Color "+ele.getChildText("color")+" , count "+Integer.valueOf(ele.getChildText("count")));
			}
		}

		if (e.getChild("knowncolorsbl")!=null)
		{
			for(Element ele: e.getChild("knowncolorsbl").getChildren())
			{
				knownColorsBL.add(new ColorCount(Integer.valueOf(ele.getChildText("count")), ele.getChildText("color")));

			}
		}

		if (e.getChild("knowncolorsblmenu")!=null)
		{
			for(Element ele: e.getChild("knowncolorsblmenu").getChildren())
			{
				knownColorsBLMenu.add(ele.getText());
				//System.out.println(ele.getText());
			}
		}


		//System.out.println(knownColorsBLMenu);


	}

	public CatalogPart(String id)
	{
		String htmlpath;
		if (id.contains(".html"))
		{
			partNumber = id.substring(id.indexOf('_')+1, id.indexOf(".html"));
			htmlpath = id;
		}
		else
		{
			partNumber = id;
			htmlpath = "C:/Users/Owner/Documents/BLCrawler/Catalog/HTML/part_"+id+".html";
		}


		dateAdded = new Date();

		categoryID = 0;
		categoryName = "";
		name = "";
		firstYear = 0;
		lastYear = 0;
		weight = 0;
		lengthStuds = 0;
		widthStuds = 0;
		heightStuds = 0;
		lengthMill = 0;
		widthMill = 0;
		heightMill = 0;
		notes = "";



		txtRep = "";
		altNumbers = new ArrayList<>();
		similarMold = new ArrayList<>();
		similarMoldParts = new ArrayList<>();
		similarPattern = new ArrayList<>();
		similarPatternParts = new ArrayList<>();
		sameSticker = new ArrayList<>();
		sameStickerParts = new ArrayList<>();
		duplicate = new ArrayList<>();
		duplicateParts = new ArrayList<>();
		door = new ArrayList<>();
		doorParts = new ArrayList<>();
		doorFrame = new ArrayList<>();
		doorFrameParts = new ArrayList<>();
		window = new ArrayList<>();
		windowParts = new ArrayList<>();
		windowFrame = new ArrayList<>();
		windowFrameParts = new ArrayList<>();
		tire = new ArrayList<>();
		tireParts = new ArrayList<>();
		wheel = new ArrayList<>();
		wheelParts = new ArrayList<>();
		pairsWith = new ArrayList<>();
		pairsWithParts = new ArrayList<>();
		itemsForSale = new ArrayList<>();
		wantedLists = new ArrayList<>();
		priceGuides = new ArrayList<>();
		knownColorsBL = new ArrayList<>();
		knownColorsBLMenu = new ArrayList<>();

		if (id.contains(".html"))
		{
			buildFromFile(htmlpath);
		}
		else
		{
			buildFromFile("C:/Users/Owner/Documents/BLCrawler/Catalog/part_database.xml");
		}

	}

	public void buildFromHtml(String path)
	{
		txtRep = "";

		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			txtRep = new String(encoded, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (txtRep.contains(" data-ce-key"))
		{
			System.out.println("data-ce count: "+StringUtils.countMatches(txtRep, " data-ce-key"));

		}

		//Clear the seemingly random/useless data ce keys that appear in tags and screw up parses
		while (txtRep.contains(" data-ce-key"))
		{
			String subRep = txtRep.substring(txtRep.indexOf(" data-ce-key"));
			subRep = subRep.substring(0,ordinalIndexOf(subRep, 2)+1);
			//System.out.println(subRep);
			txtRep = txtRep.replace(subRep, "");

			//System.out.println("cleared "+subRep+" from part "+partNumber);
		}

		generateCategoryID();
		generateBLBackgroundID();
		generateCategoryName();
		generateName();
		generateInventory();
		generateAltIDs();
		generateFirstYear();
		generateLastYear();
		generateWeight();
		generateDimensions();
		generateNotes();
		generateSimilarMolds();
		generateSimilarPatterns();
		generateSameStickers();
		generateDuplicates();
		generateDoors();
		generateDoorFrames();
		generateWindows();
		generateWindowFrames();
		generateTires();
		generateWheels();
		generatePairedParts();
		generateItemsForSale();
		generateWantedListColors();
		generatePriceGuideColors();
		generateKnownColors();
		generateMenuColors();
		

		txtRep = "";
	}

	public void buildFromXml(String path)
	{

	}

	public void buildFromFile(String path)
	{
		if (path.contains(".html"))
		{
			buildFromHtml(path);
		}
		else
		{
			buildFromXml(path);
		}
	}

	public Element buildXML()
	{
		Element part = new Element("part");
		part.setAttribute("id", partNumber);

		Element PartNumber = new Element("partnumber");
		PartNumber.setText(partNumber);
		part.addContent(PartNumber);
		

        Element blidInternal = new Element("blidInternal");
        blidInternal.setText(String.valueOf(bricklinkInternalID));
        part.addContent(blidInternal);

		Element Date = new Element("date");
		Date.setText(dateAdded.toString());
		part.addContent(Date);

		Element CategoryID = new Element("categoryid");
		CategoryID.setText(String.valueOf(categoryID));
		part.addContent(CategoryID);

		Element CategoryName = new Element("categoryname");
		CategoryName.setText(categoryName);
		part.addContent(CategoryName);

		Element Name = new Element("name");
		Name.setText(name);
		part.addContent(Name);

		if (altNumbers.size()!=0)
		{
			Element AltNumbers = new Element("altnumbers");
			for (String number : altNumbers)
			{
				AltNumbers.addContent(new Element("altnumber").setText(number));
			}
			part.addContent(AltNumbers);
		}

		Element FirstYear = new Element("firstyear");
		FirstYear.setText(String.valueOf(firstYear));
		part.addContent(FirstYear);


		Element LastYear = new Element("lastyear");
		LastYear.setText(String.valueOf(lastYear));
		part.addContent(LastYear);

		Element Weight = new Element("weight");
		Weight.setText(String.valueOf(weight));
		part.addContent(Weight);

		Element LengthStuds = new Element("lengthstuds");
		LengthStuds.setText(String.valueOf(lengthStuds));
		part.addContent(LengthStuds);

		Element WidthStuds = new Element("widthstuds");
		WidthStuds.setText(String.valueOf(widthStuds));
		part.addContent(WidthStuds);

		Element HeightStuds = new Element("heightstuds");
		HeightStuds.setText(String.valueOf(heightStuds));
		part.addContent(HeightStuds);

		Element LengthMill = new Element("lengthmill");
		LengthMill.setText(String.valueOf(lengthMill));
		part.addContent(LengthMill);

		Element WidthMill = new Element("widthmill");
		WidthMill.setText(String.valueOf(widthMill));
		part.addContent(WidthMill);

		Element HeightMill = new Element("heightmill");
		HeightMill.setText(String.valueOf(heightMill));
		part.addContent(HeightMill);

		Element Notes = new Element("notes");
		Notes.setText(notes);
		part.addContent(Notes);

		Element Inventory = new Element("hasInventory");
		if (hasInventory)
		{
			Inventory.setText("true");
		}
		else
		{
			Inventory.setText("false");
		}
		part.addContent(Inventory);

		if (similarMold.size()!=0)
		{
			Element SimilarMolds = new Element("similarmolds");
			for (String number : similarMold)
			{
				SimilarMolds.addContent(new Element("similarmold").setText(number));
			}
			part.addContent(SimilarMolds);
		}

		if (pairsWith.size()!=0)
		{
			Element PairsWiths = new Element("pairswiths");
			for (String number : pairsWith)
			{
				PairsWiths.addContent(new Element("pairswith").setText(number));
			}
			part.addContent(PairsWiths);
		}

		if (similarPattern.size()!=0)
		{
			Element SimilarPatterns = new Element("similarpatterns");
			for (String number : similarPattern)
			{
				SimilarPatterns.addContent(new Element("similarpattern").setText(number));
			}
			part.addContent(SimilarPatterns);
		}

		if (doorFrame.size()!=0)
		{
			Element DoorFrames = new Element("doorframes");
			for (String number : doorFrame)
			{
				DoorFrames.addContent(new Element("doorframe").setText(number));
			}
			part.addContent(DoorFrames);
		}

		if (door.size()!=0)
		{
			Element Doors = new Element("doors");
			for (String number : door)
			{
				Doors.addContent(new Element("door").setText(number));
			}
			part.addContent(Doors);
		}

		if (windowFrame.size()!=0)
		{
			Element WindowFrames = new Element("windowframes");
			for (String number : windowFrame)
			{
				WindowFrames.addContent(new Element("windowframe").setText(number));
			}
			part.addContent(WindowFrames);
		}

		if (window.size()!=0)
		{
			Element Windows = new Element("windows");
			for (String number : window)
			{
				Windows.addContent(new Element("window").setText(number));
			}
			part.addContent(Windows);
		}

		if (wheel.size()!=0)
		{
			Element Wheels = new Element("wheels");
			for (String number : wheel)
			{
				Wheels.addContent(new Element("wheel").setText(number));
			}
			part.addContent(Wheels);
		}

		if (tire.size()!=0)
		{
			Element Tires = new Element("tires");
			for (String number : tire)
			{
				Tires.addContent(new Element("tire").setText(number));
			}
			part.addContent(Tires);
		}

		if (sameSticker.size()!=0)
		{
			Element SameStickers = new Element("samestickers");
			for (String number : sameSticker)
			{
				SameStickers.addContent(new Element("samesticker").setText(number));
			}
			part.addContent(SameStickers);
		}

		if (duplicate.size()!=0)
		{
			Element Duplicates = new Element("duplicates");
			for (String number : duplicate)
			{
				Duplicates.addContent(new Element("duplicate").setText(number));
			}
			part.addContent(Duplicates);
		}

		if (itemsForSale.size()!=0)
		{
			Element ItemsForSale = new Element("itemsforsale");
			for (ColorCount number : itemsForSale)
			{
				Element Color = new Element("colorforsale");
				Color.addContent(new Element("color").setText(number.getColor()));
				Color.addContent(new Element("count").setText(String.valueOf(number.getCount())));

				ItemsForSale.addContent(Color);
			}
			part.addContent(ItemsForSale);
		}

		if (wantedLists.size()!=0)
		{
			Element WantedLists = new Element("wantedlists");
			for (ColorCount number : wantedLists)
			{
				Element Color = new Element("colorwanted");
				Color.addContent(new Element("color").setText(number.getColor()));
				Color.addContent(new Element("count").setText(String.valueOf(number.getCount())));

				WantedLists.addContent(Color);
			}
			part.addContent(WantedLists);
		}

		if (priceGuides.size()!=0)
		{
			Element PriceGuides = new Element("priceguides");
			for (ColorCount number : priceGuides)
			{
				Element Color = new Element("colorpriceguide");
				Color.addContent(new Element("color").setText(number.getColor()));
				Color.addContent(new Element("count").setText(String.valueOf(number.getCount())));

				PriceGuides.addContent(Color);
			}
			part.addContent(PriceGuides);
		}

		if (knownColorsBL.size()!=0)
		{
			Element KnownColors = new Element("knowncolorsbl");
			for (ColorCount number : knownColorsBL)
			{
				Element Color = new Element("knowncolorbl");
				Color.addContent(new Element("color").setText(number.getColor()));
				Color.addContent(new Element("count").setText(String.valueOf(number.getCount())));

				KnownColors.addContent(Color);
			}
			part.addContent(KnownColors);
		}

		if (knownColorsBLMenu.size()!=0)
		{
			Element KnownColors = new Element("knowncolorsblmenu");
			for (String number : knownColorsBLMenu)
			{
				KnownColors.addContent(new Element("color").setText(number));
			}
			part.addContent(KnownColors);
		}







		//System.out.println(part.getChildren());





		return part;


	}
	
   public void generateBLBackgroundID()
   {
       //System.out.println(index);
       //String IDSubstring = txtRep.substring(index, index+10);

       String IDSubstring = txtRep.substring(txtRep.indexOf("_var_item"));
       IDSubstring = IDSubstring.substring(0, IDSubstring.indexOf(","));
       
       

       //System.out.println("Say something else");
       System.out.println("Pre-shorten substring: "+IDSubstring);
       IDSubstring = IDSubstring.replaceAll("[^0-9]","");

       //System.out.println("Post-shorten substring: "+IDSubstring);
       try
        {   
            bricklinkInternalID = Integer.valueOf(IDSubstring);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Parsing exception in part "+partNumber+"at blInternalID new.");
        }

        //System.out.println("Part number "+partNumber+" has an internal id of "+bricklinkInternalID);
    }

	public void generateCategoryID()
	{
		try
		{
			String CatSubstring = txtRep.substring(txtRep.indexOf("catString"), txtRep.indexOf("catString")+15);
			categoryID = Integer.valueOf(CatSubstring.substring(10, CatSubstring.indexOf('"')));
		}
		catch (NumberFormatException e)
		{
			System.out.println("Parsing exception in part "+partNumber+"at categoryID.");
		}

		//System.out.println("Part number "+partNumber+" has a category ID of "+categoryID);
	}
	public void generateCategoryName()
	{
		try
		{
			String CatSubstring = txtRep.substring(txtRep.indexOf("catString"));
			categoryName = CatSubstring.substring(CatSubstring.indexOf(">")+1, CatSubstring.indexOf("</"));
		}
		catch (NumberFormatException e)
		{
			System.out.println("Parsing exception in part "+partNumber+"at category name.");
		}
		//System.out.println("Part number "+partNumber+" has a category of "+categoryName);
	}

	public void generateInventory()
	{
		if (txtRep.indexOf("_var_has_inventory		= \"Y\"")!=-1)
		{
			hasInventory = true;
			System.out.println("Part "+partNumber+" has an inventory");
		}
		else
		{
			hasInventory = false;
			//System.out.println("No inventory for part "+partNumber);
		}
	}

	public void generateName()
	{
		try
		{
			String NameSubstring = txtRep.substring(txtRep.indexOf("<title"), txtRep.indexOf("</title>"));
			name = NameSubstring.substring(NameSubstring.indexOf("Lego")+5, NameSubstring.indexOf("[")-1);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at name. Name substring is "+
					txtRep.substring(txtRep.indexOf("<title"), txtRep.indexOf("</title>")));
		}

		//System.out.println("Part number "+partNumber+" has a name of "+name);
	}

	public void generateAltIDs()
	{
		if (txtRep.contains("Alternate Item No:"))
		{
			String altIDSubstring = txtRep.substring(txtRep.indexOf("Alternate Item No:")+26);
			altIDSubstring = altIDSubstring.substring(altIDSubstring.indexOf('>')+1,altIDSubstring.indexOf('<'));

			while(!altIDSubstring.equals(""))
			{
				if (altIDSubstring.contains(","))
				{
					altNumbers.add(altIDSubstring.substring(0,altIDSubstring.indexOf(',')));

					//System.out.println("Part number "+partNumber+" has an alternative ID of of "+
					//		altIDSubstring.substring(0,altIDSubstring.indexOf(',')));

					altIDSubstring = altIDSubstring.substring(0,altIDSubstring.indexOf(','));

				}
				else
				{
					altNumbers.add(altIDSubstring);

					//System.out.println("Part number "+partNumber+" has an alternative ID of of "+
					//		altIDSubstring);
					altIDSubstring = "";
				}
			}

		}
	}

	public void generateFirstYear()
	{
		try
		{
			if (txtRep.contains("Years Released:"))
			{
				String FirstSubstring = txtRep.substring(txtRep.indexOf("Years Released:"));
				FirstSubstring = FirstSubstring.substring(0, FirstSubstring.indexOf("</span>"));
				FirstSubstring = FirstSubstring.substring(FirstSubstring.length()-4,FirstSubstring.length());

				if (!FirstSubstring.contains("?"))
				{
					firstYear = Integer.valueOf(FirstSubstring);
				}
				else
				{
					firstYear = 0;
				}
			}
			else if (txtRep.contains("Year Released:"))
			{
				String FirstSubstring = txtRep.substring(txtRep.indexOf("Year Released:"));
				FirstSubstring = FirstSubstring.substring(0, FirstSubstring.indexOf("</span>"));
				FirstSubstring = FirstSubstring.substring(FirstSubstring.length()-4,FirstSubstring.length());

				if (!FirstSubstring.contains("?"))
				{
					firstYear = Integer.valueOf(FirstSubstring);
				}
				else
				{
					firstYear = 0;
				}
			}
			else
			{
				firstYear = 0;
			}


		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at First Year.");
		}

		//System.out.println("Part number "+partNumber+" has a first year of "+firstYear);
	}

	public void generateLastYear()
	{
		try
		{
			if (txtRep.contains("Years Released:"))
			{
				String LastSubstring = txtRep.substring(txtRep.indexOf("Years Released:"));
				LastSubstring = LastSubstring.substring(0, LastSubstring.indexOf("<br"));
				LastSubstring = LastSubstring.substring(LastSubstring.length()-4,LastSubstring.length());

				if (!LastSubstring.contains("?"))
				{
					lastYear = Integer.valueOf(LastSubstring);
				}
				else
				{
					lastYear = 0;
				}
			}
			else if (txtRep.contains("Year Released:"))
			{
				lastYear = firstYear;
			}
			else
			{
				lastYear = 0;
			}


		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Last Year.");
		}

		//System.out.println("Part number "+partNumber+" has date range of "+firstYear+" to "+lastYear);
	}

	public void generateWeight()
	{
		try
		{
			if (txtRep.contains("Weight:"))
			{
				String WeightSubstring = txtRep.substring(txtRep.indexOf("Weight:"));
				WeightSubstring = WeightSubstring.substring(0, WeightSubstring.indexOf("</span>"));
				WeightSubstring = WeightSubstring.substring(WeightSubstring.indexOf('>')+1,WeightSubstring.length());

				if (!WeightSubstring.contains("?"))
				{
					weight = Double.valueOf(WeightSubstring.substring(0, WeightSubstring.indexOf('g')));
				}
				else
				{
					//System.out.println("Weight is unlisted for part "+partNumber);
					weight = 0;
				}
			}
			else
			{
				System.out.println("Unable to ID weight for part "+partNumber);
				weight = 0;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at weight.");
		}
		//System.out.println("Part number "+partNumber+" has weight of "+weight);

	}

	public void generateDimensions()
	{
		String lengthSubstring = "0";
		String widthSubstring = "0";
		String heightSubstring = "0";
		try
		{
			if (txtRep.contains("Size:"))
			{
				if (txtRep.contains("Size: ?"))
				{

				}
				else
				{

					String SizeSubstring = txtRep.substring(txtRep.indexOf("Size:"));
					SizeSubstring = SizeSubstring.substring(0, SizeSubstring.indexOf("</span>"));
					SizeSubstring = SizeSubstring.substring(SizeSubstring.indexOf('>')+1,SizeSubstring.length());

					if (!SizeSubstring.contains("?"))
					{
						if (SizeSubstring.contains("in studs"))
						{
							SizeSubstring = SizeSubstring.substring(0, SizeSubstring.indexOf("in studs")-1);
							lengthSubstring = SizeSubstring.substring(0, SizeSubstring.indexOf('x'));
							if (SizeSubstring.substring(SizeSubstring.indexOf('x')+1, SizeSubstring.length()).contains("x"))
							{
								widthSubstring = SizeSubstring.substring(SizeSubstring.indexOf('x')+1, SizeSubstring.length());
								String widthHeight = widthSubstring;
								widthSubstring = widthSubstring.substring(0,widthSubstring.indexOf('x'));
								heightSubstring = widthHeight.substring(widthHeight.indexOf('x')+1, widthHeight.length());
							}
							else
							{
								widthSubstring = SizeSubstring.substring(SizeSubstring.indexOf('x')+1, SizeSubstring.length());
							}


						}
						else
						{
							System.out.println("Unknown size formatting for part "+partNumber);
						}
					}
					else
					{
						//System.out.println("Size is unlisted for part "+partNumber);
					}
				}
			}
			else
			{
				System.out.println("Unable to ID size for part "+partNumber);
			}

			lengthStuds = Double.valueOf(lengthSubstring);
			widthStuds = Double.valueOf(widthSubstring);
			heightStuds = Double.valueOf(heightSubstring);

			if (widthStuds==0&&lengthStuds!=0)
			{
				widthStuds = 0.33;
			}

			if (heightStuds == 0 && (lengthStuds!=0&&widthStuds!=0))
			{
				heightStuds = 0.33;
			}

			lengthMill = (int)(lengthStuds*8);
			widthMill = (int)(widthStuds*8);
			heightMill = (int)(heightStuds*9.6);

			if (lengthStuds!=0)
			{
				//System.out.println("part "+partNumber+" has dimensions of "
				//		+lengthStuds+" x "+widthStuds+" x "+heightStuds);
			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at size.");
		}


	}

	public void generateNotes()
	{
		try
		{
			if (txtRep.contains("Additional Notes:"))
			{
				String NotesSubstring = txtRep.substring(txtRep.indexOf("Additional Notes:"));
				NotesSubstring = NotesSubstring.substring(NotesSubstring.indexOf("</strong>")+10, NotesSubstring.indexOf("</span>"));
				NotesSubstring = NotesSubstring.substring(NotesSubstring.indexOf(">")+1, NotesSubstring.length());
				notes = NotesSubstring;

			}
			else
			{

				notes = "";
			}
			if (!notes.equals(""))
			{
				if (notes.equals("<!-- Test -->"))
				{
					//System.out.println("Part "+partNumber+" has additional notes of "+notes+", displaying weird test behaviour of 3001");
				}
				//System.out.println("Part "+partNumber+" has additional notes of "+notes);
			}
			else
			{
				//System.out.println("Part "+partNumber+" does not have additional notes");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at notes.");
		}


	}

	public void generateSimilarMolds()
	{
		try
		{
			if (txtRep.contains("This Item is similar in mold type to the following Item"))
			{
				String MoldsSubstring = txtRep.substring(txtRep.indexOf("following Item"));
				MoldsSubstring = MoldsSubstring.substring(MoldsSubstring.indexOf("<ul>")+4, MoldsSubstring.indexOf("</ul>"));
				System.out.println(MoldsSubstring);
				while (!MoldsSubstring.equals(""))
				{
					if (MoldsSubstring.contains("P="))
					{
						String partID = MoldsSubstring.substring(MoldsSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							similarMoldParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						similarMold.add(partID);
						//System.out.println("Part ID "+partID+" added as similar mold for part "+partNumber);
						MoldsSubstring = MoldsSubstring.substring(MoldsSubstring.indexOf("P=")+2);
					}
					else
					{
						MoldsSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at notes.");
		}


	}

	public void generateSimilarPatterns()
	{
		try
		{
			if (txtRep.contains("This Item is similar in pattern to the following Item"))
			{
				String PatternsSubstring = txtRep.substring(txtRep.indexOf("following Item"));
				PatternsSubstring = PatternsSubstring.substring(PatternsSubstring.indexOf("<ul>")+4, PatternsSubstring.indexOf("</ul>"));
				System.out.println(PatternsSubstring);
				while (!PatternsSubstring.equals(""))
				{
					if (PatternsSubstring.contains("P="))
					{
						String partID = PatternsSubstring.substring(PatternsSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							similarPatternParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						similarPattern.add(partID);
						//System.out.println("Part ID "+partID+" added as similar pattern for part "+partNumber);
						PatternsSubstring = PatternsSubstring.substring(PatternsSubstring.indexOf("P=")+2);
					}
					else
					{
						PatternsSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at similarmold.");
		}


	}

	public void generateSameStickers()
	{
		try
		{
			if (txtRep.contains("This Item is the same as the following Item"))
			{
				String SameStickerSubstring = txtRep.substring(txtRep.indexOf("following Item"));
				SameStickerSubstring = SameStickerSubstring.substring(SameStickerSubstring.indexOf("<ul>")+4, SameStickerSubstring.indexOf("</ul>"));
				System.out.println(SameStickerSubstring);
				while (!SameStickerSubstring.equals(""))
				{
					if (SameStickerSubstring.contains("P="))
					{
						String partID = SameStickerSubstring.substring(SameStickerSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							sameStickerParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						sameSticker.add(partID);
						//System.out.println("Part ID "+partID+" added as same sticker for part "+partNumber);
						SameStickerSubstring = SameStickerSubstring.substring(SameStickerSubstring.indexOf("P=")+2);
					}
					else
					{
						SameStickerSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at samesticker.");
		}


	}

	public void generateDuplicates()
	{
		try
		{
			if (txtRep.contains("This Item is a duplicate entry for the following Item"))
			{
				String DuplicateSubstring = txtRep.substring(txtRep.indexOf("following Item"));
				DuplicateSubstring = DuplicateSubstring.substring(DuplicateSubstring.indexOf("<ul>")+4, DuplicateSubstring.indexOf("</ul>"));
				System.out.println(DuplicateSubstring);
				while (!DuplicateSubstring.equals(""))
				{
					if (DuplicateSubstring.contains("P="))
					{
						String partID = DuplicateSubstring.substring(DuplicateSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							duplicateParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						duplicate.add(partID);
						//System.out.println("Part ID "+partID+" added as duplicate for part "+partNumber);
						DuplicateSubstring = DuplicateSubstring.substring(DuplicateSubstring.indexOf("P=")+2);
					}
					else
					{
						DuplicateSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at duplicate.");
		}


	}

	public void generateDoors()
	{
		try
		{
			if (txtRep.contains("This Door Frame fits with the following Door"))
			{
				String DoorSubstring = txtRep.substring(txtRep.indexOf("following Door"));
				DoorSubstring = DoorSubstring.substring(DoorSubstring.indexOf("<ul>")+4, DoorSubstring.indexOf("</ul>"));
				System.out.println(DoorSubstring);
				while (!DoorSubstring.equals(""))
				{
					if (DoorSubstring.contains("P="))
					{
						String partID = DoorSubstring.substring(DoorSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							doorParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						door.add(partID);
						//System.out.println("Part ID "+partID+" added as Door for part "+partNumber);
						DoorSubstring = DoorSubstring.substring(DoorSubstring.indexOf("P=")+2);
					}
					else
					{
						DoorSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Door.");
		}


	}

	public void generateDoorFrames()
	{
		try
		{
			if (txtRep.contains("This Door fits with the following Door Frame"))
			{
				String DoorSubstring = txtRep.substring(txtRep.indexOf("following Door Frame")+10);
				DoorSubstring = DoorSubstring.substring(DoorSubstring.indexOf("<ul>")+4, DoorSubstring.indexOf("</ul>"));
				System.out.println(DoorSubstring);
				while (!DoorSubstring.equals(""))
				{
					if (DoorSubstring.contains("P="))
					{
						String partID = DoorSubstring.substring(DoorSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							doorFrameParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						doorFrame.add(partID);
						//System.out.println("Part ID "+partID+" added as Door Frame for part "+partNumber);
						DoorSubstring = DoorSubstring.substring(DoorSubstring.indexOf("P=")+2);
					}
					else
					{
						DoorSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Door Frame.");
		}
	}

	public void generateWindows()
	{
		try
		{
			if (txtRep.contains("This Window fits with the following Glass"))
			{
				String WindowSubstring = txtRep.substring(txtRep.indexOf("following Glass"));
				WindowSubstring = WindowSubstring.substring(WindowSubstring.indexOf("<ul>")+4, WindowSubstring.indexOf("</ul>"));
				System.out.println(WindowSubstring);
				while (!WindowSubstring.equals(""))
				{
					if (WindowSubstring.contains("P="))
					{
						String partID = WindowSubstring.substring(WindowSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							windowParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						window.add(partID);
						//System.out.println("Part ID "+partID+" added as Window Frame for part "+partNumber);
						WindowSubstring = WindowSubstring.substring(WindowSubstring.indexOf("P=")+2);
					}
					else
					{
						WindowSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Window Frame.");
		}
	}

	public void generateWindowFrames()
	{
		try
		{
			if (txtRep.contains("This Glass fits with the following Window"))
			{
				String WindowSubstring = txtRep.substring(txtRep.indexOf("following Window"));
				WindowSubstring = WindowSubstring.substring(WindowSubstring.indexOf("<ul>")+4, WindowSubstring.indexOf("</ul>"));
				System.out.println(WindowSubstring);
				while (!WindowSubstring.equals(""))
				{
					if (WindowSubstring.contains("P="))
					{
						String partID = WindowSubstring.substring(WindowSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							windowFrameParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						windowFrame.add(partID);
						//System.out.println("Part ID "+partID+" added as Window Frame for part "+partNumber);
						WindowSubstring = WindowSubstring.substring(WindowSubstring.indexOf("P=")+2);
					}
					else
					{
						WindowSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Window Frame.");
		}
	}



	public void generateTires()
	{
		try
		{
			if (txtRep.contains("This Wheel fits with the following"))
			{
				String TireSubstring = txtRep.substring(txtRep.indexOf("following Tire "));
				TireSubstring = TireSubstring.substring(TireSubstring.indexOf("<ul>")+4, TireSubstring.indexOf("</ul>"));
				System.out.println(TireSubstring);
				while (!TireSubstring.equals(""))
				{
					if (TireSubstring.contains("P="))
					{
						String partID = TireSubstring.substring(TireSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							tireParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						tire.add(partID);
						//System.out.println("Part ID "+partID+" added as Tire for part "+partNumber);
						TireSubstring = TireSubstring.substring(TireSubstring.indexOf("P=")+2);
					}
					else
					{
						TireSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Tire.");
		}
	}

	public void generateWheels()
	{
		try
		{
			if (txtRep.contains("Tread fits with the following Wheel"))
			{
				String WheelSubstring = txtRep.substring(txtRep.indexOf("following Wheel"));
				WheelSubstring = WheelSubstring.substring(WheelSubstring.indexOf("<ul>")+4, WheelSubstring.indexOf("</ul>"));
				System.out.println(WheelSubstring);
				while (!WheelSubstring.equals(""))
				{
					if (WheelSubstring.contains("P="))
					{
						String partID = WheelSubstring.substring(WheelSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							wheelParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						wheel.add(partID);
						//System.out.println("Part ID "+partID+" added as Wheel for part "+partNumber);
						WheelSubstring = WheelSubstring.substring(WheelSubstring.indexOf("P=")+2);
					}
					else
					{
						WheelSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Wheel.");
		}
	}

	public void generatePairedParts()
	{
		try
		{
			if (txtRep.contains("This Item pairs with the following Item"))
			{
				String PairSubstring = txtRep.substring(txtRep.indexOf("pairs with the"));
				PairSubstring = PairSubstring.substring(PairSubstring.indexOf("<ul>")+4, PairSubstring.indexOf("</ul>"));
				System.out.println(PairSubstring);
				while (!PairSubstring.equals(""))
				{
					if (PairSubstring.contains("P="))
					{
						String partID = PairSubstring.substring(PairSubstring.indexOf("P=")+2);
						partID = partID.substring(0,partID.indexOf('"'));

						if (ConsoleGUIModel.getDatabase().partExists(partID+".html"))
						{
							pairsWithParts.add(ConsoleGUIModel.getDatabase().getPart(partID+".html"));
							//System.out.println("Part ID "+partID+" added to object based list for part "+partNumber);
						}
						else
						{
							//System.out.println("Part ID "+partID+" does not exist, standby for correction");
						}

						pairsWith.add(partID);
						//System.out.println("Part ID "+partID+" added as Pair for part "+partNumber);
						PairSubstring = PairSubstring.substring(PairSubstring.indexOf("P=")+2);
					}
					else
					{
						PairSubstring = "";
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Pair.");
		}
	}

	public void generateItemsForSale()
	{
		try
		{
			String ForSaleSubstring = txtRep.substring(txtRep.indexOf("Lots For Sale:"));
			ForSaleSubstring = ForSaleSubstring.substring(ForSaleSubstring.indexOf("Lots For Sale:"), ForSaleSubstring.indexOf("</td>"));

			while (!ForSaleSubstring.equals(""))
			{
				if (ForSaleSubstring.contains("span"))
				{
					String Color = ForSaleSubstring.substring(ForSaleSubstring.indexOf("<span"),ForSaleSubstring.indexOf("</span><br")+11);
					ForSaleSubstring = ForSaleSubstring.substring(ForSaleSubstring.indexOf("</span><br")+5);
					String Count = Color;
					//System.out.println(Color);
					Color = Color.substring(Color.indexOf("showInventoryWithColor( ")+24,Color.indexOf("); return")-1);

					Count = Count.substring(Count.indexOf("</a>")+6, Count.indexOf("</span><br")-1);

					int colorID = Integer.valueOf(Color);
					int colorCount = Integer.valueOf(Count);
					ColorCount colorObj=new ColorCount(colorCount, colorID);
					itemsForSale.add(colorObj);

					//System.out.println("Part "+partNumber+" has "+colorCount+" items for sale in "+colorObj.getColor());
				}
				else
				{
					ForSaleSubstring = "";
				}

			}

		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Items For Sale.");
		}
	}

	public void generateWantedListColors()
	{
		try
		{
			String WantedSubstring = txtRep.substring(txtRep.indexOf("On Wanted List:"));
			WantedSubstring = WantedSubstring.substring(WantedSubstring.indexOf("On Wanted List:")+15, WantedSubstring.indexOf("</td>"));

			while (!WantedSubstring.equals(""))
			{
				if (WantedSubstring.substring(0,50).contains("span"))
				{
					String Color = WantedSubstring.substring(WantedSubstring.indexOf("><span style"),WantedSubstring.indexOf("</span><br")+11);
					WantedSubstring = WantedSubstring.substring(WantedSubstring.indexOf("</span><br")+5);
					String Count = Color;
					//System.out.println(Color);
					Color = Color.substring(Color.indexOf("blue;")+7,Color.indexOf("</span> "));

					Count = Count.substring(Count.indexOf("</span> (")+9, Count.indexOf(")</span><br"));

					int colorCount = Integer.valueOf(Count);
					ColorCount colorObj=new ColorCount(colorCount, Color);
					wantedLists.add(colorObj);

					//System.out.println("Part "+partNumber+" has "+colorCount+" items on wanted lists in "+Color);
				}
				else
				{
					WantedSubstring = "";
				}
			}

			//System.out.println(wantedLists);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Wanted Lists.");
		}
	}

	public void generatePriceGuideColors()
	{
		try
		{
			String PriceGuideSubstring = txtRep.substring(txtRep.indexOf("Price Guide Info:"));
			PriceGuideSubstring = PriceGuideSubstring.substring(PriceGuideSubstring.indexOf("Price Guide Info:")+15, PriceGuideSubstring.indexOf("</td>"));

			while (!PriceGuideSubstring.equals(""))
			{
				if (PriceGuideSubstring.contains("http://www.bricklink.com/Catalog/PG"))
				{
					String Color = PriceGuideSubstring.substring(PriceGuideSubstring.indexOf("colorID="),PriceGuideSubstring.indexOf("</span><br")+11);
					PriceGuideSubstring = PriceGuideSubstring.substring(PriceGuideSubstring.indexOf("colorID=")+8);
					if (PriceGuideSubstring.contains("http://www.bricklink.com/Catalog/PG"))
					{
						PriceGuideSubstring = PriceGuideSubstring.substring(PriceGuideSubstring.indexOf("http://www.bricklink.com/Catalog/PG"));
					}

					String Count = Color;
					//System.out.println(Color);
					Color = Color.substring(8,Color.indexOf('"'));

					Count = Count.substring(Count.indexOf("</a>")+6, Count.indexOf(")</span><br"));

					int colorCount = Integer.valueOf(Count);
					int colorID = Integer.valueOf(Color);
					ColorCount colorObj=new ColorCount(colorCount, colorID);
					priceGuides.add(colorObj);

					//System.out.println("Part "+partNumber+" has "+colorCount+" sales on price guide in "+colorObj.getColor());
				}
				else
				{
					PriceGuideSubstring = "";
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Price Guide.");
		}
	}

	public void generateKnownColors()
	{
		try
		{
			String KnownSubstring = txtRep.substring(txtRep.indexOf("Known Colors:"));
			KnownSubstring = KnownSubstring.substring(KnownSubstring.indexOf("Known Colors:")+15, KnownSubstring.indexOf("</td>"));

			while (!KnownSubstring.equals(""))
			{
				if (KnownSubstring.contains("http://www.bricklink.com/Catalog/ItemIn"))
				{
					String Color = KnownSubstring.substring(KnownSubstring.indexOf("colorID="),KnownSubstring.indexOf("</span><br")+11);
					KnownSubstring = KnownSubstring.substring(KnownSubstring.indexOf("colorID=")+8);
					if (KnownSubstring.contains("http://www.bricklink.com/Catalog/ItemIn"))
					{
						KnownSubstring = KnownSubstring.substring(KnownSubstring.indexOf("http://www.bricklink.com/Catalog/ItemIn"));
					}

					String Count = Color;
					//System.out.println(Color);
					Color = Color.substring(8,Color.indexOf('&'));

					Count = Count.substring(Count.indexOf("</a>")+6, Count.indexOf(")</span><br"));

					int colorCount = Integer.valueOf(Count);
					int colorID = Integer.valueOf(Color);
					ColorCount colorObj=new ColorCount(colorCount, colorID);
					knownColorsBL.add(colorObj);

					//System.out.println("Part "+partNumber+" is known to exist in "+colorCount+" sets in color "+colorObj.getColor());
				}
				else
				{
					KnownSubstring = "";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Known Colors.");
		}
//		if (knownColorsBL.size()!=0)
//		{
//			ConsoleGUIModel.getDatabase().incrementColoredParts(knownColorsBL.size());
//		}
//		else
//		{
//			System.out.println("No known colors for part "+partNumber);
//		}

	}

	public void generateMenuColors()
	{
		try
		{
			String MenuSubstring = txtRep.substring(txtRep.indexOf("div class=\"pciSelectColorDropdownList\""));
			MenuSubstring = MenuSubstring.substring(0,MenuSubstring.indexOf("</td>"));
			//System.out.println(MenuSubstring);
			for(String color: ConsoleGUIModel.getDatabase().getColormap().getColorNames())
			{
				//System.out.println(color);
				if(MenuSubstring.contains("data-name=\""+color))
			   {

				   knownColorsBLMenu.add(color);
				   //System.out.println("Part "+partNumber+" has color "+color+" in the menu");
			   }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parsing exception in part "+partNumber+"at Menu Colors.");
		}

		ArrayList<String> i = new ArrayList<>();
		i.addAll(knownColorsBLMenu);
		for (ColorCount color: knownColorsBL)
		{
			if (!knownColorsBLMenu.contains(color.getColor()))
			{
				System.out.println("Part "+partNumber+" contains color "+color.getColor()+" in the known colors, but not in the menu");
			}
			i.remove(color.getColor());

			//System.out.println("Ran for color "+color.getColor());
		}
		for (String color: i)
		{
			//System.out.println("Part "+partNumber+" contains color "+color+" in the menu, but not in the known colors");
		}
		//System.out.println(knownColorsBLMenu);
		if (knownColorsBLMenu.size()!=0)
		{
			ConsoleGUIModel.getDatabase().incrementColoredParts(knownColorsBLMenu.size());
		}
		else
		{
			System.out.println("No known colors for part "+partNumber);
		}



	}



	public String getPartNumber()
	{
		return partNumber;
	}

	public void setPartNumber(String partNumber)
	{
		this.partNumber = partNumber;
	}




	public ArrayList<ColorCount> getItemsForSale()
	{
		return itemsForSale;
	}

	public void setItemsForSale(ArrayList<ColorCount> itemsForSale)
	{
		this.itemsForSale = itemsForSale;
	}

	public ArrayList<ColorCount> getPriceGuides()
	{
		return priceGuides;
	}

	public void setPriceGuides(ArrayList<ColorCount> priceGuides)
	{
		this.priceGuides = priceGuides;
	}

	public int ordinalIndexOf(String str, int n) {
	    int pos = str.indexOf('"');
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf('"', pos + 1);
	    return pos;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}

	public ArrayList<String> getKnownColorsBLMenu()
	{
		return knownColorsBLMenu;
	}

	public void setKnownColorsBLMenu(ArrayList<String> knownColorsBLMenu)
	{
		this.knownColorsBLMenu = knownColorsBLMenu;
	}

	public Boolean getHasInventory()
	{
		return hasInventory;
	}

	public void setHasInventory(Boolean hasInventory)
	{
		this.hasInventory = hasInventory;
	}

	public ArrayList<ColorCount> getKnownColorsBL()
	{
		return knownColorsBL;
	}

	public void setKnownColorsBL(ArrayList<ColorCount> knownColorsBL)
	{
		this.knownColorsBL = knownColorsBL;
	}

	public Date getDateAdded()
	{
		return dateAdded;
	}

	public int getCategoryID()
	{
		return categoryID;
	}

	public ArrayList<String> getAltNumbers()
	{
		return altNumbers;
	}

	public int getFirstYear()
	{
		return firstYear;
	}

	public int getLastYear()
	{
		return lastYear;
	}

	public double getWeight()
	{
		return weight;
	}

	public double getLengthStuds()
	{
		return lengthStuds;
	}

	public double getWidthStuds()
	{
		return widthStuds;
	}

	public double getHeightStuds()
	{
		return heightStuds;
	}

	public int getLengthMill()
	{
		return lengthMill;
	}

	public int getWidthMill()
	{
		return widthMill;
	}

	public int getHeightMill()
	{
		return heightMill;
	}

	public ArrayList<CatalogPart> getConsistsOfParts()
	{
		return consistsOfParts;
	}

	public ArrayList<CatalogSet> getSetInventories()
	{
		return setInventories;
	}

	public ArrayList<CatalogPart> getPartInventories()
	{
		return partInventories;
	}

	public ArrayList<CatalogMinifig> getMinifigInventories()
	{
		return minifigInventories;
	}

	public ArrayList<CatalogGear> getGearInventories()
	{
		return gearInventories;
	}

	public ArrayList<CatalogBook> getBookInventories()
	{
		return bookInventories;
	}

	public String getNotes()
	{
		return notes;
	}

	public ArrayList<String> getSimilarMold()
	{
		return similarMold;
	}

	public ArrayList<String> getPairsWith()
	{
		return pairsWith;
	}

	public ArrayList<String> getSimilarPattern()
	{
		return similarPattern;
	}

	public ArrayList<String> getDoorFrame()
	{
		return doorFrame;
	}

	public ArrayList<String> getDoor()
	{
		return door;
	}

	public ArrayList<String> getWheel()
	{
		return wheel;
	}

	public ArrayList<String> getTire()
	{
		return tire;
	}

	public ArrayList<String> getSameSticker()
	{
		return sameSticker;
	}

	public ArrayList<String> getDuplicate()
	{
		return duplicate;
	}

	public ArrayList<String> getWindow()
	{
		return window;
	}

	public ArrayList<String> getWindowFrame()
	{
		return windowFrame;
	}

	public ArrayList<CatalogPart> getSimilarMoldParts()
	{
		return similarMoldParts;
	}

	public ArrayList<CatalogPart> getPairsWithParts()
	{
		return pairsWithParts;
	}

	public ArrayList<CatalogPart> getSimilarPatternParts()
	{
		return similarPatternParts;
	}

	public ArrayList<CatalogPart> getDoorFrameParts()
	{
		return doorFrameParts;
	}

	public ArrayList<CatalogPart> getDoorParts()
	{
		return doorParts;
	}

	public ArrayList<CatalogPart> getWheelParts()
	{
		return wheelParts;
	}

	public ArrayList<CatalogPart> getTireParts()
	{
		return tireParts;
	}

	public ArrayList<CatalogPart> getSameStickerParts()
	{
		return sameStickerParts;
	}

	public ArrayList<CatalogPart> getDuplicateParts()
	{
		return duplicateParts;
	}

	public ArrayList<CatalogPart> getWindowParts()
	{
		return windowParts;
	}

	public ArrayList<CatalogPart> getWindowFrameParts()
	{
		return windowFrameParts;
	}

	public ArrayList<ColorCount> getWantedLists()
	{
		return wantedLists;
	}

	public ArrayList<String> getKnownColorsVerified()
	{
		return knownColorsVerified;
	}

	public String getTxtRep()
	{
		return txtRep;
	}

	public ArrayList<CatalogPartColored> getColoredSubParts()
	{
		return coloredSubParts;
	}

    public int getBricklinkInternalID()
    {
        return bricklinkInternalID;
    }

    public void setBricklinkInternalID(int bricklinkInternalID)
    {
        this.bricklinkInternalID = bricklinkInternalID;
    }

}
