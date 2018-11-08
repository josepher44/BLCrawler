package blcrawler.model.queue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import blcrawler.commands.TimerTest;
import blcrawler.commands.Timestamp;
import blcrawler.commands.residuals.InitialCommand;
import blcrawler.commands.templates.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;

public class TaskTimer {
	public long millis;
	public long startTime;
	public long endTime;
	public long currentStep;
	public long delay;
	public Thread thread;
	public Command currentCommand;
	public int totalCommands;
	public ArrayList<QueueEntry> queue;
	public boolean nextStepFlag;

	private boolean done;
	
	
	public TaskTimer()
	{
		totalCommands = 0;
		nextStepFlag=false;
		currentCommand=new InitialCommand();
		thread = new Thread() {
			public void run() {
				while(true)
				{
					try {
						sleep(200);
						currentStep++;
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
		startTime=System.currentTimeMillis();
		
		queue=new ArrayList<QueueEntry>();
		
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
		double percentDone;
		if ((currentCommand.equals(null)||currentCommand.isFinished())&&queue.size()!=0)
		{
			if (!nextStepFlag)
			{
				nextStepFlag=true;
				delay=queue.get(0).getCommand().getDelay();
				ConsoleGUIModel.getGuiView().setTaskBar(0);
			}
			else
			{
				delay--;
				percentDone = 1.0 - ((double)delay/(double)queue.get(0).getCommand().getDelay());
				ConsoleGUIModel.getGuiView().setTaskBar((int)(percentDone*100));
			}
			if (delay<=0)
			{
				currentCommand = queue.get(0).getCommand();
				currentCommand.execute();
				ConsoleGUIModel.getGuiView().setTaskBar(0);
				queue.remove(0);
				nextStepFlag=false;
				totalCommands++;
				if (totalCommands%75 == 0)
				{
					try {
						ConsoleGUIModel.getSeleniumModel().relaunchTor();
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
			

		}
		
		//Possibly a bad idea, but it's been working wonders on memory allocation
		//System.gc();
	}
	
	public long get() {
		return millis;
	}
	
	public boolean isDone() {
		if (System.currentTimeMillis()>=endTime)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void set(long millis)
	{
	}

}
