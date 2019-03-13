package blcrawler.primatives;

import java.util.Date;

public class PriceGuideSale
{
    int quantity;
    double price;
    Date date;
    
    public PriceGuideSale(String htmltag)
    {   
        String qstring1 = htmltag.substring(htmltag.indexOf("</td><td>")+9);
        quantity = Integer.valueOf(qstring1.substring(0, qstring1.indexOf("</td>")));
        price = Double.valueOf(htmltag.substring(htmltag.indexOf('$'), htmltag.indexOf("</td></tr>")));
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    
    
}
