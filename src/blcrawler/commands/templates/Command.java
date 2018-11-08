package blcrawler.commands.templates;

public interface Command 
{
	
	/**
	 * Called when the command reaches the top of the queue, and is run
	 */
	void execute();
	
	/**
	 * Flag for whether a command should be executed immediately, or added to a queue. Commands
	 * which directly utilize net resources, such as API or selenium calls, should always be false.
	 * Most other commands can be safely executed immediately, especially those with near-zero 
	 * execution times. 
	 * @return true if command should be executed immediately, false if queueable
	 */
	boolean executeImmediately();
	
	/**
	 * <P>Flag for whether a queued command should be appended to the start or end of a queue.
	 * Should be used only for network commands with high priority and short runtime of both the 
	 * command and their dependants. Copies of commands flagged to execute next may exist, but 
	 * should always be called one at a time, usually manually. Always false for commands generated
	 * algorithmically in large batches, such as scrape commands, or for commands which generate 
	 * their own child commands. ExecuteNext and ExecuteImmediately are exclusive of each other, and
	 * should never both be set to true.</P>
	 * 
	 * <P>Example use: Quick manual lookup of sale data on a single part via API, while an API price 
	 * guide scrape is continuously running via the same tor/selenium instances. The manual lookup 
	 * is prioritized first, but should not interrupt the lookup in the process of the currently 
	 * executing command, as would happen if executeImmediate were true. All other elements of the 
	 * queue are shifted down one place</P>
	 * @return true if command should be added to the next execution instance of its queue, false
	 * otherwise
	 */
	boolean executeNext();
	
	/**
	 * <P>Get the delay to add in between the execution of the previous command, and beginning to 
	 * run this one. Should be zero in most cases. Used to space out network requests. </P>
	 * 
	 * <P>Possibly depreciated, as envisioned functionality is better achieved via a scheduler than 
	 * with queues</P>
	 * @return The delay, measured in fifths of seconds
	 */
	/*TODO: Explicit functional testing, to verify delay and timeout behave as intended and in a 
	constructive manner
	TODO: Change from fifths of seconds to a meaningful unit, likely ms with a class for conversion
	*/
	long getDelay();
	
	/**
	 * <P>Get the timeout for the command, after which it should be force-stopped. Setting this to zero
	 * will allow the command to run indefinitely. This should be used on commands which have the
	 * potential to hang, such as selenium launches and network actions. </P>
	 * 
	 * <P>Not depreciated, but not currently implemented anywhere</P>
	 * @return The timeout, measured in fifths of seconds
	 */
	/*TODO: Explicit functional testing, to verify delay and timeout behave as intended and in a 
	constructive manner
	TODO: Change from fifths of seconds to a meaningful unit, likely ms with a class for conversion
	*/
	int getTimeout();
	
	/**
	 * Returns whether a command has completed its execution. Can be directly set to true for
	 * instantaneous commands, otherwise, should be set to false in the constructor, true at the
	 * end of execute()
	 * @return True if a command has finished executing, false otherwise. 
	 */
	boolean isFinished();
	
	/**
	 * Actions to take upon queueing a command. Usually just used for console prints. Nothing time
	 * consuming should go here. 
	 */
	void queue();
	
	/**
	 * Safely force-stop a command in the process of execution. Called when a command reaches its
	 * timeout, can also be called directly
	 */
	void forceQuit();
	
	/**
	 * Actions to take upon safe completion of a command and all its sub-commands
	 */
	void done();
	
	/**
	 * Affiliates the command with a specific Queue ID. Queue IDs are socks port numbers for
	 * selenium delay queues, or 0 for the instant queue. More queue types may be added in the
	 * future
	 * @param id The queue ID. Note that this is represented as a string in many other places
	 */
	void setQueueID(int id);

}
