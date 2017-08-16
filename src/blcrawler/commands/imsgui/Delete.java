package blcrawler.commands.imsgui;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Delete implements GUICommand
{
	public ObservableList<InventoryLocation> items;
	public ObservableList<Integer> locations;
	public Delete (ObservableList<InventoryLocation> selection, ObservableList<Integer> ids)
	{
		items = FXCollections.observableArrayList();
		items.addAll(selection);
		locations = FXCollections.observableArrayList();
		locations.addAll(ids);
		execute();
	}

	@Override
	public void execute()
	{

		System.out.println("Removing item count: "+items.size());
		ConsoleGUIModel.getImsgui().removeItems(items);

	}

	@Override
	public void undo()
	{
		ConsoleGUIModel.getImsgui().addItems(items, locations);

	}

}
