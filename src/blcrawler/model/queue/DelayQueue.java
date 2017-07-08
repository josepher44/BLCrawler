package blcrawler.model.queue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import blcrawler.commands.Command;
import blcrawler.commands.Delay;
import blcrawler.commands.InitialCommand;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.selenium.SeleniumModel;

public class DelayQueue extends Queue
{
	public ArrayList<QueueEntry> queue;
	public int id;
	public SeleniumModel selenium;
	public int commandsExecuted;
	public int limit;
	public boolean execute;
	public boolean clear;
	
	public Command currentCommand;
	public boolean nextStepFlag;
	public long delay;
	
	public DelayQueue(int ID) 
	{
		id = ID;
		execute = false;
		queue = new ArrayList<>();
		//new ConsoleOutput("SeleniumDistro", "Created new Selenium Queue, ID# " + String.valueOf(id));
		selenium = new SeleniumModel(String.valueOf(id));
		currentCommand = new InitialCommand();
		commandsExecuted = 0;
		limit = ThreadLocalRandom.current().nextInt(40, 150);
		clear = false;
		

		Thread thread = new Thread() {
			public void run() {
				while(true)
				{
					try {
						sleep(200);
						//new ConsoleOutput("Master Clock Step Count", String.valueOf(currentStep));
						executeQueue();
					} catch (InterruptedException e) {
						System.out.println("ERR: delayQueue thread interrupted");
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.setDaemon(true);
		thread.start();
		
		
	}
	
	public void add(Command command)
	{
		queue.add(new QueueEntry(command));
	}
	
	public void addToBeginning(Command command)
	{
		queue.add(0,new QueueEntry(command));
	}
	
	public void empty()
	{
		clear = true;
	}
	
	public void executeQueue() {
		double percentDone;
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
				percentDone = 1.0 - ((double)delay/(double)queue.get(0).getCommand().getDelay());
				
			}
			if (delay<=0)
			{
				currentCommand = queue.get(0).getCommand();
				currentCommand.execute();
				commandsExecuted++;
				System.out.println("Current commands for module "+id+" is "+commandsExecuted+". Current limit is "+limit);
				queue.remove(0);
				nextStepFlag=false;
				
				if (clear)
				{
					queue.clear();
					clear = false;
				}
				/*
				 * totalCommands++;
				 * if (totalCommands%75 == 0)
					{
						try {
							ConsoleGUIModel.getSeleniumModel().relaunchTor();
						} catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				 */
				
					
			}
			
			if (commandsExecuted>=limit)
			{
				System.out.println("Called exit tor, commandsExecuted = "+commandsExecuted+" limit= "+limit);
				commandsExecuted = 0;
				limit = ThreadLocalRandom.current().nextInt(90, 150);//40-60 for part scraping
				ConsoleGUIModel.getSelenium().relaunch(id);
				addToBeginning(new Delay(125));
				
			}
			

		}
		
		//Possibly a bad idea, but it's been working wonders on memory allocation
		//System.gc();
		//new ConsoleOutput("SeleniumDistro", "Executed Selenium Queue ID# "+ String.valueOf(id));
	}
	
	public void execute()
	{
		executeQueue();
	}

	public SeleniumModel getSelenium()
	{
		return selenium;
	}

	public void setSelenium(SeleniumModel selenium)
	{
		this.selenium = selenium;
	}
	
	public int getQueueSize()
	{
		return queue.size();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getCommandsExecuted()
	{
		return commandsExecuted;
	}

	public void setCommandsExecuted(int commandsExecuted)
	{
		this.commandsExecuted = commandsExecuted;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public boolean isExecute()
	{
		return execute;
	}

	public void setExecute(boolean execute)
	{
		this.execute = execute;
		
	}
}
