package blcrawler.commands;

public interface Command {
	
	void execute();
	
	void queue();
	
	void stop();
	
	boolean executeImmediately();
	
	boolean executeNext();
	
	long getDelay();
	
	int getTimeout();
	
	boolean isFinished();

}
