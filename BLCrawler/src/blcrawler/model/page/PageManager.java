package blcrawler.model.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import blcrawler.model.ConsoleOutput;

public class PageManager {
	
	public List<PartBrowse> partBrowsePages; 
	public HashMap<String, String> partBrowseFileMap;
	
	
	public File partBrowseDirectory;
	public File[] partBrowseFiles;
	
	public PageManager()
	{
		partBrowsePages=new ArrayList<PartBrowse>();
		partBrowseDirectory = new File("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse");
		partBrowseFiles = partBrowseDirectory.listFiles();
		partBrowseFileMap = new HashMap<String,String>();

		
		
		
		for (int k=0; k<partBrowseFiles.length;k++)
		{
			BufferedReader txtReader=null;
			try {
				txtReader = new BufferedReader(new FileReader(
						"C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse/"+partBrowseNameGenerator(k+1)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				partBrowseFileMap.put(partBrowseFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		new ConsoleOutput("Initialization", "PartBrowse Files Found: "+ partBrowseFileMap.toString());
	}
	
	public void addPartBrowse(PartBrowse partbrowse) {
		
		
		if (partBrowseFileMap.containsValue(partbrowse.getUrl()))
		{
			new ConsoleOutput("PageManager", "Partbrowse page of url "+partbrowse.getUrl()+" already stored.");
		}
		else
		{
			partBrowsePages.add(partbrowse);
			
			partBrowseFiles = partBrowseDirectory.listFiles();
			
			String highestValue = partBrowseFiles[partBrowseFiles.length-1].getName();
			highestValue = highestValue.substring(highestValue.indexOf("_")+1, highestValue.indexOf("_")+6);
			int fileNumber = Integer.parseInt(highestValue)+1;
			String fileName = partBrowseNameGenerator(fileNumber);
			
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse/"+fileName), "utf-8"))) {
		   writer.write(partbrowse.getTxtRep());
			} catch (UnsupportedEncodingException e) {
				new ConsoleOutput("System:", "You dummy. That's not a real encoding type!");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				new ConsoleOutput("System:", "File not found exception thrown by new partbrowse");
				e.printStackTrace();
			} catch (IOException e) {
				new ConsoleOutput("System:", "IO exception thrown by new partbrowse");
				e.printStackTrace();
			}
			
			partBrowseFileMap.put(fileName, partbrowse.getUrl());	
			
			
			
			new ConsoleOutput("PageManager", "Page saved as "+fileName+".");
		}
	}
		




		
		
		
	
	public void addPartBrowseFromDatabase() {
		
	}
	
	public String partBrowseNameGenerator(int index) {
		String pbfileName = "partbrowse_";
		for (int i=index; i<10000; i=i*10)
		{
			pbfileName=pbfileName+Integer.toString(0);
		}
		pbfileName=pbfileName+Integer.toString(index)+".txt";
		return pbfileName;
	}

}
