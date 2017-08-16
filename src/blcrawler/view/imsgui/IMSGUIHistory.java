package blcrawler.view.imsgui;

import java.util.ArrayList;

import blcrawler.commands.imsgui.*;

public class IMSGUIHistory
{
	static ArrayList<GUICommand> commands = new ArrayList<>();
	static ArrayList<GUICommand> redoStack = new ArrayList<>();

	public static ArrayList<GUICommand> getCommands()
	{
		return commands;
	}

	public static void addCommand(GUICommand command)
	{
		commands.add(0, command);
		redoStack.clear();
	}

	private static void addInternalCommand(GUICommand command)
	{
		commands.add(0, command);
	}

	public static GUICommand getMostRecentCommand()
	{
		return commands.get(0);
	}

	public static GUICommand getCommand(int index)
	{
		return commands.get(index);
	}

	public static void undo()
	{
		System.out.println("Commands: "+commands.size());
		if (commands.size()>0)
		{
			redoStack.add(0, commands.get(0));
			commands.remove(0);
			redoStack.get(0).undo();
		}
	}

	public static void redo()
	{
		if(redoStack.size()>0)
		{
			addInternalCommand(redoStack.get(0));
			redoStack.remove(0);
			commands.get(0).execute();
		}

	}

}
