package Commands;

public interface Command {
	
	void execute();
	
	void queue();
	
	void stop();

}
