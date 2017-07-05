package blcrawler.primatives;

import blcrawler.model.ConsoleGUIModel;

public class ColorCount
{
	int count;
	String color;
	int colorID;
	
	public ColorCount(int number, int ID)
	{
		count = number;
		colorID = ID;
		
		ColorMap colorMap = ConsoleGUIModel.getDatabase().getColormap();
		
		color = colorMap.nameFromID(colorID);
	}
	
	public ColorCount(int number, String color_name)
	{
		count = number;
		color = color_name;
		
		ColorMap colorMap = ConsoleGUIModel.getDatabase().getColormap();
		
		colorID = colorMap.idFromName(color);
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public int getColorID()
	{
		return colorID;
	}

	public void setColorID(int colorID)
	{
		this.colorID = colorID;
	}
	
	
}
