package blcrawler.view.imsgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.event.*;

import blcrawler.model.bsx.BSXImporter;
import blcrawler.model.bsx.Inventory;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import blcrawler.model.bsx.inventorylot.InventoryLot;

public class IMSGUIView
{
	Scene scene;
	TableView<InventoryLocation> inventoryView;
	ObservableList<InventoryLocation> lots;
	BSXImporter importer;
	public static Inventory currentInventory;
	public IMSGUIView() {
		currentInventory = new Inventory();
		Start();
	}

	public Scene getScene()
	{
		return scene;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
	}

	public void Start()
	{
        Button btn = new Button();
        btn.setText("Toolbar Placeholder'");
        btn.setOnAction(e -> System.out.println("Hello World!"));
        importer = new BSXImporter("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/OtherBSX/Redraweringmaster.bsx");
        lots = importer.getInventoryLocationList();
        currentInventory.setDivisionTable(importer.getDrawerDivisionTable());
        currentInventory.setDivisionList(importer.getDrawerDivisionList());


        //Set up Columns
        TableColumn<InventoryLocation,Short> IndexColumn = new TableColumn<>("Index");
        IndexColumn.setMinWidth(50);
        IndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<InventoryLocation,String> ItemIDColumn = new TableColumn<>("ItemID");
        ItemIDColumn.setMinWidth(50);
        ItemIDColumn.setCellValueFactory(new PropertyValueFactory<>("ItemID"));

        TableColumn<InventoryLocation,String> NameColumn = new TableColumn<>("Name");
        NameColumn.setMinWidth(100);
       	NameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));

        TableColumn<InventoryLocation,Short> CabinetColumn = new TableColumn<>("Cabinet");
        CabinetColumn.setMinWidth(50);
        CabinetColumn.setCellValueFactory(new PropertyValueFactory<>("Cabinet"));

        TableColumn<InventoryLocation,String> CategoryNameColumn = new TableColumn<>("CategoryName");
        CategoryNameColumn.setMinWidth(150);
        CategoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));

        TableColumn<InventoryLocation,Short> CategoryIDColumn = new TableColumn<>("CategoryID");
        CategoryIDColumn.setMinWidth(50);
        CategoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryID"));

        TableColumn<InventoryLocation,Boolean> MultiColumn = new TableColumn<>("Multi?");
        MultiColumn.setMinWidth(50);
        MultiColumn.setCellValueFactory(new PropertyValueFactory<>("multiLocated"));

        TableColumn<InventoryLocation,String> RawRemarksColumn = new TableColumn<>("Raw Remarks");
        RawRemarksColumn.setMinWidth(50);
        RawRemarksColumn.setCellValueFactory(new PropertyValueFactory<>("Remarks"));

        inventoryView = new TableView<>();
        inventoryView.setItems(lots);
        inventoryView.getColumns().add(IndexColumn);
        inventoryView.getColumns().add(ItemIDColumn);
        inventoryView.getColumns().add(NameColumn);
        inventoryView.getColumns().add(CabinetColumn);
        inventoryView.getColumns().add(CategoryIDColumn);
        inventoryView.getColumns().add(CategoryNameColumn);
        inventoryView.getColumns().add(MultiColumn);
        inventoryView.getColumns().add(RawRemarksColumn);


        Button ShowCompartments = new Button();
        ShowCompartments.setText("Show Compartments");
        ShowCompartments.setOnAction(e -> invertDisplay());

        VBox root = new VBox();
        BorderPane mainScene = new BorderPane();
        mainScene.setTop(root);

        VBox leftBar = new VBox();
        mainScene.setLeft(leftBar);
        leftBar.getChildren().add(ShowCompartments);

        VBox table = new VBox();
        mainScene.setCenter(table);
        table.getChildren().add(inventoryView);





        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem aye = new MenuItem("aye                                   Ctrl-P");
        MenuItem bae = new MenuItem("bae");

        aye.setOnAction(e->AddPart.display());

        FadeTransition ft = new FadeTransition(Duration.millis(3000), menuFile.getGraphic());

        ft.setFromValue(0.05);
        ft.setToValue(1.0);
        ft.setCycleCount(50);
        ft.setAutoReverse(true);



        menuFile.getItems().addAll(aye, bae);









        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        MenuItem see = new MenuItem("see");

        menuEdit.getItems().addAll(see);

        // --- Menu View
        Menu menuView = new Menu("View");

        Menu menuConfig = new Menu("Configuration");



        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuConfig);

        root.getChildren().add(menuBar);
        root.getChildren().add(btn);

        scene = new Scene(mainScene, 1024, 768);



        //root.addAll(menuBar);

        currentInventory.mapLocationsToDivisions();
        //currentInventory.identifyEmptyUndividedDrawers();

	}

	private void invertDisplay()
	{
		// TODO Auto-generated method stub
		
	}

	public static Inventory getCurrentInventory()
	{
		return currentInventory;
	}

	public static void setCurrentInventory(Inventory currentInventory)
	{
		IMSGUIView.currentInventory = currentInventory;
	}

	public void addPart()
	{
		
	}






}
