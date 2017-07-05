package blcrawler.view;



import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import blcrawler.model.ConsoleGUIModel;
import blcrawler.controller.GUIMainController;
import net.miginfocom.swing.MigLayout;


public class ConsoleGUIView {
	
	private static GUIMainController guiMainController;
	private static ConsoleGUIModel guiModel;
	
	private static final String key = "ENTER";
	/**
	 * @return the guiMainController
	 */
	public static GUIMainController getGuiMainController() {
		return guiMainController;
	}
	/**
	 * @return the guiModel
	 */
	public static ConsoleGUIModel getGuiModel() {
		return guiModel;
	}
	/**
	 * @return the key
	 */
	public static String getKey() {
		return key;
	}
	/**
	 * @param guiMainController the guiMainController to set
	 */
	public static void setGuiMainController(GUIMainController guiMainController) {
		ConsoleGUIView.guiMainController = guiMainController;
	}
	/**
	 * @param guiModel the guiModel to set
	 */
	public static void setGuiModel(ConsoleGUIModel guiModel) {
		ConsoleGUIView.guiModel = guiModel;
	}
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
    private JTextArea consoleOut;
    
	

	private JTextArea commandLine;
	
	
	private JScrollPane scrollableConsole;


	private JProgressBar queueBar;


	private JProgressBar taskBar;


	private KeyStroke keyStroke;


	public ConsoleGUIView(GUIMainController guiMainController, ConsoleGUIModel guiModel) {
		
		  this.guiMainController = guiMainController;
		  this.guiModel = guiModel;
		  
		  
		
		  mainFrame = new JFrame("Bricklink");
		  mainFrame.setSize(1000,640);
		  
		  
		  mainPanel = new JPanel();
		  mainPanel.setLayout(new MigLayout());
		  
		  //Center on the screen
		  Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		  int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
		  int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
		  mainFrame.setLocation(x, y);
		  
		  consoleOut=new JTextArea(25,60);
		  

		  DefaultCaret caret = (DefaultCaret)consoleOut.getCaret();
		  caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		  consoleOut.setEditable(false);
		  consoleOut.setLineWrap(true);
		  JScrollPane scrollableConsole = new JScrollPane(consoleOut);
		  

		  
		  commandLine=new JTextArea(5,60);
		  keyStroke = KeyStroke.getKeyStroke(key);
		  commandLine.setEditable(true);
		  commandLine.setLineWrap(true);
		  commandLine.getInputMap(JComponent.WHEN_FOCUSED).put(keyStroke,"Thing");
		  commandLine.getActionMap().put("Thing", this.guiMainController.getCommandEntered());


		  queueBar = new JProgressBar(0,100);
		  queueBar.setPreferredSize(new Dimension(660,10));
		  

		  taskBar = new JProgressBar(0,100);
		  taskBar.setPreferredSize(new Dimension(660,10));
		  
		  setIndeterminiteOff();
		  
		  headerLabel = new JLabel(guiModel.getHeaderLabel(),JLabel.CENTER );
		  statusLabel = new JLabel(guiModel.getStatusLabel(),JLabel.CENTER);        
		
		  
		  mainFrame.addWindowListener(new WindowAdapter() {
		     public void windowClosing(WindowEvent windowEvent){
		        ConsoleGUIModel.getSelenium().killAllProcesses();
		    	 System.exit(0);
		     }        
		  });    
		  


		  
		  mainFrame.add(mainPanel);
		  mainPanel.add(headerLabel, "wrap 20");
		  mainPanel.add(scrollableConsole, "span 6");
		  mainPanel.add(statusLabel, "wrap");
		  mainPanel.add(commandLine, "wrap 6");
		  mainPanel.add(queueBar, "wrap");
		  mainPanel.add(taskBar, "wrap");
		  
		  
		  mainFrame.setVisible(true);
		 		  


	}
	



	public void clearConsoleIn() {
		commandLine.setText(null);
	}


	public String getCLText() {
		return commandLine.getText();
	}


	/**
	 * @return the commandLine
	 */
	public JTextArea getCommandLine() {
		return commandLine;
	}


	/**
	 * @return the consoleOut
	 */
	public JTextArea getConsoleOut() {
		return consoleOut;
	}


	/**
	 * @return the controlPanel
	 */
	public JPanel getControlPanel() {
		return controlPanel;
	}


	/**
	 * @return the headerLabel
	 */
	public JLabel getHeaderLabel() {
		return headerLabel;
	}


	/**
	 * @return the keyStroke
	 */
	public KeyStroke getKeyStroke() {
		return keyStroke;
	}


	/**
	 * @return the mainFrame
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}


	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}


	/**
	 * @return the scrollableConsole
	 */
	public JScrollPane getScrollableConsole() {
		return scrollableConsole;
	}


	/**
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		return statusLabel;
	}


	/**
	 * @param commandLine the commandLine to set
	 */
	public void setCommandLine(JTextArea commandLine) {
		this.commandLine = commandLine;
	}
	
	/**
	 * @param consoleOut the consoleOut to set
	 */
	public void setConsoleOut(JTextArea consoleOut) {
		this.consoleOut = consoleOut;
	}


	/**
	 * @param controlPanel the controlPanel to set
	 */
	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}


	/**
	 * @param headerLabel the headerLabel to set
	 */
	public void setHeaderLabel(JLabel headerLabel) {
		this.headerLabel = headerLabel;
	}


	public void setIndeterminiteOff() {
		taskBar.setIndeterminate(false);
		queueBar.setIndeterminate(false);
	}


	public void setIndeterminiteOn() {
		taskBar.setIndeterminate(true);
		queueBar.setIndeterminate(true);
	}


	/**
	 * @param keyStroke the keyStroke to set
	 */
	public void setKeyStroke(KeyStroke keyStroke) {
		this.keyStroke = keyStroke;
	}


	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}


	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	/**
	 * @param scrollableConsole the scrollableConsole to set
	 */
	public void setScrollableConsole(JScrollPane scrollableConsole) {
		this.scrollableConsole = scrollableConsole;
	}
	
	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}
	
	public void setTaskBar(int value) {
		taskBar.setValue(value);
	}

}
