package blcrawler.model.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;

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
	
	public List<Part> partPages; 
	public HashMap<String, String> partFileMap;
	
	public File partDirectory;
	public File[] partFiles;
	
	public PageManager()
	{
		//initializePartBrowse();
		//initializePartCatalog();
		//initializePart();
		
	
	}
	
	public void addPart(Part part) {
		
		
		if (partFileMap.containsValue(part.getUrl()))
		{
			new ConsoleOutput("PageManager", "Part page of url "+part.getUrl()+" already stored.");
		}
		else
		{
			partPages.add(part);
			
			partFiles = partDirectory.listFiles();
			String fileName= "";
			if (partFiles.length==0)
			{
				fileName = "part_00001.txt";
			}
			else
			{
			String highestValue = partFiles[partFiles.length-1].getName();
			highestValue = highestValue.substring(highestValue.indexOf("_")+1, highestValue.indexOf("_")+6);
			int fileNumber = Integer.parseInt(highestValue)+1;
			fileName = partNameGenerator(fileNumber);
			}
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/Part/"+fileName), "utf-8"))) {
		   writer.write(part.getTxtRep());
			} catch (UnsupportedEncodingException e) {
				new ConsoleOutput("System:", "You dummy. That's not a real encoding type!");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				new ConsoleOutput("System:", "File not found exception thrown by new part");
				e.printStackTrace();
			} catch (IOException e) {
				new ConsoleOutput("System:", "IO exception thrown by new part");
				e.printStackTrace();
			}
			
			partFileMap.put(fileName, part.getUrl());	
			
			
			
			new ConsoleOutput("PageManager", "Page saved as "+fileName+".");
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
	
	public void addPartBrowsesFromDatabase() {
		
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
	
	public void buildPartIndex () {
		
		
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
			
			Document pageDoc = Jsoup.parse(html, "http://www.bricklink.com");
			Elements links = pageDoc.select("a[href]");
			String buffer = "";
			for(int k=0; k<links.size(); k++)
			{

				buffer = links.get(k).attr("abs:href");
				if (buffer.startsWith("http://www.bricklink.com/catalogItem.asp?P"))
				{
					urls.add(buffer);
				}
				
			}
			new ConsoleOutput("PageManager", "Scraped " + i + " of " + partCatalogFiles.length + " files.");
			
		}
		Collections.sort(urls);
		urls = new ArrayList<String>(new LinkedHashSet<String>(urls));
		
		String txtRep="";
		for (int k = 0; k<urls.size(); k++)
		{
			txtRep = txtRep+urls.get(k) + System.lineSeparator();
		}
		
		
		
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartIndex/partindex.txt"), "utf-8"))) {
	   writer.write(txtRep);
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
					int forEnd;
					if (html.charAt(html.indexOf("Page <b>1</b>")+21)=='<') 
					{
						forEnd = Character.getNumericValue(html.charAt(html.indexOf("Page <b>1</b>")+20));
					}
					else
					{
						forEnd = Character.getNumericValue(html.charAt(html.indexOf("Page <b>1</b>")+20))*10+Character.getNumericValue(html.charAt(html.indexOf("Page <b>1</b>")+21));
					}
					
					for (int k=2; k<1+forEnd; k++)
					{
						String url = "http://www.bricklink.com/catalogList.asp?pg=" + k + "&"+txtRep.substring(46, txtRep.indexOf("[/URL]"));
						urls.add(url);
						new ConsoleOutput("PageManager", "From File " + fiveDigits(i+1)+ ", url " +url+ " recorded for parsing");
					}
				}
			}
		}
		Collections.shuffle(urls);
		for (int i=0; i<urls.size(); i++)
		{
			ConsoleGUIModel.getConsoleController().createPartCatalog(urls.get(i));
		}
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
	
	
	
	
	
	
	
	
	
	
	
	public void initializePart() {
		partPages=new ArrayList<Part>();
		partDirectory = new File("C:/Users/Joe/Documents/BLCrawl/Database/Pages/Part");
		partFiles = partDirectory.listFiles();
		partFileMap = new HashMap<String,String>();


		BufferedReader txtReader=null;
		
		
		for (int k=0; k<partFiles.length;k++)
		{
			try {
				txtReader = new BufferedReader(new FileReader(
						"C:/Users/Joe/Documents/BLCrawl/Database/Pages/Part/"+partNameGenerator(k+1)));
				partFileMap.put(partFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new ConsoleOutput("Initialization", "Part File Stored: " + partFileMap.get(partFiles[k].getName())+ " from " + partFiles[k].getName());
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


		
		
		
	
	public void initializePartBrowse() {
		partBrowsePages=new ArrayList<PartBrowse>();
		partBrowseDirectory = new File("C:/Users/Joseph/Documents/BLCrawler/Database/Pages/PartBrowse");
		partBrowseFiles = partBrowseDirectory.listFiles();
		partBrowseFileMap = new HashMap<String,String>();

		

		BufferedReader txtReader=null;
		
		for (int k=0; k<partBrowseFiles.length;k++)
		{
			try {
				txtReader = new BufferedReader(new FileReader(
						"C:/Users/Joseph/Documents/BLCrawler/Database/Pages/PartBrowse/"+partBrowseNameGenerator(k+1)));
				partBrowseFileMap.put(partBrowseFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			new ConsoleOutput("Initialization", "PartBrowse File Stored: "+ partBrowseFileMap.get(partBrowseFiles[k].getName())+ " from " +partBrowseFiles[k].getName());
		}
		
		
	}
	
	public void initializePartCatalog() {
		partCatalogPages=new ArrayList<PartCatalog>();
		partCatalogDirectory = new File("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog");
		partCatalogFiles = partCatalogDirectory.listFiles();
		partCatalogFileMap = new HashMap<String,String>();


		BufferedReader txtReader=null;
		
		
		for (int k=0; k<partCatalogFiles.length;k++)
		{
			try {
				txtReader = new BufferedReader(new FileReader(
						"C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartCatalog/"+partCatalogNameGenerator(k+1)));
				partCatalogFileMap.put(partCatalogFiles[k].getName(), txtReader.readLine().substring(5));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			new ConsoleOutput("Initialization", "PartCatalog File Stored: "+ partCatalogFileMap.get(partCatalogFiles[k].getName())+" from " +partCatalogFiles[k].getName());
		}
		
		
		
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
	
	public String partNameGenerator(int index) {
		String pfileName = "part_";
		for (int i=index; i<10000; i=i*10)
		{
			pfileName=pfileName+Integer.toString(0);
		}
		pfileName=pfileName+Integer.toString(index)+".txt";
		return pfileName;
	}
	
	public void scrapeRemainingParts() {
		BufferedReader in;
		ArrayList<String> shuffledList = new ArrayList<String>();
		
		try {
			in = new BufferedReader(new FileReader("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartIndex/partindex.txt"));
			String line;
			
			while((line = in.readLine()) != null)
			{
			    if (!partFileMap.containsValue(line))
			    {
			    	shuffledList.add(line);
			    }
			}
			in.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.shuffle(shuffledList);
		for(int i=0; i<shuffledList.size(); i++)
		{
			ConsoleGUIModel.getConsoleController().createPart(shuffledList.get(i));
		}
		
		
		
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

}
