package blcrawler.model;

public class Command {
	private String commandType;
	
	public Command(String commandType) {
		this.commandType = commandType;
		
		if (commandType.equals("invalid"))
		{
			new ConsoleOutput("CommandLib", "ERROR: Invalid command name or type");
		}
	}

}
