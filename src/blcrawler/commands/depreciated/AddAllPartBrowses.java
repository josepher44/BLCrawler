package blcrawler.commands.depreciated;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.page.PartBrowseIndex;

/**
 * Depreciated command for identifying all part numbers via the browse for sale page. Should not
 * be used
 * @author Joe Gallagher
 *
 */
@Deprecated
public class AddAllPartBrowses implements Command 
{
	
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
	public AddAllPartBrowses() {
		timeout=10;
		delay=5;
		isFinished=false;
	}

	@Override
	public void execute() 
	{
		/*
		 * Create a depreciated PartBrowseIndex class referencing the top level 200-ish link page
		 * on bricklink
		 */
		PartBrowseIndex index = new PartBrowseIndex("http://www.bricklink.com/browseTree.asp?itemType=P");
		new ConsoleOutput("CommandResult", "Page of type PartBrowseIndex at url=http://www.bricklink.com/browseTree.asp?itemType=P successfully accessed and recorded");
		index.listPartBrowseMasterPages();
		
		//Finish execution
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
		queueID=id;
	}

	@Override
	public void done() 
	{
		
	}
}
