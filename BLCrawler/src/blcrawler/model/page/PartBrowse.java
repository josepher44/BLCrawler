package blcrawler.model.page;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;


public class PartBrowse implements Page {
	

	private Document pageDoc;
	private String url;
	private List<Date> pullTimeStamps;
	private String pageHTML;
	private String txtRep;
	
	public PartBrowse(String input) {
		this.url=input;
		this.pullTimeStamps = new ArrayList<Date>();
		this.pullTimeStamps.add(new Date());
		GUIModel.getSeleniumModel().getURL(url);
		this.pageHTML = GUIModel.getSeleniumModel().getHTML();
		pageDoc = Jsoup.parse(pageHTML, "http://bricklink.com");
		this.txtRep = formTxtRep();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse/"+"partbrowse_00001.txt"), "utf-8"))) {
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

	@Override
	public void parseFromWeb() {

		GUIModel.getSeleniumModel().getURL(url);
		this.pageHTML = GUIModel.getSeleniumModel().getHTML();
		
		pullTimeStamps.add(new Date());


	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Date getLastPullTimestamp() {
		// TODO Auto-generated method stub
		return pullTimeStamps.get(pullTimeStamps.size()-1);
	}
	
	public String formTxtRep()
	{
		String returnText = "URL: " + url + "\n" + "\n" + pullTimeStamps.toString();
		returnText = returnText+"\n"+"\n"+"Raw HTML: "+"\n"+"\n"+pageHTML;
		returnText = returnText.replaceAll("\n", System.lineSeparator());
		return returnText;
	}
	
}
