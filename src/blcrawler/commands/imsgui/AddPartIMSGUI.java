package blcrawler.commands.imsgui;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddPartIMSGUI implements GUICommand
{
	public InventoryLocation item;
	public Integer location;
	public AddPartIMSGUI (InventoryLocation i, Integer id)
	{
		item = i;
		location = id;
		execute();
	}

	@Override
	public void execute()
	{

		ConsoleGUIModel.getImsgui().addItem(item, location);


	}

	@Override
	public void undo()
	{
		ConsoleGUIModel.getImsgui().removeItem(item);

	}

}
