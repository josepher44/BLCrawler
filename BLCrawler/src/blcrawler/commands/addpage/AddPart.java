package blcrawler.commands.addpage;
import java.util.Random;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;
import blcrawler.model.page.Part;

public class AddPart implements Command {
	
	private final String url;
	private final int timeout;
	private final int delay;
	private boolean isFinished;
	
	public AddPart(String url) {
		int max = 3;
		int min = 0;
		Random random = new Random();
		timeout=30+random.nextInt(max - min + 1) + min;
		delay=0+random.nextInt(max - min + 1) + min;
		isFinished=false;
		this.url=url;
	}

	@Override
	public void execute() {
		if (GUIModel.getPageManager().partFileMap.containsValue(url))
		{
			new ConsoleOutput("PageManager", "Part page of url "+url+" already stored.");
		
		}
		else
		{
			new Part(url);
			new ConsoleOutput("CommandResult", "Page of type Part at url=" +url+ " successfully accessed and recorded");
		}
		isFinished=true;
		
	}

	@Override
	public void queue() {
		if (GUIModel.getTaskTimer().queue.size() > 0)
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
