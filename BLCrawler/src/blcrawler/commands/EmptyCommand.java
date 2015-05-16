package blcrawler.commands;

public class EmptyCommand implements Command {

	public void EmptyCommand() {
		
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
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
		return 0;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

}
