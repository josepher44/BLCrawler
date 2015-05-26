package blcrawler.commands;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;

public class AddUrl implements Command {
	
	private String url;
	
	public AddUrl(String url) {
		this.url=url;
	}

	@Override
	public void execute() {
		
		new ConsoleOutput("CommandResult", "URL input: "+ url);
		GUIModel.getSeleniumModel().getURL(url);
		
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
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
		return false;
	}

}
