package blcrawler.commands.imsgui;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.inventorylot.InventoryLocation;

/**
 * Add a part to the IMSGUI system. The part is represented with an InventoryLocation class, 
 * since it carries both part data and storage information
 * @author Joe Gallagher
 *
 */
public class AddPartIMSGUI implements GUICommand
{
	/*
	 * Particular fields
	 */
	public InventoryLocation item;	//The item to add, represented as an inventoryLocation
	public Integer indecies;		/*The index from the top of the tableview to enter the part in
	*								IE: 0 will put it at the top of the table
	*/
	
	/**
	 * Constructor
	 * @param i the item details as an InventoryLocation
	 * @param id the index to insert the part at. IE: 0 will go at the top, 1 will be 1 down, etc.
	 */
	public AddPartIMSGUI (InventoryLocation i, Integer id)
	{
		item = i;
		indecies = id;
		execute();	//Note that unlike queue-based commands, which are executed external to the
					//class via the queue, IMSGUI commands must call their own execute
	}

	@Override
	public void execute()
	{
		ConsoleGUIModel.getImsgui().addItem(item, indecies);
	}

	@Override
	public void undo()
	{
		ConsoleGUIModel.getImsgui().removeItem(item);
	}
}
