package blcrawler.view.imsgui;

import java.util.Collections;

import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.bsx.Inventory;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AssessMolds
{
	public static Inventory inventory;
	
	
	public static void display()
	{
		inventory = IMSGUIView.getCurrentInventory();
		Stage window = new Stage();
		
		window.setTitle("Assess Molds");
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());
		
		VBox mainLayout = new VBox();
		
		Text moldcount = new Text();
		
		Scene scene = new Scene(mainLayout);
		window.setScene(scene);
		window.show();
	}
}
