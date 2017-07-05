package blcrawler.commands.addpage;
import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.PartBrowse;
import blcrawler.model.page.PartCatalog;

public class AddPartCatalog implements Command {
	
	private final String url;
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	private int queueID;
	
	public AddPartCatalog(String url) {
		timeout=30;
		delay=20;
		isFinished=false;
		this.url=url;
	}

	@Override
	public void execute() {
		if (ConsoleGUIModel.getPageManager().partCatalogFileMap.containsValue(url))
		{
			new ConsoleOutput("PageManager", "Partcatalog page of url "+url+" already stored.");
		
		}
		else
		{
			new PartCatalog(url);
			new ConsoleOutput("CommandResult", "Page of type Partcatalog at url=" +url+ " successfully accessed and recorded");
		}
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
		if (ConsoleGUIModel.getTaskTimer().queue.size() > 0)
		{
			new ConsoleOutput("CommandResult", "Addition of url " + url + " added to queue. " + ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued, standby");
		}
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
