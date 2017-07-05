package blcrawler.controller;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;

public class GUIMainController {
	
	private static ConsoleGUIModel gui;
	/**
	 * @return the gui
	 */
	public static ConsoleGUIModel getGui() {
		return gui;
	}
	
	/**
	 * @param gui the gui to set
	 */
	public static void setGui(ConsoleGUIModel gui) {
		GUIMainController.gui = gui;
	}
	
	  
	
	Action CommandEntered;



	public GUIMainController(ConsoleGUIModel gui) {
		this.gui = gui;
		
		CommandEntered = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsoleGUIModel.getConsoleController().initializeCommand();


				
				
				}
		  };
	}



	public void buildGUI() {

	}



	/**
	 * @return the thing
	 */
	public Action getCommandEntered() {
		return CommandEntered;
	}


	protected void sendInToOut(String input) {
		new ConsoleOutput("Command", input);
	}



	/**
	 * @param thing the thing to set
	 */
	public void setCommandEntered(Action thing) {
			CommandEntered = thing;
	}

}
