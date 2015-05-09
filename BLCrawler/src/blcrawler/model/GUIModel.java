package blcrawler.model;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIModel {
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	
	public GUIModel(String name) {
		 mainFrame = new JFrame("Bricklink");
	      mainFrame.setSize(800,600);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      
	      //Center on the screen
	      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	      int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
	      int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
	      mainFrame.setLocation(x, y);

	      headerLabel = new JLabel("Yo",JLabel.CENTER );
	      statusLabel = new JLabel("Status",JLabel.CENTER);        

	      statusLabel.setSize(350,100);
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
	         }        
	      });    
	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	      
	      
	}

}
