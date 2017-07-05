package blcrawler.commands.addpage;
import java.util.Random;

import blcrawler.commands.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.page.Part;

public class AddPart implements Command {
	
	private final String value;
	private boolean isFinished;
	private int queueID;
	
	public AddPart(String value) {
		isFinished=false;
		this.value=value;
	}

	@Override
	public void execute() {
//		if (ConsoleGUIModel.getPageManager().partFileMap.containsValue(url))
//		{
//			new ConsoleOutput("PageManager", "Part page of url "+url+" already stored.");
//		
//		}
//		else
//		{
//			new Part(url);
//			new ConsoleOutput("CommandResult", "Page of type Part at url=" +url+ " successfully accessed and recorded");
//		}
		new Part(value);
		System.out.println("Execution of part "+value);
		isFinished=true;
		
	}

	@Override
	public boolean executeImmediately() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean executeNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
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
			//new ConsoleOutput("CommandResult", "Addition of url " + value + " added to queue. " + ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued, standby");
			System.out.println("Part "+value+" queued");
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
