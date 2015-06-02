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
		
		sortUrl();
		//GUIModel.getSeleniumModel().getURL(url);
		
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
	
	
	public void sortUrl() {
		String sortStringA = url.substring(0, 50);	//length of http://www.bricklink.com/browseList.asp?itemType=P
		new ConsoleOutput("URL Interpretation", sortStringA);
	}

}
