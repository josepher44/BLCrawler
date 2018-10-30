package blcrawler.primatives;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ColorMap
{
	public Hashtable<Integer, String> nameFromID;
	public Hashtable<String, Integer> idFromName;
	public ArrayList<String> colorNames;
	public ArrayList<Integer> colorIDs;
	
	public ColorMap()
	{
		nameFromID = new Hashtable<>();
		idFromName = new Hashtable<>();
		colorNames = new ArrayList<>();
		colorIDs = new ArrayList<>();
		
		String path = "C:/Users/Owner/Documents/BLCrawler/ColorCodes.xml";
		File colors = new File(path);
		
		SAXBuilder builder3 = new SAXBuilder();
		Document readDoc3 = null;

		
		try
		{
			readDoc3 = builder3.build(colors);
		}
		catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		
		
		Element root = readDoc3.getRootElement();
		
		for (Element currentElement : root.getChildren("row"))
		{
			String color = currentElement.getChildText("Name");
			color = color.substring(0, color.length()-1);
			nameFromID.put(Integer.valueOf(currentElement.getChildText("ID")), color);
			idFromName.put(color, Integer.valueOf(currentElement.getChildText("ID")));
			colorNames.add(color);
			colorIDs.add(Integer.valueOf(currentElement.getChildText("ID")));
		}

	}
	
	public String nameFromID(int id)
	{
		return nameFromID.get(id);
	}
	public int idFromName(String name)
	{
		if(idFromName.containsKey(name))
		{
			return idFromName.get(name);
		}
		else
		{
			return 0;
		}
			
	}

	public ArrayList<String> getColorNames()
	{
		return colorNames;
	}

	public void setColorNames(ArrayList<String> colorNames)
	{
		this.colorNames = colorNames;
	}

	public ArrayList<Integer> getColorIDs()
	{
		return colorIDs;
	}

	public void setColorIDs(ArrayList<Integer> colorIDs)
	{
		this.colorIDs = colorIDs;
	}
	
	
	
}
