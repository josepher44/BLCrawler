package blcrawler.view.imsgui;


import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.beans.property.Property;
import javafx.scene.control.Button;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.ShortStringConverter;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.BSXImporter;
import blcrawler.model.bsx.Inventory;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import blcrawler.model.bsx.inventorylot.InventoryLot;
import blcrawler.view.imsgui.javafx.*;

public class IMSGUIView
{
	Scene scene;
	Stage window;
	TableView<InventoryLocation> inventoryView;
	ObservableList<InventoryLocation> lots;
	BSXImporter importer;
	public static DataFormat dataFormat = new DataFormat("mydata");
    private Timeline scrolltimeline = new Timeline();
    private double scrollDirection = 0;
    public int rootIndex=0;
    public int minIndex=0;
    public int maxIndex=0;
    public int minSelectedIndex=0;
    public int maxSelectedIndex=0;
	public static Inventory currentInventory;
	public IMSGUIView() {
		currentInventory = new Inventory();
		Start();
	}

    private void setupScrolling() {
        scrolltimeline.setCycleCount(Timeline.INDEFINITE);
        scrolltimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), "Scoll", (ActionEvent) -> { dragScroll();}));
        inventoryView.setOnDragExited(event -> {
            if (event.getY() > 0) {
                scrollDirection = 1.0 / inventoryView.getItems().size();
            }
            else {
                scrollDirection = -1.0 / inventoryView.getItems().size();
            }
            scrolltimeline.play();
        });
        inventoryView.setOnDragEntered(event -> {
            scrolltimeline.stop();
        });
        inventoryView.setOnDragDone(event -> {
            scrolltimeline.stop();
        });
    }

    private void dragScroll() {
        ScrollBar sb = getVerticalScrollbar();
        if (sb != null) {
            double newValue = sb.getValue() + scrollDirection;
            newValue = Math.min(newValue, 1.0);
            newValue = Math.max(newValue, 0.0);
            if (sb.getValue()<newValue)
            {
                if (minSelectedIndex<rootIndex)
                {
                	minSelectedIndex++;
                	clearDownwards();
                }
                else
                {
                	maxSelectedIndex++;
                	selectDownwards();
                }
            }
            else
            {
                if (minSelectedIndex<rootIndex)
                {
                	minSelectedIndex--;
                	selectUpwards();
                }
                else
                {
                	maxSelectedIndex--;
                	clearUpwards();
                }
            }
            sb.setValue(newValue);

        }
    }

    private ScrollBar getVerticalScrollbar() {
        ScrollBar result = null;
        for (Node n : inventoryView.lookupAll(".scroll-bar")) {
            if (n instanceof ScrollBar) {
                ScrollBar bar = (ScrollBar) n;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    result = bar;
                }
            }
        }
        return result;
    }

	public Scene getScene()
	{
		return scene;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
	}

	public void setWindow(Stage w)
	{
		this.window = w;
	}

	public void importBSX(File file)
	{
		importer = new BSXImporter(file.getAbsolutePath());
        lots = importer.getInventoryLocationList();
        currentInventory.setDivisionTable(importer.getDrawerDivisionTable());
        currentInventory.setDivisionList(importer.getDrawerDivisionList());


        currentInventory.mapLocationsToDivisions();

        inventoryView.setItems(lots);
	}

	public void Start()
	{

		ObservableList<TablePosition> selectedCells = FXCollections.observableArrayList();
		Button btn = new Button();
        btn.setText("Toolbar Placeholder'");
        btn.setOnAction(e -> System.out.println("Hello World!"));




        //Set up Columns

        TableColumn<InventoryLocation,ImageView> ImageColumn = new TableColumn<>("Image");
        ImageColumn.setMinWidth(50);
        ImageColumn.setCellValueFactory(new PropertyValueFactory<>("imageview"));

        TableColumn<InventoryLocation,Short> IndexColumn = new TableColumn<>("Index");
        IndexColumn.setMinWidth(50);
        IndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));

        TableColumn<InventoryLocation,Character> ConditionColumn = new TableColumn<>("Condition");
        ConditionColumn.setMinWidth(20);
        ConditionColumn.setPrefWidth(20);
        ConditionColumn.setCellValueFactory(new PropertyValueFactory<>("Condition"));

        TableColumn<InventoryLocation,String> ItemIDColumn = new TableColumn<>("ItemID");
        ItemIDColumn.setMinWidth(50);
        ItemIDColumn.setCellValueFactory(new PropertyValueFactory<>("ItemID"));

        TableColumn<InventoryLocation,String> NameColumn = new TableColumn<>("Name");
        NameColumn.setMinWidth(100);
       	NameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
       	//NameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<InventoryLocation,String> ColorColumn = new TableColumn<>("Color");
        ColorColumn.setMinWidth(50);
        ColorColumn.setCellValueFactory(new PropertyValueFactory<>("ColorName"));

        TableColumn<InventoryLocation,Integer> QtyColumn = new TableColumn<>("Qty");
        QtyColumn.setMinWidth(30);
        QtyColumn.setCellValueFactory(new PropertyValueFactory<>("Qty"));

        TableColumn<InventoryLocation,Double> PriceColumn = column("Price", item -> item.priceProperty().asObject());
        PriceColumn.setMinWidth(50);
        //PriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        TableColumn<InventoryLocation,Short> CabinetColumn = new TableColumn<>("Cabinet");
        CabinetColumn.setMinWidth(50);
        CabinetColumn.setCellValueFactory(new PropertyValueFactory<>("Cabinet"));


        //CabinetColumn.setCellFactory(col -> new EditingShortCell<>());

        CabinetColumn.setCellFactory(TextFieldTableCellShort.forTableColumn(new ShortStringConverter()));
        CabinetColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryLocation, Short>>()
        {
            @Override public void handle(TableColumn.CellEditEvent<InventoryLocation, Short> t) {
                ((InventoryLocation)t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setCabinet(t.getNewValue());
            }
        });





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
        inventoryView.setPrefHeight(600);
        inventoryView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        inventoryView.setItems(lots);
        inventoryView.getColumns().add(ImageColumn);
       // inventoryView.getColumns().add(IndexColumn);
        inventoryView.getColumns().add(ItemIDColumn);
        inventoryView.getColumns().add(NameColumn);
        inventoryView.getColumns().add(ConditionColumn);
        inventoryView.getColumns().add(ColorColumn);
        inventoryView.getColumns().add(QtyColumn);
        inventoryView.getColumns().add(PriceColumn);
        inventoryView.getColumns().add(CabinetColumn);
        inventoryView.getColumns().add(CategoryIDColumn);
        inventoryView.getColumns().add(CategoryNameColumn);
        inventoryView.getColumns().add(MultiColumn);
        inventoryView.getColumns().add(RawRemarksColumn);

        inventoryView.setRowFactory(new Callback<TableView<InventoryLocation>, TableRow<InventoryLocation>>()
        {


        	@Override
    	    public TableRow<InventoryLocation> call(TableView<InventoryLocation> p) {
    	        final TableRow<InventoryLocation> row = new TableRow<InventoryLocation>();
    	        row.setOnDragEntered(new EventHandler<DragEvent>() {
    	            @Override
    	            public void handle(DragEvent t) {
    	                if (row.getIndex()<rootIndex)
    	                {
    	                	minSelectedIndex = row.getIndex();
    	                	selectUpwards();
    	                }
    	                else
    	                {
    	                	maxSelectedIndex = row.getIndex();
    	                	selectDownwards();
    	                }
    	                System.out.println("absolute min: "+minIndex);
    	                System.out.println("selected min: "+minSelectedIndex);
    	                System.out.println("absolute max: "+maxIndex);
    	                System.out.println("selected max: "+maxSelectedIndex);
    	            	//setSelection(row);
    	            }
    	        });
    	        row.setOnDragExited(new EventHandler<DragEvent>() {
    	        	@Override
    	            public void handle(DragEvent t) {
    	                if (row.getIndex()<rootIndex)
    	                {
    	                	clearDownwards();
    	                }
    	                else
    	                {
    	                	clearUpwards();

    	                }

    	        	}
    	        });
    	        row.setOnDragDetected(new EventHandler<MouseEvent>() {
    	            @Override
    	            public void handle(MouseEvent t)
    				{
    	                rootIndex = row.getIndex();
    	                minIndex = rootIndex;
    	                minSelectedIndex = rootIndex;
    	                maxIndex = rootIndex;
    	                maxSelectedIndex = rootIndex;
    	            	Dragboard db = row.getTableView().startDragAndDrop(TransferMode.COPY);
    	                ClipboardContent content = new ClipboardContent();
						content.put(dataFormat, "XData");
    	                db.setContent(content);
    	                setSelection(row);
    	                t.consume();
    	            }
    	        });
    	        row.setOnMouseReleased(new EventHandler<MouseEvent>() {
    	        	@Override
     	            public void handle(MouseEvent t)
     				{
	    	        	rootIndex=0;
	    	    	    minIndex=0;
	    	    	    maxIndex=0;
	    	    	    minSelectedIndex=0;
	    	    	    maxSelectedIndex=0;
     				}
    	        });
    	        return row;
    	    }
    	});







        PriceColumn.setCellFactory(col -> new PriceTableCell<>());

        inventoryView.setEditable(true);

        setupScrolling();



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
        MenuItem newMenu = new MenuItem("new								   Ctrl-N");
        MenuItem openMenu = new MenuItem("open								   Ctrl-O");
        MenuItem aye = new MenuItem("aye                                   Ctrl-P");
        MenuItem bae = new MenuItem("bae");

        aye.setOnAction(e->AddPart.display());
        newMenu.setOnAction(e->newFile());
        openMenu.setOnAction(e->openFile());

        FadeTransition ft = new FadeTransition(Duration.millis(3000), menuFile.getGraphic());

        ft.setFromValue(0.05);
        ft.setToValue(1.0);
        ft.setCycleCount(50);
        ft.setAutoReverse(true);



        menuFile.getItems().addAll(newMenu, openMenu, aye, bae);









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

        scene = new Scene(mainScene, 1800, 1100);


        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
	            public void handle(MouseEvent t)
				{
	        	rootIndex=0;
	    	    minIndex=0;
	    	    maxIndex=0;
	    	    minSelectedIndex=0;
	    	    maxSelectedIndex=0;
				}
        });


        //root.addAll(menuBar);

        //currentInventory.identifyEmptyUndividedDrawers();

	}

	private void selectDownwards()
	{
    	if (maxSelectedIndex > maxIndex)
    	{
    		maxIndex = maxSelectedIndex;
    	}
    	for(int i = rootIndex; i<=maxSelectedIndex; i++)
    	{
    		inventoryView.getSelectionModel().select(i);
    	}
	}

	private void selectUpwards()
	{
    	if (minSelectedIndex < minIndex)
    	{
    		minIndex = minSelectedIndex;
    	}
    	for (int i = minSelectedIndex; i<=rootIndex; i++)
    	{
    		inventoryView.getSelectionModel().select(i);
    	}
	}

	private void clearDownwards()
	{
    	for (int i = minIndex; i<=minSelectedIndex; i++)
    	{
    		inventoryView.getSelectionModel().clearSelection(i);
    	}
	}

	private void clearUpwards()
	{
    	for(int i = maxSelectedIndex; i<=maxIndex; i++)
    	{
    		inventoryView.getSelectionModel().clearSelection(i);
    	}
	}

	private void setSelection(IndexedCell cell)
	{
	    if (cell.isSelected())
	    {
	        System.out.println("False enter");
	        inventoryView.getSelectionModel().clearSelection(cell.getIndex());
	    }
	    else
	    {
	        System.out.println("Select");
	        inventoryView.getSelectionModel().select(cell.getIndex());
	    }
	}

	public void newFile()
	{

	}

	public void openFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File("C:/Users/Joseph/Downloads/bricksync-win64-169/bricksync-win64/data/OtherBSX"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSX", "*.bsx"));
		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
            openFile(file);
        }
	}

	public void openFile(File file)
	{
        importBSX(file);
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

	public int getHighestIndex()
	{
		int index = 0;
		for (InventoryLocation item : lots)
		{
			if(item.getIndex()>index)
			{
				index = item.getIndex();
			}
		}
		return index;
	}

	public void addPart()
	{
		InventoryLot lot = new InventoryLot();

		Boolean error = false;

		CatalogPart part=null;
		String colorString=null;
		String locationString = "";
		String comments=null;
		String price = "";
		String quantity=null;
		char condition='U';

		part = AddPart.getPart();
		colorString = AddPart.getColor();
		comments = AddPart.getComments();
		quantity = AddPart.getQuantityString();
		condition = AddPart.getCondition();

		if(part==null||colorString==null)
		{
			error = true;
			System.out.println("Did not select a part or a color");
		}
		else
		{
			try
			{
				price = AddPart.getPriceString();
				if (!price.equals(""))
				{
					lot.setPrice(Double.valueOf(price));
				}
				else
				{
					lot.setPrice(0.00);
				}
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("Illegal argument exception: Invalid Price");
				error=true;
			}
			try
			{
				locationString = AddPart.getLocationString();
				if(locationString.length()==9)
				{
					lot.setSectionID(Short.valueOf(locationString.substring(7, 9)));
					lot.setCabinet(Short.valueOf(locationString.substring(0,3)));
					lot.setDrawer(Short.valueOf(locationString.substring(4,6)));
				}
				else if(locationString.length()==6)
				{
					lot.setSectionID((short) 1);
					lot.setCabinet(Short.valueOf(locationString.substring(0,3)));
					lot.setDrawer(Short.valueOf(locationString.substring(4,6)));
				}
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("Illegal argument exception: Invalid Location");
				error=true;
			}
		}


		try
		{
			lot.setCategoryID((short) part.getCategoryID());
			lot.setCategoryName(part.getCategoryName());
			lot.setColorID((short) ConsoleGUIModel.getDatabase().getColormap().idFromName(colorString));
			lot.setColorName(colorString);
			lot.setComments(comments);
			lot.setCondition(condition);
			lot.setIndex(getHighestIndex()+1);
			lot.setItemID(part.getPartNumber());
			lot.setItemName(part.getName());
			lot.setItemType("Part");
			lot.setItemTypeID('P');
			lot.setLotID(0);
			lot.setMultiLocated(false);
			lot.setQty(Integer.valueOf(quantity));
			lot.setRemarks(locationString);
			lot.setStatus("I");
			lot.setWeight(part.getWeight());
		}
		catch (Exception e)
		{
		}

		if(!error)
		{
			InventoryLocation location = new InventoryLocation(lot);
			lots.add(location);
		}


	}



    private static <S,T> TableColumn<S,T> column(String title, Function<S, Property<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
    }



}
