package blcrawler.model.page;

import java.util.ArrayList;
import java.io.File;
import java.io.FilenameFilter;

import blcrawler.model.ConsoleOutput;

public class PageManager {
	
	public ArrayList<PartBrowse> partBrowsePages; 
	public File partBrowseDirectory;
	public File[] partBrowseFiles;
	
	public PageManager()
	{
		partBrowsePages=new ArrayList<PartBrowse>();
		this.partBrowseDirectory = new File("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse");
		this.partBrowseFiles = partBrowseDirectory.listFiles();
	}
	
	public void addPartBrowse(PartBrowse e) {
		partBrowsePages.add(e);
		partBrowseFiles = partBrowseDirectory.listFiles();
		String highestValue = partBrowseFiles[partBrowseFiles.length-1].getName();
		new ConsoleOutput("PageManager", "Page saved as "+highestValue+".");
	}
		




		
		
		
	
	public void addPartBrowseFromDatabase() {
		
	}

}
