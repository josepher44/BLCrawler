package blcrawler.commands;

import blcrawler.commands.templates.Command;
import blcrawler.commands.templates.InstantCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ReadBLSummary extends InstantCommand
{

    @Override
    public void execute()
    {
        System.out.println("Reading summary data for all parts in bricklink catalog");
    

        String partsummarypath = "C:/Users/Owner/Documents/BLCrawler/parts.xml";
        File partsummaryfile = new File(partsummarypath);
    
        SAXBuilder builder3 = new SAXBuilder();
        Document readDoc3 = null;
    
    
        try
        {
            readDoc3 = builder3.build(partsummaryfile);
        }
        catch (JDOMException | IOException e)
        {
            e.printStackTrace();
        }
    
    
        Element rootElement = readDoc3.getRootElement();
    
        List list = rootElement.getChildren("ITEM");
    
        ArrayList<String> partsDone = new ArrayList<String>();
    
    
        int k=0;
        File f;
        for (int i = 0; i < list.size(); i++)
        {
    
            Element node = (Element) list.get(i);
            //System.out.println("Reading part "+(i+1)+" of "+list.size());
    
            f = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/"
                    +"part_"+node.getChildText("ITEMID")+".xml");
            if(f.exists())
            {
    
                //System.out.println("file already exists for part "+node.getChildText("ITEMID"));
            }
            else
            {
                try
                {
                    Element part = new Element("part");
                    Document subdoc = new Document();
                    subdoc.setRootElement(part);
    
                    Element blid = new Element("blid");
                    blid.setText(node.getChildText("ITEMID"));
    
    
    
                    subdoc.getRootElement().addContent(blid);
    
                    // new XMLOutputter().output(doc, System.out);
                    XMLOutputter xmlOutput = new XMLOutputter();
    
                    // display nice nice
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(subdoc, new FileWriter("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/"
                            +"part_"+node.getChildText("ITEMID")+".xml"));
    
                    System.out.println(node.getChildText("ITEMID")+" File Saved!");
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
    
            }
    
    //      if (node.getChildText("ITEMID").contains("p"))
    //      {
    //          if (partsDone.contains(node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p'))))
    //          {
    //              System.out.println("Patterned entry for part number "+node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p'))
    //                      +" added, pattern count at "+k);
    //              k++;
    //          }
    //          else
    //          {
    //              partsDone.add(node.getChildText("ITEMID").substring(0, node.getChildText("ITEMID").indexOf('p')));
    //          }
    //      }
    //      else
    //      {
    //          if (partsDone.contains(node.getChildText("ITEMID")))
    //          {
    //              System.out.println("Patterned entry for part number "+node.getChildText("ITEMID")
    //                      +" added, pattern count at "+k);
    //              k++;
    //          }
    //          else
    //          {
    //              partsDone.add(node.getChildText("ITEMID"));
    //          }
    //
    //      }
    
            //System.out.println("Slot: "+node.getChildText("slot"));
            //System.out.println("Size: "+node.getChildText("size"));
        }
        
    }
}
