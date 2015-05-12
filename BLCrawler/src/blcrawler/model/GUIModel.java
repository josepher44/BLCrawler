package blcrawler.model;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

import blcrawler.controller.ConsoleController;
import blcrawler.controller.GUIMainController;
import blcrawler.view.GUIView;

public class GUIModel {
	
	//private static GUIModel instance = new GUIModel("gui");
	private GUIMainController guiController;
	private static GUIView guiView;
	private ConsoleController consoleController;
	
	private String headerLabel;
	private String statusLabel;
	
	public GUIModel(String name) {
		
		consoleController = new ConsoleController();
		headerLabel = "Test for region that's having some issues displaying text, it looks all funny";
		statusLabel = "status";
		guiController = new GUIMainController(this);
		guiView = new GUIView(guiController, this);
		//redirectSystemStreams();

		
		 
	      
	      
	}

	

	/*	 
		private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextAreaFromSystem(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {

		      updateTextAreaFromSystem(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}
	
	*/
	
	
	/**
	 * @return the guiController
	 */
	public GUIMainController getGuiController() {
		return guiController;
	}

	/**
	 * @param guiController the guiController to set
	 */
	public void setGuiController(GUIMainController guiController) {
		this.guiController = guiController;
	}

	/**
	 * @return the guiView
	 */
	public static GUIView getGuiView() {
		return guiView;
	}

	/**
	 * @param guiView the guiView to set
	 */
	public void setGuiView(GUIView guiView) {
		this.guiView = guiView;
	}

	/**
	 * @return the headerLabel
	 */
	public String getHeaderLabel() {
		return headerLabel;
	}

	/**
	 * @param headerLabel the headerLabel to set
	 */
	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	/**
	 * @return the statusLabel
	 */
	public String getStatusLabel() {
		return statusLabel;
	}

	/**
	 * @param statusLabel the statusLabel to set
	 */
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

}
