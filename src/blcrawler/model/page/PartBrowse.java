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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;


public class PartBrowse implements Page {
	

	private Document pageDoc;
	private String url;
	private List<Date> pullTimeStamps;
	private String pageHTML;
	private String txtRep;
	private File folder;
	
	public PartBrowse(String input) throws IllegalArgumentException {
		if (input.startsWith("http://"))
		{
			url=input;
			pullTimeStamps = new ArrayList<Date>();
			pullTimeStamps.add(new Date());
			ConsoleGUIModel.getSeleniumModel().gotoURL(url);
			pageHTML = ConsoleGUIModel.getSeleniumModel().getHTML();
			pageDoc = Jsoup.parse(pageHTML, "http://www.bricklink.com");
			txtRep = formTxtRep();
			ConsoleGUIModel.getPageManager().addPartBrowse(this);
			memSave();
		}
		else if (input.startsWith("partbrowse_"))
		{
			BufferedReader in;
			
			try {
				in = new BufferedReader(new FileReader("C:/Users/Joe/Documents/BLCrawl/Database/Pages/PartBrowse"+input));
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
			
			
			
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		

	}

	public String formTxtRep()
	{
		String returnText = "URL: " + url + "\n" + "\n" + pullTimeStamps.toString();
		returnText = returnText+"\n"+"\n"+"Raw HTML: "+"\n"+"\n"+pageHTML;
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
	
}
