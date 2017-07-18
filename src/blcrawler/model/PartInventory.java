package blcrawler.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class PartInventory
{
	ArrayList<PartInventoryEntry> Inventory;
	String Path;
	String partNumber;
	PartInventory moldNormalizedInventory;
	
	public PartInventory(String path)
	{
		Path = path;
		Inventory = new ArrayList<>();
		partNumber = Path.substring(Path.indexOf("part_")+5, Path.indexOf(".txt"));
		String basetxtRep="";
		moldNormalizedInventory = null;
		try
		{
			basetxtRep = new String(Files.readAllBytes(Paths.get(path)));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String txtRep = basetxtRep;
		for(int i=0; i<StringUtils.countMatches(basetxtRep, "\"item\""); i++)
		{
			//System.out.println(txtRep);
			String subRep = txtRep.substring(txtRep.indexOf("\"item\":{"), txtRep.indexOf("}]}"));
			PartInventoryEntry partToAdd = new PartInventoryEntry(subRep, this);
			if (!partToAdd.isFlagForRemoval())
			{
				addToInventory(partToAdd);
				System.out.println("Added to part "+path.substring(path.indexOf("part_"))+"Part number "+partToAdd.getPartNumber()+", quantity "+partToAdd.getQuantity());
			}
					
			txtRep = txtRep.substring(txtRep.indexOf("}]}")+3);
			
			
		}
		
		//moldNormalizedInventory();
		
		
		//System.out.println("Built an inventory for part "+path.substring(path.indexOf("part_")));
	}
	
	//empty part inventory, shou
	public PartInventory()
	{
		Inventory = new ArrayList<>();
		
		moldNormalizedInventory = null;
	}
	
	public PartInventory moldNormalizedInventory()
	{
		if (moldNormalizedInventory==null)
		{
			buildMoldNormalizedInventory();
			return moldNormalizedInventory;
		}
		else
		{
			return moldNormalizedInventory;
		}
	}
	
	public void buildMoldNormalizedInventory()
	{
		PartInventory normal = new PartInventory();
		for (PartInventoryEntry part : Inventory)
		{
			PartInventoryEntry newPart = part;
			String oldnum = part.getPartNumber();
			if (ConsoleGUIModel.getDatabase().getChildToMasterMold().containsKey(newPart.getPartNumber()))
			{
				newPart.setPartNumber(ConsoleGUIModel.getDatabase().getMasterPartNumber(newPart.getPartNumber()));
				System.out.println("Changed part number "+oldnum+" to "+newPart.getPartNumber());
			}
			newPart.setFlagForRemoval(false);
			newPart.setType("PART");
			newPart.setColorID(0);
			newPart.setQuantity(newPart.getQuantity()+newPart.getExtraquantity());
			newPart.setExtraquantity(0);
			newPart.setAlternate(false);
			newPart.setCounterpart(false);
			normal.addToInventory(newPart);
		}
		Map<String, PartInventoryEntry> map = new HashMap<>();
		map.clear();
		for (PartInventoryEntry part: normal.getInventory())
		{
			PartInventoryEntry oldpart = null;
			oldpart = map.put(part.getPartNumber(), part);
			if (oldpart != null)
			{
				part.setQuantity(part.getQuantity() + oldpart.getQuantity());
				if (part.getPartNumber().equals("983"))
				{
					System.out.println("Quantity is "+part.getQuantity());
				}
			}
		}
		normal.setInventory(new ArrayList<PartInventoryEntry>(map.values()));
		
		for(PartInventoryEntry part : normal.getInventory())
		{
//			System.out.println("Part #"+partNumber+"contains the following when normalized: "+part.getPartNumber()+
//					", quantity of "+part.getQuantity());
		}
		
		moldNormalizedInventory = normal;
	}
	
	public PartInventory(ArrayList<PartInventoryEntry> parts)
	{
		Inventory = new ArrayList<>();
		Inventory.addAll(parts);
	}
	
	public void addToInventory(PartInventory part)
	{
		for (PartInventoryEntry invent : part.getInventory())
		{
			addToInventory(invent);
//			System.out.println("Added to part "+Path.substring(Path.indexOf("part_"))+"Part number "+invent.getPartNumber());
		}
	}
	
	public void addToInventory(PartInventoryEntry part)
	{
		Inventory.add(part);
		
	}

	public ArrayList<PartInventoryEntry> getInventory()
	{
		return Inventory;
	}

	public void setInventory(ArrayList<PartInventoryEntry> inventory)
	{
		Inventory = inventory;
	}
	
	@Override
	public String toString()
	{
		String returnString = "";
		if (Inventory.size() > 0) 
		{
			  Collections.sort(Inventory, new Comparator<PartInventoryEntry>() {
		      @Override
		      public int compare(final PartInventoryEntry object1, final PartInventoryEntry object2) 
		      {
		          return object1.getPartNumber().compareTo(object2.getPartNumber());
		      }
		  });
		}
		for (PartInventoryEntry part : Inventory)
		{
			returnString = returnString+"{<{"+part.toString()+"}>}";
		}
		return returnString;
	}

	public String getPartNumber()
	{
		return partNumber;
	}

	public void setPartNumber(String partNumber)
	{
		this.partNumber = partNumber;
	}
	
	
}
