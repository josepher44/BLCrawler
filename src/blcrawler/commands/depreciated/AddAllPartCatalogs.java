package blcrawler.commands.depreciated;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.PartCatalogIndex;

/**
 * Deprecated command to add parts via the part catalog page
 * @author Joe Gallagher
 *
 */

@Deprecated
public class AddAllPartCatalogs implements Command {
	
	/*
	 * Standard Fields
	 */
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	int queueID;
	
	/**
	 * Constructor
	 */
	public AddAllPartCatalogs() 
	{
		timeout=10;
		delay=5;
		isFinished=false;
	}

	@Override
	public void execute() 
	{
		//Create deprecated class PartCatalogIndex and pull all page links
		PartCatalogIndex index = new PartCatalogIndex("http://www.bricklink.com/Catalog/Tree.asp?itemType=P");
		new ConsoleOutput("CommandResult", "Page of type PartCatalogIndex at url=http://www.bricklink.com/Catalog/Tree.asp?itemType=P successfully accessed and recorded");
		index.listPartCatalogMasterPages();
		
		//Finish Execuiton
		isFinished=true;
		done();
	}

	@Override
	public boolean executeImmediately() 
	{
		return false;
	}

	@Override
	public boolean executeNext() 
	{
		return false;
	}

	@Override
	public long getDelay() 
	{
		return delay;
	}

	@Override
	public int getTimeout() 
	{
		return timeout;
	}

	@Override
	public boolean isFinished() 
	{
		return isFinished;
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
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

}
