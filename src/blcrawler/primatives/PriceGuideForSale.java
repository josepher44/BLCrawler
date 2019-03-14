package blcrawler.primatives;

public class PriceGuideForSale
{
    /*
    "<tr><td style=\"text-align: left;\" nowrap=\"\"><img style=\"margin-left: 5px;\" src=\"//static.bricklink.com/clone/img/flagsS/DE.gif\" "
    + "alt=\"Germany\" title=\"Germany\" width=\"14\" height=\"12\" align=\"ABSMIDDLE\">"
    + "<a href=\"http://www.bricklink.com/store.asp?sID=315556&amp;itemID=68661890\">"
    + "<img src=\"//static.bricklink.com/clone/img/box16Y.png\" style=\"margin-left: 5px;\" alt=\"Store: MagicGreenBus\" title=\"Store: MagicGreenBus\" "
    + "width=\"12\" height=\"12\" border=\"0\" align=\"ABSMIDDLE\"></a></td><td>3</td><td style=\"text-align: left\"></td><td>~US $0.5380</td></tr>"
    */
    String country;
    int storeID;
    Boolean shipstome;
    String itemLink;
    int quantity;
    int bulk;
    Double price;
    
    public PriceGuideForSale(String html)
    {
        try
        {
            country = html.substring(html.indexOf("title=")+7, html.indexOf("\" width"));
            storeID = Integer.valueOf(html.substring(html.indexOf("sID=")+4, html.indexOf("&amp;itemID")));
            if (html.contains("box16Y.png"))
            {
                shipstome = true;
            }
            else
            {
                shipstome = false;
            }
            itemLink = html.substring(html.indexOf("http://www.bricklink.com/store"), html.indexOf("\"><img src=\"//static"));
            quantity = Integer.valueOf(html.substring(html.indexOf("</a></td><td>")+13, html.indexOf("</td><td style=\"text-align: left")));
            if (html.contains("(x"))
            {
                String bulkstring = html.substring(html.indexOf("(x")+2);
                bulk = Integer.valueOf(bulkstring.substring(2,bulkstring.indexOf(")")));
            }
            else
            {
                bulk = 1;
            }
            price = Double.valueOf(html.substring(html.indexOf('$')+1, html.indexOf("</td></tr>")));
        }
        catch (Exception e)
        {
            System.out.println("Invalid HTML Format passed. HTML was "+html);
        }
        
    }
    

}
