package Commands;

import blcrawler.model.ConsoleOutput;

public class InvalidCommand implements Command {



	public InvalidCommand() {
		execute();
	}

	@Override
	public void execute() {

		new ConsoleOutput("CommandResult", "ERROR: Invalid command name or type");
		
	}

	@Override
	public void queue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
