package blcrawler.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

import blcrawler.model.Command;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;

public class ConsoleController {
	private List<String> validBaseCommands;
	
	public ConsoleController() {
		this.validBaseCommands = new ArrayList<String>();
		validBaseCommands.add("GetDate");
		//redirectSystemStreams();

		
		
		
	}
	
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
	

	private void updateTextAreaFromSystem(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if (!text.contains("\n"))
		    	{
		    		GUIModel.getGuiView().getConsoleOut().append("System: ");
		    	}
		    	
		      GUIModel.getGuiView().getConsoleOut().append(text);
		    }
		  });
		}
	
	public Command InterpretText(String textInput) {
		for (int i=0; i==validBaseCommands.size(); i++)
		{
			
			if (validBaseCommands.get(i).equals(textInput))
			{
				return new Command(textInput.substring(0,i));
			
			}
			
		}
		return new Command("invalid");

		
		
	}
	
	
	public void outputConsole(ConsoleOutput output)
	{
		GUIModel.getGuiView().getConsoleOut().append(output.getCombined());
	}
}
