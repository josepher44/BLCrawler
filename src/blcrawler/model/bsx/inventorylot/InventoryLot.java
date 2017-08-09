package blcrawler.model.bsx.inventorylot;

import java.util.ArrayList;

/*Class representing a single lot of a bsx file
 * Contains all fields stored in the bsx, and methods to convert
 * to and from bsx and xml formats
 * Also stores a history of the lot using a snapshot system
 * for more complete mass update generation
 */

public class InventoryLot extends InventoryEntry
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

	int lotID;
	double weight;
	boolean multiLocated;
	int index;

	public InventoryLot()
	{

	}

	public void deriveAllRemarks(String s)
	{
		try
		{
			Remarks = s;
			Cabinet = Short.parseShort(s.substring(0, 3));
			//System.out.println("InventoryLot: Cabinet of " + s + " is " + Cabinet);
			Drawer = Short.parseShort(s.substring(4, 6));
			//System.out.println("InventoryLot: Drawer of " + s + " is " + Drawer);
			if (s.length()>=7)
			{
				if (s.charAt(6)=='-')
				{
					SectionID = Short.parseShort(s.substring(7,9));
					//System.out.println("InventoryLot: Section of " + s + " is " + SectionID);
				}
				else
				{
					SectionID = 1;
					//System.out.println("InventoryLot: Drawer " + s + " is undivided");
				}
			}
			else
			{
				SectionID = 1;
				//System.out.println("InventoryLot: Drawer " + s + " is undivided");
			}
		}
		catch (NumberFormatException e)
		{
			Cabinet = 0;
			Drawer = 0;
			SectionID = 1;
		}




	}





	public boolean getMultiLocated()
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






}
