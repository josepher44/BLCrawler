package blcrawler.model.bsx.inventorylot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import blcrawler.view.imsgui.javafx.CustomImage;
import java.awt.image.*;
import java.awt.Image;
import javafx.scene.image.ImageView;

/*Class representing a single lot of a bsx file
 * Contains all fields stored in the bsx, and methods to convert
 * to and from bsx and xml formats
 * Also stores a history of the lot using a snapshot system
 * for more complete mass update generation
 */

public abstract class InventoryEntry
{
	String ItemID;
	char ItemTypeID;
	short ColorID;
	String ItemName;
	String ItemType;
	String ColorName;
	Short CategoryID;
	String CategoryName;
	String Status;
	int Qty;
	double Price;
	char Condition;
	String Comments;
	String Remarks;
	short Cabinet;
	short Drawer;
	short SectionID;
	short[] divisions;
	int lotID;
	double weight;
	boolean multiLocated;
	int index;
	boolean empty;
	boolean divided;
	String trimmedRemarks;
	String rawSizingIndicators;
	String rcode;
	String ecode;
	String mcode;
	int normalizedDrawerNumber;
	ArrayList<String> validCodes;

	String imageLocation;

	ImageView imageview;
	CustomImage tableImage;



	public InventoryEntry()
	{

	}

	public InventoryEntry(InventoryLot lot)
	{
		validCodes = new ArrayList<>();
		for (Integer i=1;i<17;i++)
		{
			validCodes.add(i.toString());
			validCodes.add(i.toString()+"w");
			if (i%2==0 && i<12)
			{
				validCodes.add(i.toString()+"x");
			}
		}


		ItemID = lot.getItemID();
		ItemTypeID = lot.getItemTypeID();
		ColorID = lot.getColorID();
		ItemName = lot.getItemName();
		ItemType = lot.getItemType();
		ColorName = lot.getColorName();
		CategoryID = lot.getCategoryID();
		CategoryName = lot.getCategoryName();
		Status = lot.getStatus();
		Qty = lot.getQty();
		Price = lot.getPrice();
		Condition = lot.getCondition();
		Comments = lot.getComments();

		Remarks = lot.getRemarks();
		deriveAllRemarks(Remarks);

		lotID = lot.getLotID();
		weight = lot.getWeight();
		multiLocated = lot.getMultiLocated();
		index = lot.getIndex();




		Remarks = Remarks.replaceAll("\\s+","~");
		deriveTrimmedRemarks();
		//System.out.println(trimmedRemarks);



	}

	public InventoryEntry(InventoryLot lot, String remarks)
	{
		ItemID = lot.getItemID();
		ItemTypeID = lot.getItemTypeID();
		ColorID = lot.getColorID();
		ItemName = lot.getItemName();
		ItemType = lot.getItemType();
		ColorName = lot.getColorName();
		CategoryID = lot.getCategoryID();
		CategoryName = lot.getCategoryName();
		Status = lot.getStatus();
		Qty = lot.getQty();
		Price = lot.getPrice();
		Condition = lot.getCondition();
		Comments = lot.getComments();

		Remarks = remarks;
		deriveAllRemarks(Remarks);
		//Cabinet = lot.getCabinet();
		//Drawer = lot.getDrawer();
		//SectionID = lot.getSectionID();
		//divisions = lot.getDivisions();

		lotID = lot.getLotID();
		weight = lot.getWeight();
		multiLocated = lot.getMultiLocated();
		index = lot.getIndex();
		Remarks = Remarks.replaceAll("\\s+","~");
		deriveTrimmedRemarks();
		//System.out.println(trimmedRemarks);



	}

	public void deriveAllRemarks(String s)
	{
		Remarks = s;
		Cabinet = Short.parseShort(s.substring(0, 3));
		//System.out.println("Cabinet of " + s + " is " + Cabinet);
		Drawer = Short.parseShort(s.substring(4, 6));
		//System.out.println("Drawer of " + s + " is " + Drawer);
		if (s.length()>=7)
		{
			if (s.charAt(6)=='-')
			{
				SectionID = Short.parseShort(s.substring(7,9));
				//System.out.println("Section of " + s + " is " + SectionID);
			}
			else
			{
				SectionID = 1;
				//System.out.println("Drawer " + s + " is undivided");
			}
		}
		else
		{
			SectionID = 1;
			//System.out.println("Drawer " + s + " is undivided");
		}
	}

	public void linkToDrawerDivision()
	{

	}


	public void updateAllRemarks(String remarks)
	{
		Remarks = remarks;

	}


	public String getTrimmedRemarks()
	{
		return trimmedRemarks;
	}

	public void deriveTrimmedRemarks()
	{
		if (Remarks.contains("~"))
		{
			trimmedRemarks = Remarks.substring(0,Remarks.indexOf("~"));
			rawSizingIndicators = Remarks.substring(Remarks.indexOf("~")+1);
			try
			{
				parseSizingIndicators();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.out.println("EXCEPTION: String error at drawer "+Remarks);
			}
		}
		else
		{
			trimmedRemarks = Remarks;
		}
	}

