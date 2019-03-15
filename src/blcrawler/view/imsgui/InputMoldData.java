package blcrawler.view.imsgui;

import java.util.Collections;

import blcrawler.model.CatalogPart;
import blcrawler.model.ConsoleGUIModel;
import blcrawler.model.MoldEmpirical;
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

public class InputMoldData
{
	public CatalogPart part;
	public MoldEmpirical moldData;
	
	private static Button acceptButton;
	private static TextField capacity1;
    private static TextField capacity2;
    private static TextField capacity3;
    private static TextField capacity4;
    private static TextField capacity5;
    private static TextField capacity6;
    private static TextField capacity7;
    private static TextField capacity8;
    private static TextField capacity9;
    private static TextField capacity10;
    private static TextField capacity11;
    private static TextField capacity12;
    private static TextField capacity13;
    private static TextField capacity14;
    private static TextField capacity15;
    private static TextField capacity16;
    private static TextField capacity2x;
    private static TextField capacity4x;
    private static TextField capacity6x;
    private static TextField capacity8x;
    private static TextField capacity10x;
    
    private static Label l_capacity1;
    private static Label l_capacity2;
    private static Label l_capacity3;
    private static Label l_capacity4;
    private static Label l_capacity5;
    private static Label l_capacity6;
    private static Label l_capacity7;
    private static Label l_capacity8;
    private static Label l_capacity9;
    private static Label l_capacity10;
    private static Label l_capacity11;
    private static Label l_capacity12;
    private static Label l_capacity13;
    private static Label l_capacity14;
    private static Label l_capacity15;
    private static Label l_capacity16;
    private static Label l_capacity2x;
    private static Label l_capacity4x;
    private static Label l_capacity6x;
    private static Label l_capacity8x;
    private static Label l_capacity10x;
    
    private static HBox h_capacity1;
    private static HBox h_capacity2;
    private static HBox h_capacity3;
    private static HBox h_capacity4;
    private static HBox h_capacity5;
    private static HBox h_capacity6;
    private static HBox h_capacity7;
    private static HBox h_capacity8;
    private static HBox h_capacity9;
    private static HBox h_capacity10;
    private static HBox h_capacity11;
    private static HBox h_capacity12;
    private static HBox h_capacity13;
    private static HBox h_capacity14;
    private static HBox h_capacity15;
    private static HBox h_capacity16;
    private static HBox h_capacity2x;
    private static HBox h_capacity4x;
    private static HBox h_capacity6x;
    private static HBox h_capacity8x;
    private static HBox h_capacity10x;
	
    public InputMoldData(CatalogPart a_part)
    {
        part = a_part;
        moldData = part.getMoldData();
        int[] capacities = moldData.getEmpiricalMeasurementsSingle();
        int[] capacities2x = moldData.getEmpiricalMeasurementsDouble();
        capacity1.setText(String.valueOf(capacities[1]));
        capacity2.setText(String.valueOf(capacities[2]));
        capacity3.setText(String.valueOf(capacities[3]));
        capacity4.setText(String.valueOf(capacities[4]));
        capacity5.setText(String.valueOf(capacities[5]));
        capacity6.setText(String.valueOf(capacities[6]));
        capacity7.setText(String.valueOf(capacities[7]));
        capacity8.setText(String.valueOf(capacities[8]));
        capacity9.setText(String.valueOf(capacities[9]));
        capacity10.setText(String.valueOf(capacities[10]));
        capacity11.setText(String.valueOf(capacities[11]));
        capacity12.setText(String.valueOf(capacities[12]));
        capacity13.setText(String.valueOf(capacities[13]));
        capacity14.setText(String.valueOf(capacities[14]));
        capacity15.setText(String.valueOf(capacities[15]));
        capacity16.setText(String.valueOf(capacities[16]));
        capacity2x.setText(String.valueOf(capacities2x[2]));
        capacity4x.setText(String.valueOf(capacities2x[4]));
        capacity6x.setText(String.valueOf(capacities2x[6]));
        capacity8x.setText(String.valueOf(capacities2x[8]));
        capacity10x.setText(String.valueOf(capacities2x[10]));
        
        
    }
	
