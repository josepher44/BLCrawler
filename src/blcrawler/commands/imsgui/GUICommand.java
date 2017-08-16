package blcrawler.commands.imsgui;

public interface GUICommand
{
    /**
     * This is called to execute the command from implementing class.
     */
    public abstract void execute();

    /**
     * This is called to undo last command.
     */
    public abstract void undo();
}
