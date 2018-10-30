package blcrawler.model;

public class PartInventoryEntry
{
	String partNumber;
	String type;
	int colorID;
	int quantity;
	int extraquantity;
	boolean alternate;
	boolean counterpart;
	boolean flagForRemoval;
	PartInventory inv_parent;
	public PartInventoryEntry(String entry, PartInventory parent)
	{
		//System.out.println("Building a part now, raw string ="+entry);
		flagForRemoval = false;
		inv_parent = parent;
		partNumber = parsePartNumber(entry);
		type = parseType(entry);
		colorID = parseColorID(entry);
		quantity = parseQuantity(entry);
		extraquantity = parseExtraQuantity(entry);
		alternate = parseAlternate(entry);
		counterpart = parseCounterpart(entry);

		
	}
	
	public String parsePartNumber(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"no\":\"")+6);
		txtRep = txtRep.substring(0, txtRep.indexOf("\","));
		//System.out.println("Part number is "+txtRep);
		if(ConsoleGUIModel.getDatabase().getPart(txtRep).getHasInventory())
		{
			flagForRemoval = true;
			//System.out.println("Warning: Stacked parts with inventory, part number "+txtRep);
			String basepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/Inventories/Parts/";
			inv_parent.addToInventory(new PartInventory(basepath+"part_"+txtRep+".txt"));
		}
		return txtRep;
	}
	
	public String parseType(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"type\":\"")+8);
		txtRep = txtRep.substring(0, txtRep.indexOf("\","));
		if(!txtRep.equals("PART"))
		{
			System.out.println("WARNING: Non-part entry found, ID is "+partNumber+", actual type is "+txtRep);
		}
		//System.out.println("Part type is "+txtRep);
		return txtRep;
	}
	
	public int parseColorID(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"color_id\":")+11);
		txtRep = txtRep.substring(0, txtRep.indexOf(","));
		return Integer.valueOf(txtRep);
	}
	
	public int parseQuantity(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"quantity\":")+11);
		txtRep = txtRep.substring(0, txtRep.indexOf(","));
		return Integer.valueOf(txtRep);
	}
	
	public int parseExtraQuantity(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"extra_quantity\":")+17);
		txtRep = txtRep.substring(0, txtRep.indexOf(","));
		return Integer.valueOf(txtRep);
	}
	
	public boolean parseAlternate(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"is_alternate\":")+15);
		txtRep = txtRep.substring(0, txtRep.indexOf(","));
		if (!txtRep.equals("false"))
		{
			System.out.println("true for alternate at part "+partNumber);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean parseCounterpart(String entry)
	{
		String txtRep = entry.substring(entry.indexOf("\"is_counterpart\":")+17);
		//txtRep = txtRep.substring(0, txtRep.indexOf(","));
		if (!txtRep.equals("false"))
		{
			System.out.println("true for counterpart at part "+partNumber);
			return true;
		}
		else
		{
			return false;
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getColorID()
	{
		return colorID;
	}

	public void setColorID(int colorID)
	{
		this.colorID = colorID;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public int getExtraquantity()
	{
		return extraquantity;
	}

	public void setExtraquantity(int extraquantity)
	{
		this.extraquantity = extraquantity;
	}

	public boolean isAlternate()
	{
		return alternate;
	}

	public void setAlternate(boolean alternate)
	{
		this.alternate = alternate;
	}

	public boolean isCounterpart()
	{
		return counterpart;
	}

	public void setCounterpart(boolean counterpart)
	{
		this.counterpart = counterpart;
	}

	public boolean isFlagForRemoval()
	{
		return flagForRemoval;
	}

	public void setFlagForRemoval(boolean flagForRemoval)
	{
		this.flagForRemoval = flagForRemoval;
	}
	
	@Override
	public String toString()
	{
		String returnString = "PartNumber: "+partNumber+", Type: "+type+", ColorID: "+colorID+", Quantity: "+quantity+
				", ExtraQuantity: "+extraquantity+", Alternate: "+alternate+", Counterpart: "+counterpart;
		return returnString;
		
	}
	
}
