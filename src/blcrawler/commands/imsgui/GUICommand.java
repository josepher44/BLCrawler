package blcrawler.commands.imsgui;

/**
 * Interface which must be implemented by all GUI commands, to support undo functionality
 * @author Joe Gallagher
 *
 */
public interface GUICommand
{
    /**
     * This is called to execute the command from implementing class. The constructor should
     * always call execute at the end. It can also be called externally when pressing redo.
     */
    public abstract void execute();

    /**
     * This is called to undo last command.
     */
    public abstract void undo();
}
