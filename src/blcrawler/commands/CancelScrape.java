package blcrawler.commands;

import blcrawler.commands.abstractcommands.InstantCommand;
import blcrawler.model.ConsoleGUIModel;

/**
 * Hard-cancels a scrape already in process, preserving results already generated. 
 * TODO: Edit to work with force quit once that is stable
 * @author Joe Gallagher
 *
 */
public class CancelScrape extends InstantCommand 
{
	public CancelScrape() 
	{
		isFinished = false;
	}
	
	@Override
	public void execute() {
		ConsoleGUIModel.getSelenium().clearAllQueues();
	}


	@Override
	public void queue() 
	{
		
	}

	@Override
	public void forceQuit() 
	{
		
	}

	@Override
	public void done() 
	{
		
	}
}
