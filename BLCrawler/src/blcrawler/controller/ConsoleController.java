package blcrawler.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingUtilities;

import blcrawler.commands.*;
import blcrawler.commands.addpage.AddAllPartBrowses;
import blcrawler.commands.addpage.AddAllPartCatalogs;
import blcrawler.commands.addpage.AddPart;
import blcrawler.commands.addpage.AddPartBrowse;
import blcrawler.commands.addpage.AddPartCatalog;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.GUIModel;

public class ConsoleController {
	private List<String> validBaseCommands;
	private HashMap<String, Runnable> commandLibrary;
	private String commandBuffer;
	
	public ConsoleController() throws Exception {
		validBaseCommands = new ArrayList<String>();
		commandLibrary = new HashMap<String, Runnable>();
		commandLibrary.put("time", () -> {createTimestamp();});
		commandLibrary.put("timertest", () -> {createTimertest();});
		commandLibrary.put("invalid", () -> {createInvalid();});
		commandLibrary.put("addpage", () -> {createAddPage();});
		commandLibrary.put("addsite", () -> {createAddPage();});
		commandLibrary.put("addurl", () -> {createAddPage();});
		commandLibrary.put("addpartbrowse", () -> {createPartBrowse();});
		commandLibrary.put("addpartbrowseindex", () -> {createPartBrowseIndex();});
		commandLibrary.put("addpartcatalog", () -> {createPartCatalog();});
		commandLibrary.put("addpartcatalogindex", () -> {createPartCatalogIndex();});
		commandLibrary.put("expandpartcatalog", () -> {expandPartCatalog();});
		commandLibrary.put("addpartindex", () -> {addPartIndex();});
		commandLibrary.put("scrapeallparts", () -> {scrapeAllParts();});
		commandLibrary.put("addpart", () -> {createPart();});
		commandLibrary.put("killfirefox", () -> {killFirefox();});
		commandLibrary.put("killtor", () -> {killTor();});
		validBaseCommands.add("GetDate");
		redirectSystemStreams();

		
		
		
	}
	
	public void initializeCommand() {
		GUIModel.getGuiController().sendInToOut(GUIModel.getGuiView().getCLText());
		InterpretText(GUIModel.getGuiView().getCLText());
		GUIModel.getGuiView().clearConsoleIn();
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
			    	new ConsoleOutput("System", text);
		    	}

		    }
		  });
		}
	
	public void InterpretText(String textInput) {
		String command;
		int i = textInput.indexOf(' ');
		if (i!=-1)
		{
			command = textInput.substring(0, i);
		}
		else
		{
			command = textInput;
		}

		
		if (commandLibrary.containsKey(command))
		{
			commandBuffer = textInput;
			commandLibrary.get(command).run();
		}
			
	
		
		else
		{
			commandLibrary.get("invalid").run();
		}
		
		
	}
	
	
	public void outputConsole(ConsoleOutput output)
	{
		GUIModel.getGuiView().getConsoleOut().append(output.getCombined());
	}
	
	public Method packForHash(String s) throws Exception
	{
		return ConsoleController.class.getMethod(s);
	}
	
	public void createAddPage() {
		GUIModel.getTaskTimer().addToQueue(new AddUrl(commandBuffer.substring(commandBuffer.indexOf(' ')+1)));
	}
	
	public void createTimestamp() {
		GUIModel.getTaskTimer().addToQueue(new Timestamp());
		
	}
	
	public void createTimertest() {
		TimerTest timertest=new TimerTest();
		timertest.queue();
		GUIModel.getTaskTimer().addToQueue(timertest);
	}
	
	public void createPartBrowse() {
		AddPartBrowse addPartBrowse = new AddPartBrowse(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPartBrowse.queue();
		GUIModel.getTaskTimer().addToQueue(addPartBrowse);
	
	}
	
	public void createPart() {
		AddPart addPart = new AddPart(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPart.queue();
		GUIModel.getTaskTimer().addToQueue(addPart);
	
	}
	
	public void createPartCatalog() {
		AddPartCatalog addPartCatalog = new AddPartCatalog(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPartCatalog.queue();
		GUIModel.getTaskTimer().addToQueue(addPartCatalog);
	
	}
	
	public void expandPartCatalog() {
		GUIModel.getPageManager().expandPartCatalog();
	
	}
	
	public void scrapeAllParts() {
		GUIModel.getPageManager().scrapeRemainingParts();
	}
	
	public void addPartIndex() {
		GUIModel.getPageManager().buildPartIndex();
	
	}
	
	public void createPartBrowse(String string) {
		AddPartBrowse addPartBrowse = new AddPartBrowse(string);
		addPartBrowse.queue();
		GUIModel.getTaskTimer().addToQueue(addPartBrowse);
	
	}
	
	public void createPart(String string) {
		AddPart addPart = new AddPart(string);
		addPart.queue();
		GUIModel.getTaskTimer().addToQueue(addPart);
	
	}
	
	public void killFirefox() {
		Runtime rt = Runtime.getRuntime();

		try {
			rt.exec("taskkill /F /IM firefox.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void killTor() {
		try {
			GUIModel.getSeleniumModel().relaunchTor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createPartBrowseIndex() {
		AddAllPartBrowses index = new AddAllPartBrowses();
		GUIModel.getTaskTimer().addToQueue(index);
	}
	
	public void createPartCatalog(String string) {
		AddPartCatalog addPartCatalog = new AddPartCatalog(string);
		addPartCatalog.queue();
		GUIModel.getTaskTimer().addToQueue(addPartCatalog);
	
	}
	
	public void createPartCatalogIndex() {
		AddAllPartCatalogs index = new AddAllPartCatalogs();
		GUIModel.getTaskTimer().addToQueue(index);
	}	
	
	
	public void createInvalid() {
		new InvalidCommand();
	}
}
