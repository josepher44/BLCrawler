package blcrawler.commands;

public interface Command {
	
	void execute();
	
	boolean executeImmediately();
	
	boolean executeNext();
	
	long getDelay();
	
	int getTimeout();
	
	boolean isFinished();
	
	void queue();
	
	void stop();
	
	void setQueueID(int id);

}
