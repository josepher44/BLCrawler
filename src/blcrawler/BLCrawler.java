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
	
	//private static JLabel emptyLabel;
	//private static PartDatabase partDatabase;
	private static IMSGUIView imsgui;

	public static void main(String[] args) {

		new ConsoleGUIModel("gui");
		imsgui = new IMSGUIView();
		ConsoleGUIModel.setImsgui(imsgui);

		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
		//imports extended inventory management system gui info from view class
		//imsgui = new IMSGUIView();
        primaryStage.setTitle("BLCrawl Inventory Management System");
        primaryStage.setScene(imsgui.getScene());
        ConsoleGUIModel.getImsgui().setWindow(primaryStage);
        primaryStage.show();
        primaryStage.setMaximized(true);

        //partDatabase = new PartDatabase();

	}

}
