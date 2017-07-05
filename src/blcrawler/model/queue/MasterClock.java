package blcrawler.model.queue;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.ConsoleOutput;

public class MasterClock
{
	public long currentStep;
	public MasterClock()
	{
		currentStep = 0;
		Thread thread = new Thread() {
			public void run() {
				while(true)
				{
					try {
						sleep(200);
						currentStep++;
						//new ConsoleOutput("Master Clock Step Count", String.valueOf(currentStep));
						executeQueues();
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
	public void executeQueues()
	{
		if (ConsoleGUIModel.getSelenium() != null)
		{
			ConsoleGUIModel.getSelenium().executeAllQueues();
			//System.out.println("executing queues");
		}
	}
	
	public long getCurrentStep()
	{
		return currentStep;
	}
	public void setCurrentStep(long currentStep)
	{
		this.currentStep = currentStep;
	}
	
	
}
