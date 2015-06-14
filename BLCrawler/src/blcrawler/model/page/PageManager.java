package blcrawler.model.page;

import java.util.ArrayList;
import java.util.Collections;
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
import blcrawler.model.GUIModel;

public class PageManager {
	
	public List<PartBrowse> partBrowsePages; 
	public HashMap<String, String> partBrowseFileMap;
	private PartBrowseIndex partBrowseIndex;
	public File partBrowseDirectory;
	public File[] partBrowseFiles;
	
	public List<PartCatalog> partCatalogPages; 
	public HashMap<String, String> partCatalogFileMap;
	private PartCatalogIndex partCatalogIndex;
	public File partCatalogDirectory;
	public File[] partCatalogFiles;
	
	public PageManager()
	{
		initializePartBrowse();
		initializePartCatalog();
		
	
	}
	
	public void updatePartBrowseIndex(PartBrowseIndex index) {
		partBrowseIndex = index;
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowseIndex/partbrowseindex.txt"), "utf-8"))) {
	   writer.write(partBrowseIndex.getTxtRep());
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
	}
	
	public void updatePartCatalogIndex(PartCatalogIndex index) {
		partCatalogIndex = index;
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalogIndex/partcatalogindex.txt"), "utf-8"))) {
	   writer.write(partCatalogIndex.getTxtRep());
		} catch (UnsupportedEncodingException e) {
			new ConsoleOutput("System:", "You dummy. That's not a real encoding type!");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			new ConsoleOutput("System:", "File not found exception thrown by new partcatalog");
			e.printStackTrace();
		} catch (IOException e) {
			new ConsoleOutput("System:", "IO exception thrown by new partcatalog");
			e.printStackTrace();
		}
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
			String fileName= "";
			if (partBrowseFiles.length==0)
			{
				fileName = "partbrowse_00001.txt";
			}
			else
			{
			String highestValue = partBrowseFiles[partBrowseFiles.length-1].getName();
			highestValue = highestValue.substring(highestValue.indexOf("_")+1, highestValue.indexOf("_")+6);
			int fileNumber = Integer.parseInt(highestValue)+1;
			fileName = partBrowseNameGenerator(fileNumber);
			}
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
	
	public void addPartCatalog(PartCatalog partcatalog) {
		
		
		if (partCatalogFileMap.containsValue(partcatalog.getUrl()))
		{
			new ConsoleOutput("PageManager", "Partcatalog page of url "+partcatalog.getUrl()+" already stored.");
		}
		else
		{
			partCatalogPages.add(partcatalog);
			
			partCatalogFiles = partCatalogDirectory.listFiles();
			String fileName= "";
			if (partCatalogFiles.length==0)
			{
				fileName = "partcatalog_00001.txt";
			}
			else
			{
			String highestValue = partCatalogFiles[partCatalogFiles.length-1].getName();
			highestValue = highestValue.substring(highestValue.indexOf("_")+1, highestValue.indexOf("_")+6);
			int fileNumber = Integer.parseInt(highestValue)+1;
			fileName = partCatalogNameGenerator(fileNumber);
			}
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog/"+fileName), "utf-8"))) {
		   writer.write(partcatalog.getTxtRep());
			} catch (UnsupportedEncodingException e) {
				new ConsoleOutput("System:", "You dummy. That's not a real encoding type!");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				new ConsoleOutput("System:", "File not found exception thrown by new partcatalog");
				e.printStackTrace();
			} catch (IOException e) {
				new ConsoleOutput("System:", "IO exception thrown by new partcatalog");
				e.printStackTrace();
			}
			
			partCatalogFileMap.put(fileName, partcatalog.getUrl());	
			
			
			
			new ConsoleOutput("PageManager", "Page saved as "+fileName+".");
		}
	}
	
	public void expandPartCatalog() {
		
		partCatalogFiles = partCatalogDirectory.listFiles();
		ArrayList<String> urls = new ArrayList<String>();
		for(int i=0; i<partCatalogFiles.length; i++)
		{
			BufferedReader in;
			String txtRep="";
			try {
				in = new BufferedReader(new FileReader(
						"C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog/partcatalog_"+fiveDigits(i+1)+".txt"));
				String line;
				while((line = in.readLine()) != null)
				{
				    txtRep=txtRep+line+System.lineSeparator();
				}
				in.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			String html = txtRep.substring(txtRep.indexOf("[HTML]")+8);
			
			if (html.contains("Page <b>1</b>"))
			{
				if (html.charAt(html.indexOf("Page <b>1</b>")+20)!=1)
				{
					for (int k=2; k<1+Character.getNumericValue(html.charAt(html.indexOf("Page <b>1</b>")+20)); k++)
					{
						String url = "http://www.bricklink.com/catalogList.asp?pg=" + k + "&"+txtRep.substring(46, txtRep.indexOf("[/URL]"));
						urls.add(url);
					}
				}
			}
		}
		Collections.shuffle(urls);
		for (int i=0; i<urls.size(); i++)
		{
			GUIModel.getConsoleController().createPartCatalog(urls.get(i));
		}
	}
	
	
	
	
	/*	
	public void InsertInAllFiles(String insertString, String beforeString)
	{
		partBrowseFiles = partBrowseDirectory.listFiles();
		String bufferedText;
		
		for (int i=0; i<partBrowseFiles.length; i++)
		{
			in = new BufferedReader(new FileReader("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse"+input));
			String line;
			while((line = in.readLine()) != null)
			{
			    txtRep=txtRep+line+System.lineSeparator();
			}
			in.close();
			
			bufferedText = partBrowseFiles[i]
		}
	}

	*/


		
		
		
	
	public void addPartBrowsesFromDatabase() {
		
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
	
	public String partCatalogNameGenerator(int index) {
		String pbfileName = "partcatalog_";
		for (int i=index; i<10000; i=i*10)
		{
			pbfileName=pbfileName+Integer.toString(0);
		}
		pbfileName=pbfileName+Integer.toString(index)+".txt";
		return pbfileName;
	}
	
	/*
	 * Initialization methods. 
	 * These are called during the constructor, to pull from the database and repopulate the program
	 * with data from previously archived pages. 
	 */
	
	public void initializePartBrowse() {
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
				partBrowseFileMap.put(partBrowseFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		new ConsoleOutput("Initialization", "PartBrowse Files Found: "+ partBrowseFileMap.toString());
	}
	
	public void initializePartCatalog() {
		partCatalogPages=new ArrayList<PartCatalog>();
		partCatalogDirectory = new File("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog");
		partCatalogFiles = partCatalogDirectory.listFiles();
		partCatalogFileMap = new HashMap<String,String>();

		
		
		
		for (int k=0; k<partCatalogFiles.length;k++)
		{
			BufferedReader txtReader=null;
			try {
				txtReader = new BufferedReader(new FileReader(
						"C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog/"+partCatalogNameGenerator(k+1)));
				partCatalogFileMap.put(partCatalogFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		new ConsoleOutput("Initialization", "PartCatalog Files Found: "+ partCatalogFileMap.toString());
	}
	
	public String fiveDigits(int i)
	{
		if (i<10)
		{
			return "0000"+i;
		}
		else if (i<100)
		{
			return "000"+i;
		}
		else if (i<10000)
		{
			return "00"+i;
		}
		else if (i<10000)
		{
			return "0"+i;
		}
		else
		{
			return Integer.toString(i);
		}
	}

}
