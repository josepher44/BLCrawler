package blcrawler.commands.residuals;

import blcrawler.commands.templates.QueueCommandStandard;

/**
 * Silent delay command. Used to buffer a selenium queue's startup time, by adding it prior to
 * beginning scrape actions
 * @author Joe Gallagher
 *
 */
public class Delay extends QueueCommandStandard 
{

	 /**
	  * Constructor
	  * @param n_delay Time to delay in fifths of seconds
	  */
	public Delay(int n_delay) {
		timeout=n_delay;
		delay=n_delay;
		isFinished=false;
	}

	@Override
	public void execute() 
	{
		//new ConsoleOutput("CommandResult", "Timer complete");
		isFinished=true;
	}
	
	@Override
	public void queue() {
		/*new ConsoleOutput("CommandResult", "Timer started. Standby. " + 
					ConsoleGUIModel.getTaskTimer().queue.size() + " tasks queued");*/
	}

	@Override
	public void forceQuit() 
	{
		
	}
	
	public void setQueueID(int id)
	{
		this.queueID=id;	
	}

	@Override
	public void done() 
	{
		
	}
}
