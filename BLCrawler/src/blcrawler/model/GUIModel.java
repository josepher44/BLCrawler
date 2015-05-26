package blcrawler.model;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

import blcrawler.controller.ConsoleController;
import blcrawler.controller.GUIMainController;
import blcrawler.model.page.PageManager;
import blcrawler.model.queue.TaskTimer;
import blcrawler.model.selenium.SeleniumModel;
import blcrawler.view.GUIView;

public class GUIModel {
	
	//private static GUIModel instance = new GUIModel("gui");
	private static GUIMainController guiController;
	private static GUIView guiView;
	private static ConsoleController consoleController;
	private static TaskTimer taskTimer;
	private static PageManager pageManager;
	private static SeleniumModel selenium;
	
	private String headerLabel;
	private String statusLabel;
	
	public GUIModel(String name) {
		try
		{
			consoleController = new ConsoleController();
		}
		catch (Exception e)
		{
			System.err.println("Caught CommandErr exception: " + e.getMessage());
		}
		
		headerLabel = "Test for region that's having some issues displaying text, it looks all funny";
		statusLabel = "status";
		guiController = new GUIMainController(this);
		guiView = new GUIView(guiController, this);
		taskTimer = new TaskTimer();
		pageManager = new PageManager();
		selenium = new SeleniumModel();


		
		 
	      
	      
	}

	


	
	
	/**
	 * @return the taskTimer
	 */
	public static TaskTimer getTaskTimer() {
		return taskTimer;
	}
	
	public static PageManager getPageManager() {
		return pageManager;
	}






	/**
	 * @param taskTimer the taskTimer to set
	 */
	public static void setTaskTimer(TaskTimer taskTimer) {
		GUIModel.taskTimer = taskTimer;
	}
	
	public static SeleniumModel getSeleniumModel() {
		return selenium;
	}






	/**
	 * @return the consoleController
	 */
	public static ConsoleController getConsoleController() {
		return consoleController;
	}






	/**
	 * @param consoleController the consoleController to set
	 */
	public void setConsoleController(ConsoleController consoleController) {
		this.consoleController = consoleController;
	}






	/**
	 * @return the guiController
	 */
	public static GUIMainController getGuiController() {
		return guiController;
	}

	/**
	 * @param guiController the guiController to set
	 */
	public void setGuiController(GUIMainController guiController) {
		this.guiController = guiController;
	}

	/**
	 * @return the guiView
	 */
	public static GUIView getGuiView() {
		return guiView;
	}

	/**
	 * @param guiView the guiView to set
	 */
	public void setGuiView(GUIView guiView) {
		this.guiView = guiView;
	}

	/**
	 * @return the headerLabel
	 */
	public String getHeaderLabel() {
		return headerLabel;
	}

	/**
	 * @param headerLabel the headerLabel to set
	 */
	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	/**
	 * @return the statusLabel
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

}
