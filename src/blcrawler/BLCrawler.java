package blcrawler;


import javafx.application.Application;
import javafx.stage.Stage;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.view.imsgui.IMSGUIView;

/**
 * {@code BLCrawler} is the top level class of the BLCrawler application. 
 * It should only be instantiated once. It extends a JavaFX application class, 
 * and launches both the console interface and the IMSGUI system.
 * 
 * @author Joe Gallagher
 *
 */
public class BLCrawler extends Application {
	
	/*
	 * Fields. Note that the console GUI is static and not named, declared
	 * first line of main
	 * >imsgui: gui for inventory management system. Subservient to console
	 */
	private static IMSGUIView imsgui;

	/**
	 * Main method, from which everything beings. Creates single instances of 
	 * ConsoleGUIModel and IMSGUIView, and makes the imsguiview a child of
	 * ConsoleGUIModel
	 * @param args arguments passed at launch. Always null
	 */
	public static void main(String[] args) {

		new ConsoleGUIModel("gui");
		//TODO: Refactor such that this is pointed to IMSGUI model
		imsgui = new IMSGUIView();
		ConsoleGUIModel.setImsgui(imsgui);

		launch(args);
	}
	
	
	/*
	 * Override of Application's Start method, called at program launch. Sets 
	 * the basic parameters of the IMSGUI stage, and ties the imsgui view to 
	 * this stage.
	 */
	@Override
    public void start(Stage primaryStage) {
		//imports extended inventory management system gui info from view class
        primaryStage.setTitle("BLCrawl Inventory Management System");
        primaryStage.setScene(imsgui.getScene());
        ConsoleGUIModel.getImsgui().setWindow(primaryStage);
        primaryStage.show();
        primaryStage.setMaximized(true);
	}

}
