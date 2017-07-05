package blcrawler.model.page;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.bsx.XMLFile;
import blcrawler.commands.PullPartFromSite;
import blcrawler.model.ConsoleGUIModel;


public class Part implements Page {
	

	private Document pageDoc;
	private String url;
	private List<Date> pullTimeStamps;
	private String pageHTML;
	private String txtRep;
	private File folder;
	private String filepath;
	
	
	
	public Part(String input) throws IllegalArgumentException {
		if (input.startsWith("http://"))
		{
//			url=input;
//			pullTimeStamps = new ArrayList<Date>();
//			pullTimeStamps.add(new Date());
//			ConsoleGUIModel.getSeleniumModel().gotoURL(url);
//			pageHTML = ConsoleGUIModel.getSeleniumModel().getHTML();
//			pageDoc = Jsoup.parse(pageHTML, "http://www.bricklink.com");
//			txtRep = formTxtRep();
//			ConsoleGUIModel.getPageManager().addPart(this);
//			memSave();
			System.out.println("Depreciated call to part constructor with URL input, check code path");
		}
		else if (input.startsWith("part_"))
		{
			ConsoleGUIModel.getSelenium().distributeToSmallestQueue(new PullPartFromSite(input, this));
			filepath = "C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/blcrawl/Catalog/Parts/"+input;
			
			
			
			
//			BufferedReader in;
//			
//			try {
//				in = new BufferedReader(new FileReader("C:/Users/Joe/Documents/BLCrawl/Database/Pages/Part/"+input));
//				String line;
//				while((line = in.readLine()) != null)
//				{
//				    txtRep=txtRep+line+System.lineSeparator();
//				}
//				in.close();
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//			}
			
			
			
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		

	}

	public String formTxtRep()
	{
		String returnText = "[URL]" + url + "\n" + "[/URL]" + "\n";
		returnText = returnText + "[TIMESTAMPS]"+ pullTimeStamps.toString() + "\n" + "[/TIMESTAMPS]" + System.lineSeparator();
		returnText = returnText+"[HTML]: "+System.lineSeparator()+System.lineSeparator()+pageHTML+"\n"+"[/HTML]";
		returnText = returnText.replaceAll("\n", System.lineSeparator());
		return returnText;
	}

	@Override
	public Date getLastPullTimestamp() {
		// TODO Auto-generated method stub
		return pullTimeStamps.get(pullTimeStamps.size()-1);
	}

	/**
	 * @return the txtRep
	 */
	public String getTxtRep() {
		return txtRep;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Reduces the memory saved in the object by nullifying fields which store raw HTML when not used
	 * Call at the conclusion of any method which calls getHTML or getTxtRep
	 */
	public void memSave()
	{
		txtRep="";
		pageHTML = "";
	}

	@Override
	public void parseFromWeb() {

		ConsoleGUIModel.getSeleniumModel().gotoURL(url);
		pageHTML = ConsoleGUIModel.getSeleniumModel().getHTML();
		
		pullTimeStamps.add(new Date());


	}
	
	/**
	 * @param txtRep the txtRep to set
	 */
	public void setTxtRep(String txtRep) {
		this.txtRep = txtRep;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageHTML()
	{
		return pageHTML;
	}

	public void setPageHTML(String pageHTML)
	{
		this.pageHTML = pageHTML;


		XMLFile xml = new XMLFile(filepath);
		xml.appendTag("HTML", pageHTML);
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		xml.appendTag("PullTimestamp", dateFormat.format(new Date()));
		ConsoleGUIModel.getDatabase().fixHTML(filepath);

	}
	
}
