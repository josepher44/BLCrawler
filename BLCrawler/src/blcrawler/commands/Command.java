package blcrawler.commands;

public interface Command {
	
	void execute();
	
	void queue();
	
	void stop();

}
