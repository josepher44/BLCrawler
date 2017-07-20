package blcrawler.view.imsgui;

import java.util.Collections;
import java.util.IllegalFormatException;

import org.apache.commons.lang3.StringUtils;

import blcrawler.view.css.*;

import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddPart
{
	static ListView<String> categoryList;
	static TableView<CatalogPart> partTable;
	static String categoryToDisplay;
	static ObservableList<CatalogPart> partSubList;
	static ListView<String> colorList;
	static ObservableList<String> colors;
	static ChoiceBox<String> itemTypes;
	static Label filterLabel;
	static TextField filter;
	static ChoiceBox<String> colorDisplayMode;
	static TextField quantity;
	static TextField price;
	static TextField comments;
	static TextField location;
	static Label quantityLabel;
	static Label priceLabel;
	static Label commentsLabel;
	static Label locationLabel;
	static RadioButton conditionNew;
	static RadioButton conditionUsed;
	static ToggleGroup condition;


	public static void display()
	{
		Stage window = new Stage();

		window.setTitle("Add part");

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());

		Button addButton = new Button("Add Part");
		addButton.setOnAction(e -> ConsoleGUIModel.getImsgui().addPart());


		itemTypes = new ChoiceBox<>();
		itemTypes.getItems().add("Book");
		itemTypes.getItems().add("Catalog");
		itemTypes.getItems().add("Gear");
		itemTypes.getItems().add("Instruction");
		itemTypes.getItems().add("Minifig");
		itemTypes.getItems().add("Original Box");
		itemTypes.getItems().add("Part");
		itemTypes.getItems().add("Set");
		itemTypes.getItems().add("Unsorted Lot");

		itemTypes.setValue("Part");
		itemTypes.setMinWidth(200);

		filterLabel = new Label("filter: ");
		filterLabel.setPadding(new Insets(0,20,0,10));

		filter = new TextField();
		filter.setMinWidth(500);

		colorDisplayMode = new ChoiceBox<>();
		colorDisplayMode.getItems().add("Show All");
		colorDisplayMode.getItems().add("BL For Sale");
		colorDisplayMode.getItems().add("BL Menu");
		colorDisplayMode.getItems().add("BL Known");
		colorDisplayMode.getItems().add("BL Price Guide");
		colorDisplayMode.getItems().add("BL Wanted");

		colorDisplayMode.setValue("BL Menu");





		categoryList = new ListView<>();
		categoryList.getItems().add("All Items");
		categoryList.getItems().addAll(ConsoleGUIModel.getDatabase().getPartCategories());
		categoryList.setPrefHeight(500);
		partSubList = FXCollections.observableArrayList();
		colors = FXCollections.observableArrayList();

		//Selection listening code
		categoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//Set action on selection change here
				categoryToDisplay = newValue;
				partSubList.clear();
				for (CatalogPart part : ConsoleGUIModel.getDatabase().getCatalogParts())
				{
					if (newValue.equals("All Items") || part.getCategoryName().equals(newValue))
					partSubList.add(part);
				}
				partTable.scrollTo(0);
		        System.out.println("ListView selection changed from oldValue = "
		                + oldValue + " to newValue = " + newValue);
		    }
		});

        TableColumn<CatalogPart,String> Number = new TableColumn<>("Part ID");
        Number.setMinWidth(50);
        Number.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

        TableColumn<CatalogPart,String> Name = new TableColumn<>("Name");
        Name.setMinWidth(550);
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));


		partTable = new TableView<>();
		partTable.setItems(partSubList);
		partTable.getColumns().add(Number);
		partTable.getColumns().add(Name);
		partTable.setPrefHeight(500);

		partTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CatalogPart>() {
			@Override
		    public void changed(ObservableValue<? extends CatalogPart> observable, CatalogPart oldValue, CatalogPart newValue) {
				//Set action on selection change here
				colors.clear();
				colorList.getItems().clear();
				if (oldValue == null)
				{
					System.out.println("CatalogPart selection changed from oldValue = "
			                + "null" + " to newValue = " + newValue.getName());
					for (String color : newValue.getKnownColorsBLMenu())
					{
						colors.add(color);

					}
					Collections.sort(colors);
					colorList.getItems().addAll(colors);
				}
				else if (newValue == null)
				{
					System.out.println("CatalogPart selection changed from oldValue = "
			                + oldValue.getName() + " to newValue = " + "null");
				}
				else
				{
					System.out.println("CatalogPart selection changed from oldValue = "
			                + oldValue.getName() + " to newValue = " + newValue.getName());
					for (String color : newValue.getKnownColorsBLMenu())
					{
						colors.add(color);

					}
					Collections.sort(colors);
					colorList.getItems().addAll(colors);
				}



		    }
		});

		colorList = new ListView<>();
		//colorList.getItems().addAll(colors);
		colorList.setPrefHeight(500);

		quantityLabel = new Label("Quantity: ");
		quantityLabel.setPadding(new Insets(0,20,0,10));

		quantity = new TextField();
		quantity.setMinWidth(500);
		// force the field to be numeric only
	    quantity.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                quantity.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });

		priceLabel = new Label("Price: ");
		priceLabel.setPadding(new Insets(0,20,0,10));

		price = new TextField();
		price.setMinWidth(500);
		// force the field to be numeric only
	    price.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("(\\d*\\.?\\d*)"))
	            {
	                price.setText(oldValue);
	            }
	        }
	    });

	    commentsLabel = new Label("Comments: ");
		commentsLabel.setPadding(new Insets(0,20,0,10));

		comments = new TextField();
		comments.setMinWidth(500);

	    locationLabel = new Label("Location: ");
		locationLabel.setPadding(new Insets(0,20,0,10));

		location = new TextField();
		location.setMinWidth(500);

		condition = new ToggleGroup();

		conditionNew = new RadioButton("New");
		conditionNew.setToggleGroup(condition);
		conditionNew.setPadding(new Insets(0,20,0,20));

		conditionUsed = new RadioButton("Used");
		conditionUsed.setToggleGroup(condition);
		conditionUsed.setSelected(true);
		conditionUsed.setPadding(new Insets(0,20,0,20));


		BorderPane mainLayout = new BorderPane();
		GridPane dataBoxes = new GridPane();
		HBox top = new HBox();
		HBox bottom = new HBox();
		VBox left = new VBox();
		VBox middle = new VBox();
		VBox right = new VBox();
		mainLayout.setPadding(new Insets(20,20,20,20));
		VBox bottomMaster = new VBox();
		HBox bottomMain = new HBox();
		HBox bottomButtons = new HBox();

		dataBoxes.add(quantityLabel, 0, 0);
		dataBoxes.add(quantity, 1, 0);
		dataBoxes.add(conditionNew, 2, 0);
		dataBoxes.add(conditionUsed, 3, 0);
		dataBoxes.add(priceLabel, 0, 1);
		dataBoxes.add(price, 1, 1);
		dataBoxes.add(commentsLabel, 0, 2);
		dataBoxes.add(comments, 1, 2);
		dataBoxes.add(locationLabel, 0, 3);
		dataBoxes.add(location, 1, 3);

		dataBoxes.setPadding(new Insets(5,5,5,5));
		dataBoxes.setVgap(5);

		bottomMain.getChildren().add(dataBoxes);
		bottomMaster.getChildren().add(bottomMain);
		bottomButtons.getChildren().add(addButton);
		bottomButtons.getChildren().add(closeButton);
		bottomMaster.getChildren().add(bottomButtons);




		bottom.getChildren().add(bottomMaster);
		left.getChildren().add(categoryList);
		middle.getChildren().add(partTable);
		right.getChildren().add(colorList);
		top.getChildren().add(itemTypes);
		top.getChildren().add(filterLabel);
		top.getChildren().add(filter);
		top.getChildren().add(colorDisplayMode);
		mainLayout.setTop(top);
		mainLayout.setBottom(bottom);
		mainLayout.setLeft(left);
		mainLayout.setCenter(middle);
		mainLayout.setRight(right);

		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().add("test.css");
		window.setScene(scene);
		window.show();
	}

	public static CatalogPart getPart()
	{
		CatalogPart part = partTable.getSelectionModel().getSelectedItem();

		return part;
	}

	public static String getLocationString() throws IllegalArgumentException
	{

		String s = location.getText();

		if(!s.matches("([0-9]){3}-([0-9]){2}-([0-9]){2}"))
		{
			throw new IllegalArgumentException();
		}

		return s;

	}

	public static String getQuantityString()
	{

		String s = quantity.getText();

		return s;

	}

	public static String getPriceString() throws IllegalArgumentException
	{
		String s = price.getText();
		if(!s.matches("(([0-9])+)|(([0-9])*\\.([0-9]){1,3})")&&!s.equals(""))
		{
			throw new IllegalArgumentException();
		}
		return s;
	}

	public static String getColor()
	{
		String s = colorList.getSelectionModel().getSelectedItem();
		return s;
	}

	public static String getComments()
	{
		String s = comments.getText();
		return s;
	}

	public static char getCondition()
	{
		if (conditionUsed.isSelected())
		{
			return 'U';
		}
		else
		{
			return 'N';
		}
	}
}
