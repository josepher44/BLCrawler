package blcrawler.commands.imsgui;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Delete implements GUICommand
{
	
	/*
	 * Particular Fields
	 */
	public ObservableList<InventoryLocation> items;	//The items to be deleted
	public ObservableList<Integer> indices;	//The indices in the table of the selected items
	
	/**
	 * Constructor
	 * @param selection	List of selected InventoryLocations. Typical use is, 
	 * inventoryView.getSelectionModel().getSelectedItems()
	 * @param ids List of selected inventory indecies. Typical use is, 
	 * inventoryView.getSelectionModel().getSelectedIndices()
	 */
	public Delete (ObservableList<InventoryLocation> selection, ObservableList<Integer> ids)
	{
		//Add all selected InventoryLocations to items
		items = FXCollections.observableArrayList();	
		items.addAll(selection);
		
		//Add all selected index IDs to indices. Only used for undo
		indices = FXCollections.observableArrayList();
		indices.addAll(ids);
		execute();
	}

	@Override
	public void execute()
	{
		//Remove all items on the list
		System.out.println("Removing item count: "+items.size());
		ConsoleGUIModel.getImsgui().removeItems(items);

	}

	@Override
	public void undo()
	{
		//Reinsert all items, at each corresponding index
		ConsoleGUIModel.getImsgui().addItems(items, indices);
	}
}
