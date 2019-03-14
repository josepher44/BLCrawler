package blcrawler.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import blcrawler.primatives.PriceGuideForSale;
import blcrawler.primatives.PriceGuideSale;

public class PriceGuide
{
    String filepath;
    String rawhtml;
    ArrayList<PriceGuideSale> salesmadenew;
    ArrayList<PriceGuideSale> salesmadeused;
    ArrayList<PriceGuideForSale> forsalenew;
    ArrayList<PriceGuideForSale> forsaleused;
    
    public PriceGuide(String partnumber, int colorID)
    {
        //Initialize lists
        salesmadenew = new ArrayList<>();
        salesmadeused = new ArrayList<>();
        forsalenew = new ArrayList<>();
        forsaleused = new ArrayList<>();
        
        filepath = "C:/Users/Owner/Documents/BLCrawler/Catalog/PriceGuides/Parts/part_"+partnumber+"_color_"+colorID+".txt";
        
        //Read from file
        rawhtml = "";
        try
        {
            rawhtml = new String ( Files.readAllBytes( Paths.get(filepath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //Form substrings for four columns
        String allsalesnew = rawhtml.substring(rawhtml.indexOf("table class=\"pcipgInnerTable\""), rawhtml.indexOf("td class=\"pcipgEvenColumn"));
        rawhtml = rawhtml.substring(rawhtml.indexOf("td class=\"pcipgEvenColumn"));
        String allsalesused = rawhtml.substring(0, rawhtml.indexOf("td class=\"pcipgOddColumn"));
        //System.out.println(allsalesused);
        rawhtml = rawhtml.substring(rawhtml.indexOf("td class=\"pcipgOddColumn"));
        String forsalenew = rawhtml.substring(0, rawhtml.indexOf("td class=\"pcipgEvenColumn"));
        rawhtml = rawhtml.substring(rawhtml.indexOf("td class=\"pcipgEvenColumn"));
        String forsaleused = rawhtml;
        
        Matcher matcher1 = Pattern.compile("<tr><td style=\"text-align: left; padding-left: 5px;\"></td><td>([0-9]+)</td><td>(~)?US \\$[0-9]+\\.+[0-9]{4}</td></tr>").matcher(allsalesnew);
        while (matcher1.find()) 
        {
           //String substring = allsalesnew.substring(matcher1.start(),matcher1.end());
           //salesmadenew.add(new PriceGuideSale(substring));
        }
        Pattern p2 = Pattern.compile("(<tr><td style=\"text-align: left; padding-left: 5px;\"><\\/td><td>([0-9]+)<\\/td><td>(~)?US \\$[0-9]+\\.+[0-9]{4}</td></tr>)");
        Matcher matcher2 = p2.matcher(allsalesused);
        while (matcher2.find()) 
        {
           String substring = matcher2.group(1);
           salesmadeused.add(new PriceGuideSale(substring));
           System.out.println(substring);
        }
        Matcher matcher3 = Pattern.compile("a.a").matcher(forsalenew);
        while (matcher3.find()) 
        {
           //String substring = allsalesnew.substring(matcher3.start(),matcher3.end());
           //salesmadenew.add(new PriceGuideSale(substring));
        }
        Matcher matcher4 = Pattern.compile("a.a").matcher(forsaleused);
        while (matcher4.find()) 
        {
           //String substring = allsalesnew.substring(matcher4.start(),matcher4.end());
           //salesmadenew.add(new PriceGuideSale(substring));
        }
        
    }
    
    public Double getSalesUsedQuantityAverage()
    {
        int quantity = 0;
        Double cost = 0.0;
        for (PriceGuideSale pg : salesmadeused)
        {
            quantity = quantity+pg.getQuantity();
            cost = cost+pg.getQuantity()*pg.getPrice();
        }
        return cost/quantity;
    }
}
