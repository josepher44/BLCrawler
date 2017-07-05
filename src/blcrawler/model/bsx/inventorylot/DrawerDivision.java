package blcrawler.model.bsx.inventorylot;

public class DrawerDivision
{
	public boolean isEmpty;
	public String rawRemarks;
	public short cabinet;
	public short drawer;
	public short division;
	public String size;
	public int index;
	public int integerDrawer;
	public InventoryLocation items;
	
	public DrawerDivision(String rawDrawer, String slot, String inputsize, String empty, int i)
	{
		if (empty.equals("y"))
		{
			isEmpty=true;
		}
		else
		{
			isEmpty = false;
		}
		division = Short.parseShort(slot);
		
		rawRemarks = rawDrawer+"-0"+division;
		if (Integer.parseInt(slot)>=10)
		{
			//remove leading zero from slots of 10 or greater
			rawRemarks = rawDrawer+"-"+division;
		}
		
		items = null;
		
		cabinet = Short.parseShort(rawRemarks.substring(0,3));
		
		drawer = Short.parseShort(rawRemarks.substring(4,6));
		
		size = inputsize;
	
		index = i;
		
		integerDrawer = (cabinet-1)*60+drawer;
		

	}

	public InventoryLocation getItems()
	{
		return items;
	}

	public void setItems(InventoryLocation items)
	{
		this.items = items;
	}

	public boolean isEmpty()
	{
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty)
	{
		this.isEmpty = isEmpty;
	}

	public String getRawRemarks()
	{
		return rawRemarks;
	}

	public void setRawRemarks(String rawRemarks)
	{
		this.rawRemarks = rawRemarks;
	}

	public short getCabinet()
	{
		return cabinet;
	}

	public void setCabinet(short cabinet)
	{
		this.cabinet = cabinet;
	}

	public short getDrawer()
	{
		return drawer;
	}

	public void setDrawer(short drawer)
	{
		this.drawer = drawer;
	}

	public short getDivision()
	{
		return division;
	}

	public void setDivision(short division)
	{
		this.division = division;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIntegerDrawer()
	{
		return integerDrawer;
	}

	public void setIntegerDrawer(int integerDrawer)
	{
		this.integerDrawer = integerDrawer;
	}
}
