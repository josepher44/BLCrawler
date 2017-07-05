package blcrawler.commands.addpage;
import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.PartBrowse;
import blcrawler.model.page.PartBrowseIndex;

public class AddAllPartBrowses implements Command {
	
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	private int queueID;
	
	public AddAllPartBrowses() {
		timeout=10;
		delay=5;
		isFinished=false;
	}

	@Override
	public void execute() {
		
		PartBrowseIndex index = new PartBrowseIndex("http://www.bricklink.com/browseTree.asp?itemType=P");
		new ConsoleOutput("CommandResult", "Page of type PartBrowseIndex at url=http://www.bricklink.com/browseTree.asp?itemType=P successfully accessed and recorded");
		index.listPartBrowseMasterPages();
		isFinished=true;
		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return false;
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

	}



	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;
		
	}

}
