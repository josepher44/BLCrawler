package blcrawler.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blcrawler.commands.individualcalls.scraping.PartBLToRawDatabase;
import blcrawler.commands.templates.Command;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.Part;

public class PullPGFromSite implements Command {

	boolean isFinished;
	String partID;
	private int queueID;
	
	private long delay;
	private int timeout;

	private int internalid;
	private int colorid;
	private CatalogPart part;
	public PullPGFromSite(int internalID, int colorID, CatalogPart part) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(2, 15);
		timeout = 25+randomNum;
		delay = 15+randomNum;
		isFinished = false;
		this.internalid = internalID;
		this.colorid = colorID;
		this.part = part;

	}
	
	@Override
	public void execute() 
	{

        String url = "https://www.bricklink.com/v2/catalog/catalogitem_pgtab.page?idItem="
                + internalid+"&idColor="+colorid+"&st=2&gm=1&gc=0&ei=0&prec=4&showflag=1&showbulk=1&currency=1";
        
        //System.out.println("Url request is"+url);
        
        //String checkString = "BrickLink - Part "+partID.substring(partID.indexOf('_')+1, partID.indexOf(".xml"));
        String output = ConsoleGUIModel.getSelenium().getURL(url, queueID);
        //new ConsoleOutput("CommandResult", "Directed Selenium module to parse data for url "+url);
        
        File file = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/PriceGuides/Parts/part_"+part.getPartNumber()+"_color_"+colorid+".txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Part #"+part.getPartNumber()+"_color_"+colorid+" recorded");
        
        
        
        
        
        isFinished = true;


		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return delay;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return timeout;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return isFinished;
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceQuit() {
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

}