	public void display()
	{
		Stage window = new Stage();
		
		window.setTitle("Assess Molds");
		
        capacity1 = new TextField();
        capacity2 = new TextField();
        capacity3 = new TextField();
        capacity4 = new TextField();
        capacity5 = new TextField();
        capacity6 = new TextField();
        capacity7 = new TextField();
        capacity8 = new TextField();
        capacity9 = new TextField();
        capacity10 = new TextField();
        capacity11 = new TextField();
        capacity12 = new TextField();
        capacity13 = new TextField();
        capacity14 = new TextField();
        capacity15 = new TextField();
        capacity16 = new TextField();
        
        l_capacity1 = new Label("Measured Capacity, Size 1 ");
        l_capacity2 = new Label("Measured Capacity, Size 2 ");
        l_capacity3 = new Label("Measured Capacity, Size 3 ");
        l_capacity4 = new Label("Measured Capacity, Size 4 ");
        l_capacity5 = new Label("Measured Capacity, Size 5 ");
        l_capacity6 = new Label("Measured Capacity, Size 6 ");
        l_capacity7 = new Label("Measured Capacity, Size 7 ");
        l_capacity8 = new Label("Measured Capacity, Size 8 ");
        l_capacity9 = new Label("Measured Capacity, Size 9 ");
        l_capacity10 = new Label("Measured Capacity, Size 10");
        l_capacity11 = new Label("Measured Capacity, Size 11");
        l_capacity12 = new Label("Measured Capacity, Size 12");
        l_capacity13 = new Label("Measured Capacity, Size 13");
        l_capacity14 = new Label("Measured Capacity, Size 14");
        l_capacity15 = new Label("Measured Capacity, Size 15");
        l_capacity16 = new Label("Measured Capacity, Size 16");
        
        l_capacity1.setPadding(new Insets(0,0,10,0));
        l_capacity2.setPadding(new Insets(0,0,10,0));
        l_capacity3.setPadding(new Insets(0,0,10,0));
        l_capacity4.setPadding(new Insets(0,0,10,0));
        l_capacity5.setPadding(new Insets(0,0,10,0));
        l_capacity6.setPadding(new Insets(0,0,10,0));
        l_capacity7.setPadding(new Insets(0,0,10,0));
        l_capacity8.setPadding(new Insets(0,0,10,0));
        l_capacity9.setPadding(new Insets(0,0,10,0));
        l_capacity10.setPadding(new Insets(0,0,10,0));
        l_capacity11.setPadding(new Insets(0,0,10,0));
        l_capacity12.setPadding(new Insets(0,0,10,0));
        l_capacity13.setPadding(new Insets(0,0,10,0));
        l_capacity14.setPadding(new Insets(0,0,10,0));
        l_capacity15.setPadding(new Insets(0,0,10,0));
        l_capacity16.setPadding(new Insets(0,0,10,0));
        
        

        h_capacity1 = new HBox();
        h_capacity1.getChildren().add(l_capacity1);
        h_capacity1.getChildren().add(capacity1);
        h_capacity2 = new HBox();
        h_capacity2.getChildren().add(l_capacity2);
        h_capacity2.getChildren().add(capacity2);
        h_capacity3 = new HBox();
        h_capacity3.getChildren().add(l_capacity3);
        h_capacity3.getChildren().add(capacity3);
        h_capacity4 = new HBox();
        h_capacity4.getChildren().add(l_capacity4);
        h_capacity4.getChildren().add(capacity4);
        h_capacity5 = new HBox();
        h_capacity5.getChildren().add(l_capacity5);
        h_capacity5.getChildren().add(capacity5);
        h_capacity6 = new HBox();
        h_capacity6.getChildren().add(l_capacity6);
        h_capacity6.getChildren().add(capacity6);
        h_capacity7 = new HBox();
        h_capacity7.getChildren().add(l_capacity7);
        h_capacity7.getChildren().add(capacity7);
        h_capacity8 = new HBox();
        h_capacity8.getChildren().add(l_capacity8);
        h_capacity8.getChildren().add(capacity8);
        h_capacity9 = new HBox();
        h_capacity9.getChildren().add(l_capacity9);
        h_capacity9.getChildren().add(capacity9);
        h_capacity10 = new HBox();
        h_capacity10.getChildren().add(l_capacity10);
        h_capacity10.getChildren().add(capacity10);
        h_capacity11 = new HBox();
        h_capacity11.getChildren().add(l_capacity11);
        h_capacity11.getChildren().add(capacity11);
        h_capacity12 = new HBox();
        h_capacity12.getChildren().add(l_capacity12);
        h_capacity12.getChildren().add(capacity12);
        h_capacity13 = new HBox();
        h_capacity13.getChildren().add(l_capacity13);
        h_capacity13.getChildren().add(capacity13);
        h_capacity14 = new HBox();
        h_capacity14.getChildren().add(l_capacity14);
        h_capacity14.getChildren().add(capacity14);
        h_capacity15 = new HBox();
        h_capacity15.getChildren().add(l_capacity15);
        h_capacity15.getChildren().add(capacity15);
        h_capacity16 = new HBox();
        h_capacity16.getChildren().add(l_capacity16);
        h_capacity16.getChildren().add(capacity16);
        h_capacity1.setPadding(new Insets(5,5,5,5));
        h_capacity2.setPadding(new Insets(5,5,5,5));
        h_capacity3.setPadding(new Insets(5,5,5,5));
        h_capacity4.setPadding(new Insets(5,5,5,5));
        h_capacity5.setPadding(new Insets(5,5,5,5));
        h_capacity6.setPadding(new Insets(5,5,5,5));
        h_capacity7.setPadding(new Insets(5,5,5,5));
        h_capacity8.setPadding(new Insets(5,5,5,5));
        h_capacity9.setPadding(new Insets(5,5,5,5));
        h_capacity10.setPadding(new Insets(5,5,5,5));
        h_capacity11.setPadding(new Insets(5,5,5,5));
        h_capacity12.setPadding(new Insets(5,5,5,5));
        h_capacity13.setPadding(new Insets(5,5,5,5));
        h_capacity14.setPadding(new Insets(5,5,5,5));
        h_capacity15.setPadding(new Insets(5,5,5,5));
        h_capacity16.setPadding(new Insets(5,5,5,5));
        
        
		
		acceptButton = new Button("Enter Mold Data");
		acceptButton.setOnAction(e -> window.close());
		
		VBox mainLayout = new VBox();
		
		mainLayout.getChildren().add(h_capacity1);
        mainLayout.getChildren().add(h_capacity2);
        mainLayout.getChildren().add(h_capacity3);
        mainLayout.getChildren().add(h_capacity4);
        mainLayout.getChildren().add(h_capacity5);
        mainLayout.getChildren().add(h_capacity6);
        mainLayout.getChildren().add(h_capacity7);
        mainLayout.getChildren().add(h_capacity8);
        mainLayout.getChildren().add(h_capacity9);
        mainLayout.getChildren().add(h_capacity10);
        mainLayout.getChildren().add(h_capacity11);
        mainLayout.getChildren().add(h_capacity12);
        mainLayout.getChildren().add(h_capacity13);
        mainLayout.getChildren().add(h_capacity14);
        mainLayout.getChildren().add(h_capacity15);
        mainLayout.getChildren().add(h_capacity16);
        
        mainLayout.setPadding(new Insets(5,5,5,5));
		
		Scene scene = new Scene(mainLayout);
		window.setScene(scene);
		window.show();
	}


    public CatalogPart getPart()
    {
        return part;
    }


    public void setPart(CatalogPart part)
    {
        this.part = part;
    }
	
	
}
