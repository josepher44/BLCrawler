package blcrawler.commands.addpage;
import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;
import blcrawler.model.page.PartBrowse;

public class AddPartBrowse implements Command {
	
	private final String url;
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	
	public AddPartBrowse(String url) {
		timeout=10;
		delay=5;
		isFinished=false;
		this.url=url;
	}

	@Override
	public void execute() {
		new PartBrowse(url);
		new ConsoleOutput("CommandResult", "Page of type PartBrowse at url=" +url+ " successfully accessed and recorded");
		isFinished=true;
		
	}

	@Override
	public void queue() {
		if (GUIModel.getTaskTimer().queue.size()>0)
		{
			new ConsoleOutput("CommandResult", "Addition of url " + url + " added to queue. " + GUIModel.getTaskTimer().queue.size() + " tasks queued, standby");
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
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
	public long getDelay() {
		// TODO Auto-generated method stub
		return delay;
	}

}
