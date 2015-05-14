package blcrawler.controller;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;

public class GUIMainController {
	
	private static GUIModel gui;
	Action CommandEntered;
	
	public GUIMainController(GUIModel gui) {
		this.gui = gui;
		
		CommandEntered = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.getConsoleController().InterpretText(gui.getGuiView().getCLText());
				sendInToOut(gui.getGuiView().getCLText());
				gui.getGuiView().clearConsoleIn();
				
				
				}
		  };
	}
	
	  
	
	protected void sendInToOut(String input) {
		new ConsoleOutput("Command", input);
	}



	/**
	 * @return the gui
	 */
	public static GUIModel getGui() {
		return gui;
	}



	/**
	 * @param gui the gui to set
	 */
	public static void setGui(GUIModel gui) {
		GUIMainController.gui = gui;
	}



	/**
	 * @return the thing
	 */
	public Action getCommandEntered() {
		return CommandEntered;
	}


	/**
	 * @param thing the thing to set
	 */
	public void setCommandEntered(Action thing) {
			CommandEntered = thing;
	}



	public void buildGUI() {

	}

}
