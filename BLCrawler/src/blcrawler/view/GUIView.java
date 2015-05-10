package blcrawler.view;



import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import blcrawler.model.GUIModel;
import blcrawler.controller.GUIMainController;


public class GUIView {
	
	private static GUIMainController guiMainController;
	private static GUIModel guiModel;
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JTextArea consoleOut;
	

	public GUIView(GUIMainController guiMainController, GUIModel guiModel) {
		
		  this.guiMainController = guiMainController;
		  this.guiModel = guiModel;
		
		
		  mainFrame = new JFrame("Bricklink");
		  mainFrame.setSize(800,600);
		  mainFrame.setLayout(new GridLayout(3, 1));
		  
		  //Center on the screen
		  Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		  int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
		  int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
		  mainFrame.setLocation(x, y);
		  
		  consoleOut=new JTextArea();
		  consoleOut.setEditable(true);
		  consoleOut.setLineWrap(true);
		  consoleOut.setSize(new Dimension(200,100));
		  
		  headerLabel = new JLabel(guiModel.getHeaderLabel(),JLabel.CENTER );
		  statusLabel = new JLabel(guiModel.getStatusLabel(),JLabel.CENTER);        
		
		  statusLabel.setSize(50,50);
		  mainFrame.addWindowListener(new WindowAdapter() {
		     public void windowClosing(WindowEvent windowEvent){
		        System.exit(0);
		     }        
		  });    
		
		  mainFrame.add(headerLabel);
		  mainFrame.add(consoleOut);
		  mainFrame.add(statusLabel);
		  
		  mainFrame.setVisible(true);

	}

}
