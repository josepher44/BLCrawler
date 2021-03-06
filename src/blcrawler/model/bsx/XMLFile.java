package blcrawler.model.bsx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFile
{
	Document doc;
	String filepath;
	public XMLFile(String path)
	{
		filepath = path;
		File file = new File(path);
		
		SAXBuilder builder = new SAXBuilder();
		doc = null;
		
		try
		{
			doc = builder.build(file);
		}
		catch (Exception e)
		{
			System.out.println("Exception generated by file "+path);
			//e.printStackTrace();
		}
	}
	
	public void appendTag(String tag, String body)
	{
		Element node = doc.getRootElement();
		
		Element element = new Element(tag);
		element.setText(body);
		
		node.addContent(element);
		
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try
		{
			xmlOutput.output(doc, new FileWriter(filepath));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
