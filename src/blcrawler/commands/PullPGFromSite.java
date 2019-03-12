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
	
	String nullpart = "<html><head>\r\n" + 
	        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
	        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">   \r\n" + 
	        "    <link rel=\"STYLESHEET\" href=\"//static.bricklink.com/clone/css/page_catalogitem_pgtab.css\" type=\"text/css\">\r\n" + 
	        "</head>\r\n" + 
	        "<body>\r\n" + 
	        "    <div id=\"_idMainPGContents\">\r\n" + 
	        "        <center>\r\n" + 
	        "        <!--START-->        <table class=\"pcipgMainTable\" width=\"100%\" cellspacing=\"5\" cellpadding=\"0\">\r\n" + 
	        "            <tbody><tr>\r\n" + 
	        "                <td colspan=\"2\" class=\"pcipgMainHeader\">\r\n" + 
	        "                    Last 6 Months Sales:\r\n" + 
	        "                </td>\r\n" + 
	        "                <td colspan=\"2\" class=\"pcipgMainHeader\">\r\n" + 
	        "                    Current Items for Sale:\r\n" + 
	        "                </td>\r\n" + 
	        "            </tr>\r\n" + 
	        "            <tr>\r\n" + 
	        "                <td class=\"pcipgMainHeader\" width=\"25%\">New</td>\r\n" + 
	        "                <td class=\"pcipgMainHeader\" width=\"25%\">Used</td>\r\n" + 
	        "                <td class=\"pcipgMainHeader\" width=\"25%\">New</td>\r\n" + 
	        "                <td class=\"pcipgMainHeader\" width=\"25%\">Used</td>\r\n" + 
	        "            </tr>\r\n" + 
	        "            \r\n" + 
	        "            \r\n" + 
	        "            <tr>\r\n" + 
	        "                <td colspan=\"4\" style=\"padding: 5px; font-size: 12px;\">\r\n" + 
	        "                    \r\n" + 
	        "                    <img src=\"//static.bricklink.com/clone/img/box16Y.png\" width=\"16\" height=\"16\" border=\"0\" align=\"ABSMIDDLE\"> <img src=\"//static.bricklink.com/clone/img/box16N.png\" width=\"16\" height=\"16\" border=\"0\" align=\"ABSMIDDLE\"> Indicates whether or not the seller ships to your country.  Click on this icon to view the item in the seller's store.<br><br>\r\n" + 
	        "                    <img src=\"//static.bricklink.com/clone/img/statusV0.png\" width=\"10\" height=\"10\" border=\"0\" align=\"ABSMIDDLE\"> Indicates items which you sold or are listed in your Inventory and available for sale.<br>\r\n" + 
	        "                    <img src=\"//static.bricklink.com/clone/img/statusVA.png\" width=\"10\" height=\"10\" border=\"0\" align=\"ABSMIDDLE\"> Indicates items which you bought.         \r\n" + 
	        "                </td>\r\n" + 
	        "            </tr>\r\n" + 
	        "        </tbody></table>\r\n" + 
	        "        <!--END-->\r\n" + 
	        "        </center>\r\n" + 
	        "    </div>\r\n" + 
	        "\r\n" + 
	        "\r\n" + 
	        "</body></html>";
	
	public PullPGFromSite(int internalID, int colorID, CatalogPart part) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(2, 25);
		timeout = 25+randomNum;
		delay = 35+randomNum;
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
        if (output.equalsIgnoreCase(nullpart))
        {
           
            file = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/PriceGuides/Parts/nullparts/part_"+part.getPartNumber()+"_color_"+colorid+".txt");
        }
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
