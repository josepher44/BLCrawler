package blcrawler;

import java.awt.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.awt.event.*;

import javax.swing.*;

import blcrawler.controller.GUIMainController;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.database.PartDatabase;
import blcrawler.view.imsgui.IMSGUIView;

public class BLCrawler extends Application {

	private static GUIMainController guiMainController;
	private static ConsoleGUIModel guiModel;
	private static JLabel emptyLabel;
	private static PartDatabase partDatabase;
	private static IMSGUIView imsgui;

	public static void main(String[] args) {

		guiModel = new ConsoleGUIModel("gui");
		imsgui = new IMSGUIView();
		guiModel.setImsgui(imsgui);

		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
		//imports extended inventory management system gui info from view class
		//imsgui = new IMSGUIView();
        primaryStage.setTitle("BLCrawl Inventory Management System");
        primaryStage.setScene(imsgui.getScene());
        primaryStage.show();

        //partDatabase = new PartDatabase();

	}

}
