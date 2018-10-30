package blcrawler.model.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import blcrawler.model.CatalogPart;
/*
 * Provides methods for reading and writing to/from the part database
 */


public class PartDatabase
{
	String databasePath;
	String bufferedText;
	ArrayList<CatalogPart> catalogParts;
	
	public PartDatabase()
	{
		databasePath = System.getProperty("user.home")+"\\blcrawler-data\\data\\database.xml";
		System.out.println(databasePath);
		System.out.println("Created a database anyways. Screw the rules");
		catalogParts = new ArrayList<>();
		//ReadDatabase();
	}
	
	/*
	 * returns a substring containing only the text within a given tag and index number
	 * For example, GetXMLTag(PART, 3) will return the text between the 3rd instance of
	 * <PART> and </PART>, tags excluded
	 */
	public void GetXMLTag(String tag, int tagindex)
	{
		
	}
	
	public void addCatalogPart(CatalogPart part)
	{
		catalogParts.add(part);
	}
	
	public void ReadDatabase()
	{
		try
		{
			bufferedText = new Scanner(new File(databasePath)).useDelimiter("\\Z").next();
		}
		catch (FileNotFoundException e)
		{
			try{
				//Create a empty database file if one isn't present
				File file = new File(databasePath);
				file.getParentFile().mkdirs();
				FileWriter writer = new FileWriter(file);
			    System.out.println("Database file not found, empty database created");
			} catch (IOException f) {
			   f.printStackTrace();
			}
		}
	}
}
