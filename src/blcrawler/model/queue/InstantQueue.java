package blcrawler.model.queue;

import java.io.IOException;
import java.util.ArrayList;

import blcrawler.commands.residuals.InitialCommand;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.selenium.SeleniumModel;

public class InstantQueue extends Queue
{
	public ArrayList<QueueEntry> queue;
	public int id;
	public SeleniumModel selenium;
	public Thread thread;
	
	public Command currentCommand;
	public boolean nextStepFlag;
	public long delay;
	
	public InstantQueue()
	{
		queue = new ArrayList<>();
		nextStepFlag=false;
		currentCommand=new InitialCommand();
		thread = new Thread() {
			public void run() {
				while(true)
				{
					try {
						sleep(10);
						executeQueue();
					} catch (InterruptedException e) {
						System.out.println("ERR: Timer thread interrupted");
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
		
	}
	
	public void addToBeginning()
	{
		
	}
	
	public void addToQueue(Command command) {
		if (command.executeImmediately())
		{
			command.execute();
		}
		else if (command.executeNext())
		{
			queue.add(0, new QueueEntry(command));
		}
		else
		{
			queue.add(new QueueEntry(command));
		}
		
	}
	
	public void executeQueue() {
		if ((currentCommand.equals(null)||currentCommand.isFinished())&&queue.size()!=0)
		{
			if (!nextStepFlag)
			{
				nextStepFlag=true;
				delay=queue.get(0).getCommand().getDelay();
				
			}
			else
			{
				delay--;
			}
			if (delay<=0)
			{
				currentCommand = queue.get(0).getCommand();
				currentCommand.execute();
				queue.remove(0);
				nextStepFlag=false;			
			}
			

		}
		
		//Possibly a bad idea, but it's been working wonders on memory allocation
		//System.gc();
		//new ConsoleOutput("SeleniumDistro", "Executed Instant Queue");
	}
	
	public void execute()
	{
		executeQueue();
	}


	
	
}
