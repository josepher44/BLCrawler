package blcrawler.model.queue;

import blcrawler.commands.templates.Command;

public class QueueEntry {
	private Command command;
	
	public QueueEntry(Command command) {
		this.command=command;
		
	}

	/**
	 * @return the command
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Command command) {
		this.command = command;
	}

}
