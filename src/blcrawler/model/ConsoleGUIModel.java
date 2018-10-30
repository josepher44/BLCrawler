package blcrawler.model;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

import blcrawler.api.APIModel;
import blcrawler.commands.BuildPartsFromHTML;
import blcrawler.commands.ReadPartsFromXML;
import blcrawler.controller.ConsoleController;
import blcrawler.controller.DatabaseController;
import blcrawler.controller.GUIMainController;
import blcrawler.model.bsx.BSXImporter;
import blcrawler.model.page.PageManager;
import blcrawler.model.queue.MasterClock;
import blcrawler.model.queue.TaskTimer;
import blcrawler.model.selenium.SeleniumDistributor;
import blcrawler.model.selenium.SeleniumModel;
import blcrawler.view.ConsoleGUIView;
import blcrawler.view.imsgui.IMSGUIView;

public class ConsoleGUIModel {

	//private static GUIModel instance = new GUIModel("gui");
	private static GUIMainController guiController;
	private static ConsoleGUIView guiView;
	private static ConsoleController consoleController;
	private static TaskTimer taskTimer;
	private static PageManager pageManager;
	private static SeleniumModel Subselenium;
	private static SeleniumDistributor selenium;
	private static APIModel apitest;
	private static DatabaseController database;
	private static IMSGUIView imsgui;
	//private static BSXImporter bsxImporter;
	private static MasterClock masterClock;


	/**
	 * @return the consoleController
	 */
	public static ConsoleController getConsoleController() {
		return consoleController;
	}
	public static SeleniumDistributor getSelenium()
	{
		return selenium;
	}
	public static void setSelenium(SeleniumDistributor selenium)
	{
		ConsoleGUIModel.selenium = selenium;
	}
	/**
	 * @return the guiController
	 */
	public static GUIMainController getGuiController() {
		return guiController;
	}

	/**
	 * @return the guiView
	 */
	public static ConsoleGUIView getGuiView() {
		return guiView;
	}






	public static IMSGUIView getImsgui()
	{
		return imsgui;
	}
	public static void setImsgui(IMSGUIView imsgui)
	{
		ConsoleGUIModel.imsgui = imsgui;
	}
	public static PageManager getPageManager() {
		return pageManager;
	}

	public static SeleniumModel getSeleniumModel() {
		return Subselenium;
	}






	/**
	 * @return the taskTimer
	 */
	public static TaskTimer getTaskTimer() {
		return taskTimer;
	}

	/**
	 * @param taskTimer the taskTimer to set
	 */
	public static void setTaskTimer(TaskTimer taskTimer) {
		ConsoleGUIModel.taskTimer = taskTimer;
	}






	private String headerLabel;






	private String statusLabel;






	public ConsoleGUIModel(String name) {
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
		guiView = new ConsoleGUIView(guiController, this);
		taskTimer = new TaskTimer();
		pageManager = new PageManager();
		masterClock = new MasterClock();
		selenium = new SeleniumDistributor();
		database = new DatabaseController();
		//bsxImporter = new BSXImporter("C:/Users/Owner/Documents/BLCrawler/Redraweringmaster.bsx");
		//Subselenium = new SeleniumModel("9152");
		//apitest= new APIModel();



		ReadPartsFromXML command = new ReadPartsFromXML();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);






	}

	public static DatabaseController getDatabase()
	{
		return database;
	}
	public static void setDatabase(DatabaseController database)
	{
		ConsoleGUIModel.database = database;
	}
	/**
	 * @return the headerLabel
	 */
	public String getHeaderLabel() {
		return headerLabel;
	}

	/**
	 * @return the statusLabel
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param consoleController the consoleController to set
	 */
	public void setConsoleController(ConsoleController consoleController) {
		this.consoleController = consoleController;
	}

	/**
	 * @param guiController the guiController to set
	 */
	public void setGuiController(GUIMainController guiController) {
		this.guiController = guiController;
	}

	/**
	 * @param guiView the guiView to set
	 */
	public void setGuiView(ConsoleGUIView guiView) {
		this.guiView = guiView;
	}

	/**
	 * @param headerLabel the headerLabel to set
	 */
	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

}
