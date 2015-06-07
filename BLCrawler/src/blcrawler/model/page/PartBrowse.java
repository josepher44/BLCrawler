package blcrawler.model.page;

import java.io.BufferedWriter;
import java.io.File;
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
	private File folder;
	
	public PartBrowse(String input) {
		url=input;
		pullTimeStamps = new ArrayList<Date>();
		pullTimeStamps.add(new Date());
		GUIModel.getSeleniumModel().getURL(url);
		pageHTML = GUIModel.getSeleniumModel().getHTML();
		pageDoc = Jsoup.parse(pageHTML, "http://bricklink.com");
		txtRep = formTxtRep();
		GUIModel.getPageManager().addPartBrowse(this);
		memSave();
	}

	/**
	 * @return the txtRep
	 */
	public String getTxtRep() {
		return txtRep;
	}

	/**
	 * @param txtRep the txtRep to set
	 */
	public void setTxtRep(String txtRep) {
		this.txtRep = txtRep;
	}

	@Override
	public void parseFromWeb() {

		GUIModel.getSeleniumModel().getURL(url);
		pageHTML = GUIModel.getSeleniumModel().getHTML();
		
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
	
	/**
	 * Reduces the memory saved in the object by nullifying fields which store raw HTML when not used
	 * Call at the conclusion of any method which calls getHTML or getTxtRep
	 */
	public void memSave()
	{
		txtRep="";
		pageHTML = "";
	}
	
}
