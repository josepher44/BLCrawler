package blcrawler.controller;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import blcrawler.model.GUIModel;

public class GUIMainController {
	
	private static GUIModel gui;
	Action Thing;
	
	public GUIMainController(GUIModel gui) {
		this.gui = gui;
		
		Thing = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello World");
				
				}
		  };
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
	public Action getThing() {
		return Thing;
	}



	/**
	 * @param thing the thing to set
	 */
	public void setThing(Action thing) {
		Thing = thing;
	}



	public void buildGUI() {

	}

}
