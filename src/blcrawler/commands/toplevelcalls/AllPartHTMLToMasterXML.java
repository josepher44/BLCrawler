package blcrawler.commands.toplevelcalls;

import java.io.File;
import java.util.ArrayList;

import blcrawler.commands.abstractcommands.InstantCommand;
import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;

/**
 * Top level command to build CatalogParts from the HTML, and update the parts database.
 * NOTE: This command works from the HTML folder, NOT the Parts folder. Parts contains XML files
 * with raw HTML as a major element, plus timestamp information. HTML in Parts contains errors,
 * which are addressed indirectly by the "FixHTML" command and corresponding database method.
 * FixHTML produces NEW files in the HTML folder, which are .html format without the extra info
 * contained in the XML files. This command, in its current form, processes the HTML folder. 
 * Eventually, the part scraping process should automatically fix the transcription errors which
 * are fixed by FixHTML, and the HTML folder should cease to be relevant. At this time, this 
 * command should be redirected to work from the Parts folder, not the HTML.
 * @author Joe Gallagher
 *
 */
public class AllPartHTMLToMasterXML extends InstantCommand {

	/*
	 * Particular fields
	 */
	String partID;			//The part currently being processed, by ID
	ArrayList<String> partIDs;	//All parts to be processed, by ID
	
	public AllPartHTMLToMasterXML() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() 
	{
		Thread thread = new Thread() 
		{
			public void run() 
			{
				//Initialize fields, set master read directory. i is a loop incrementor
				//used for operation count readouts
				File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/HTML/");
				partIDs = new ArrayList<>();
				int i=0;
				
				//Read off parts that already exist in memory from previous calls
				for (CatalogPart part : ConsoleGUIModel.getDatabase().getCatalogParts())
				{
					System.out.println("Part "+part.getPartNumber()+"exists");
				}
				System.out.println("Done with parts that exist, moving on");
				
				//For every file in the directory, read the part number from the filename. If it
				//isn't already in memory, build a CatalogPart by parsing the HTML
				for(File file: dir.listFiles()) 
				{
					String partNumber="";
					try
					{
						partNumber = 
								file.getAbsolutePath().substring(file.getAbsolutePath().indexOf('_')
										+1, file.getAbsolutePath().indexOf(".html"));
						
						//Check if part is in memory. If not, build it
						if (!ConsoleGUIModel.getDatabase().partExists(partNumber))
						{
							ConsoleGUIModel.getDatabase().addCatalogPart(new CatalogPart(
									file.getAbsolutePath()));
							i++;
							System.out.println("Built part# "+partNumber+". "+i+" of "+
													dir.listFiles().length);
						}
					}
					catch (Exception e)
					{
						if (!partNumber.equals(""))
						{
							//Part number is known, print trace and part
							e.printStackTrace();
							System.out.println("failure at part "+partNumber);
						}
						else
						{
							//Part number is unknown, print last processed file
							e.printStackTrace();
							System.out.println("failure at file "+file.getAbsolutePath()+"");
						}
					}
				}
				
				//Once all parts are in memory, build the master xml database from memory
				ConsoleGUIModel.getDatabase().buildMasterXML();
				
				//Finalize Execution
				isFinished = true;
				done();
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void queue() 
	{
		
	}

	@Override
	public void forceQuit() 
	{
		
	}
	
	@Override
	public void done() 
	{
		
	}
}
