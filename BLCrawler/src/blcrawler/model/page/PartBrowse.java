package blcrawler.model.page;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PartBrowse implements Page {
	
	Document page;
	
	public PartBrowse() {
		
	}

	@Override
	public void parseFromWeb() {
		try {
			page = Jsoup.connect("http://www.bricklink.com/browseList.asp?itemType=P&catString=46").get();
			page.toString();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
