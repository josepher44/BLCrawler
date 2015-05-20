package blcrawler.model.page;

import java.util.ArrayList;

public class PageManager {
	
	public ArrayList<PartBrowse> partBrowsePages; 
	
	public PageManager()
	{
		partBrowsePages=new ArrayList<PartBrowse>();
	}
	
	public void addPartBrowse(PartBrowse e) {
		partBrowsePages.add(e);
	}

}