	public void parseSizingIndicators()
	{
		if (rawSizingIndicators.contains("r")&&rawSizingIndicators.contains("e"))
		{
			if (rawSizingIndicators.contains("m"))
			{
				rcode = rawSizingIndicators.substring(1, rawSizingIndicators.indexOf('e'));
				ecode = rawSizingIndicators.substring(rawSizingIndicators.indexOf('e')+1,rawSizingIndicators.indexOf('m'));
				mcode = rawSizingIndicators.substring(rawSizingIndicators.indexOf('m')+1,rawSizingIndicators.length());
				//System.out.println(Remarks+": r="+rcode+", e="+ecode+", m="+mcode);
			}
			else
			{
				rcode = rawSizingIndicators.substring(1, rawSizingIndicators.indexOf('e'));
				ecode = rawSizingIndicators.substring(rawSizingIndicators.indexOf('e')+1,rawSizingIndicators.length());
				mcode = "1";
				//System.out.println(Remarks+": r="+rcode+", e="+ecode+", m="+mcode);
			}
		}
		else
		{
			System.out.println("ERROR: Invalid r-code format at drawer # "+Remarks);
		}
	}

	public void parseDrawerNumber()
	{

	}

	public void setTrimmedRemarks(String trimmedRemarks)
	{
		this.trimmedRemarks = trimmedRemarks;
	}

	public int getNormalizedDrawerNumber()
	{
		return normalizedDrawerNumber;
	}

	public void setNormalizedDrawerNumber(int normalizedDrawerNumber)
	{
		this.normalizedDrawerNumber = normalizedDrawerNumber;
	}

	public boolean isDivided()
	{
		return divided;
	}

//	public boolean isEmpty()
//	{
//		return empty;
//	}
//
//	public void setEmpty(boolean empty)
//	{
//		this.empty = empty;
//	}

	public boolean isMultiLocated()
	{
		return multiLocated;
	}

	public void setMultiLocated(boolean multiLocated)
	{
		this.multiLocated = multiLocated;
	}



	public short getCabinet()
	{
		return Cabinet;
	}

	public Short getCategoryID()
	{
		return CategoryID;
	}

	public String getCategoryName()
	{
		return CategoryName;
	}

	public short getColorID()
	{
		return ColorID;
	}

	public String getColorName()
	{
		return ColorName;
	}

	public String getComments()
	{
		return Comments;
	}

	public char getCondition()
	{
		return Condition;
	}

	public short[] getDivisions()
	{
		return divisions;
	}

	public short getDrawer()
	{
		return Drawer;
	}

	public String getItemID()
	{
		return ItemID;
	}

	public String getItemName()
	{
		return ItemName;
	}

	public String getItemType()
	{
		return ItemType;
	}

	public char getItemTypeID()
	{
		return ItemTypeID;
	}

	public int getLotID()
	{
		return lotID;
	}

	public double getPrice()
	{
		return Price;
	}

	public int getQty()
	{
		return Qty;
	}

	public String getRemarks()
	{
		return Remarks;
	}

	public short getSectionID()
	{
		return SectionID;
	}

	public String getStatus()
	{
		return Status;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setCabinet(short cabinet)
	{
		Cabinet = cabinet;
	}

	public void setCategoryID(Short categoryID)
	{
		CategoryID = categoryID;
	}

	public void setCategoryName(String categoryName)
	{
		CategoryName = categoryName;
	}

	public void setColorID(short colorID)
	{
		ColorID = colorID;
	}

	public void setColorName(String colorName)
	{
		ColorName = colorName;
	}

	public void setComments(String comments)
	{
		Comments = comments;
	}

	public void setCondition(char condition)
	{
		Condition = condition;
	}

	public void setDivisions(short[] divisions)
	{
		this.divisions = divisions;
	}

	public void setDrawer(short drawer)
	{
		Drawer = drawer;
	}

	public void setItemID(String itemID)
	{
		ItemID = itemID;
	}

	public void setItemName(String itemName)
	{
		ItemName = itemName;
	}

	public void setItemType(String itemType)
	{
		ItemType = itemType;
	}

	public void setItemTypeID(char itemTypeID)
	{
		ItemTypeID = itemTypeID;
	}

	public void setLotID(int lotID)
	{
		this.lotID = lotID;
	}

	public void setPrice(double price)
	{
		Price = price;
	}

	public void setQty(int qty)
	{
		Qty = qty;
	}

	public void setRemarks(String remarks)
	{
		Remarks = remarks;
	}

	public void setSectionID(short sectionID)
	{
		SectionID = sectionID;
	}

	public void setStatus(String status)
	{
		Status = status;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public void setDivided(boolean b)
	{
		this.divided = b;

	}

	public String getRcode()
	{
		return rcode;
	}

	public void setRcode(String rcode)
	{
		this.rcode = rcode;
	}

	public String getEcode()
	{
		return ecode;
	}

	public void setEcode(String ecode)
	{
		this.ecode = ecode;
	}

	public String getMcode()
	{
		return mcode;
	}

	public void setMcode(String mcode)
	{
		this.mcode = mcode;
	}

	public String getImageLocation()
	{
		return imageLocation;
	}


	public void setImageLocation(String imageLocation)
	{
		this.imageLocation = imageLocation;
	}



	public CustomImage getTableImage()
	{
		return tableImage;
	}

	public void setTableImage(CustomImage tableImage)
	{
		this.tableImage = tableImage;
	}








}
