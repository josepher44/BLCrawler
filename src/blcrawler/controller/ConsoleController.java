package blcrawler.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingUtilities;

import blcrawler.commands.*;
import blcrawler.commands.api.GetAllPartInventories;
import blcrawler.commands.depreciated.AddAllPartBrowses;
import blcrawler.commands.depreciated.AddAllPartCatalogs;
import blcrawler.commands.depreciated.AddPartBrowse;
import blcrawler.commands.depreciated.AddPartCatalog;
import blcrawler.commands.depreciated.AddUrl;
import blcrawler.commands.individualcalls.PartBLToRawDatabase;
import blcrawler.commands.toplevelcalls.AllImagesBLToDatabase;
import blcrawler.commands.toplevelcalls.AllPartsBLToRawDatabase;
import blcrawler.model.ConsoleOutput;
import blcrawler.model.ConsoleGUIModel;

public class ConsoleController
{
	private List<String> validBaseCommands;
	private HashMap<String, Runnable> commandLibrary;
	private String commandBuffer;

	public ConsoleController() throws Exception
	{
		validBaseCommands = new ArrayList<String>();
		commandLibrary = new HashMap<String, Runnable>();
		commandLibrary.put("time", () -> {createTimestamp();});
		commandLibrary.put("addallparts", () -> {addAllParts();});
		commandLibrary.put("clearallparts", () -> {clearAllParts();});
		commandLibrary.put("createselenium", () -> {createSelenium();});
		commandLibrary.put("fixhtml", () -> {fixHTMLs();});
		commandLibrary.put("buildparts", () -> {buildParts();});
		commandLibrary.put("buildapart", () -> {buildPartHTML();});
		commandLibrary.put("writepartsxml", () -> {writeXMLParts();});
		commandLibrary.put("buildapartxml", () -> {buildpartxml();});
		commandLibrary.put("readxml", () -> {readXMLParts();});
		commandLibrary.put("sortpriceguides", () -> {sortPriceGuides();});
		commandLibrary.put("sortitemsforsale", () -> {sortItemsForSale();});
		commandLibrary.put("testapi", () -> {testapi();});
		commandLibrary.put("cancelscrape", () -> {cancelScrape();});
		commandLibrary.put("httprequest", () -> {testhttprequest();});
		commandLibrary.put("addallimages", () -> {addAllImages();});
		commandLibrary.put("garbage", () -> {System.gc();});
		commandLibrary.put("buildmolds", () -> {buildMolds();});
		commandLibrary.put("addallinventories", () -> {addAllInventories();});
		commandLibrary.put("buildinventories", () -> {buildInventories();});
		commandLibrary.put("startgui", () -> {startGUI();});

		//Depreciated Methods old system. Avoid using or investigate function
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
//		commandLibrary.put("testmulti", () -> {testMulti();});
		validBaseCommands.add("GetDate");
		redirectSystemStreams();




	}

	public void createSelenium() {
		CreateSelenium createSelenium = new CreateSelenium(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		createSelenium.queue();
		ConsoleGUIModel.getSelenium().addToInstant(createSelenium);

	}

	public void createInvalid()
	{
		new InvalidCommand();
	}

	public void addAllParts()
	{
		AllPartsBLToRawDatabase addAllParts = new AllPartsBLToRawDatabase();
		addAllParts.queue();
		ConsoleGUIModel.getSelenium().addToInstant(addAllParts);
	}

	public void addAllImages()
	{
		AllImagesBLToDatabase command = new AllImagesBLToDatabase();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void startGUI()
	{
		StartGUI command = new StartGUI();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void addAllInventories()
	{
		GetAllPartInventories command = new GetAllPartInventories();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void buildInventories()
	{
		BuildInventories command = new BuildInventories();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void writeXMLParts()
	{
		WritePartsToXML command = new WritePartsToXML();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void cancelScrape()
	{
		CancelScrape command = new CancelScrape();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void buildMolds()
	{
		BuildMolds command = new BuildMolds();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void readXMLParts()
	{
		ReadPartsFromXML command = new ReadPartsFromXML();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void sortPriceGuides()
	{
		SortPriceGuides command = new SortPriceGuides();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void sortItemsForSale()
	{
		SortItemsForSale command = new SortItemsForSale();
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void fixHTMLs()
	{
		FixHTML fixhtml = new FixHTML();
		fixhtml.queue();
		ConsoleGUIModel.getSelenium().addToInstant(fixhtml);
	}

	public void buildParts()
	{
		BuildPartsFromHTML buildParts = new BuildPartsFromHTML();
		buildParts.queue();
		ConsoleGUIModel.getSelenium().addToInstant(buildParts);
	}

	public void buildPartHTML()
	{
		BuildPartHTML buildParts = new BuildPartHTML(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		buildParts.queue();
		ConsoleGUIModel.getSelenium().addToInstant(buildParts);
	}

	public void testhttprequest()
	{
		TestHTTPRequest command = new TestHTTPRequest(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		command.queue();
		ConsoleGUIModel.getSelenium().addToInstant(command);
	}

	public void testapi()
	{
		ApiTest test = new ApiTest(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		test.queue();
		ConsoleGUIModel.getSelenium().addToInstant(test);
	}

	public void buildpartxml()
	{
		BuildAPartXML buildParts = new BuildAPartXML(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		buildParts.queue();
		ConsoleGUIModel.getSelenium().addToInstant(buildParts);
	}

	public void addPartIndex()
	{
		ConsoleGUIModel.getPageManager().buildPartIndex();
	}

	public void initializeCommand()
	{
		ConsoleGUIModel.getGuiController().sendInToOut(ConsoleGUIModel.getGuiView().getCLText());
		InterpretText(ConsoleGUIModel.getGuiView().getCLText());
		ConsoleGUIModel.getGuiView().clearConsoleIn();
	}
	public void clearAllParts()
	{
		File dir = new File("C:/Users/Owner/Documents/BLCrawler/Catalog/Parts/");
		for(File file: dir.listFiles())
		    if (!file.isDirectory())
		        file.delete();

		new ConsoleOutput("Command Result", "parts directory successfully emptied.");
	}

	//Depreciated commands. Do not use without investigation
	public void createAddPage() {
		ConsoleGUIModel.getTaskTimer().addToQueue(new AddUrl(commandBuffer.substring(commandBuffer.indexOf(' ')+1)));
	}

	public void createPart() {
		PartBLToRawDatabase addPart = new PartBLToRawDatabase(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPart.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPart);

	}

	public void createPart(String string) {
		PartBLToRawDatabase addPart = new PartBLToRawDatabase(string);
		addPart.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPart);

	}

	public void createPartBrowse() {
		AddPartBrowse addPartBrowse = new AddPartBrowse(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPartBrowse.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPartBrowse);

	}

	public void createPartBrowse(String string) {
		AddPartBrowse addPartBrowse = new AddPartBrowse(string);
		addPartBrowse.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPartBrowse);

	}

	public void createPartBrowseIndex() {
		AddAllPartBrowses index = new AddAllPartBrowses();
		ConsoleGUIModel.getTaskTimer().addToQueue(index);
	}

	public void createPartCatalog() {
		AddPartCatalog addPartCatalog = new AddPartCatalog(commandBuffer.substring(commandBuffer.indexOf(' ')+1));
		addPartCatalog.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPartCatalog);

	}

	public void createPartCatalog(String string) {
		AddPartCatalog addPartCatalog = new AddPartCatalog(string);
		addPartCatalog.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(addPartCatalog);

	}

	public void createPartCatalogIndex() {
		AddAllPartCatalogs index = new AddAllPartCatalogs();
		ConsoleGUIModel.getTaskTimer().addToQueue(index);
	}

	public void createTimertest() {
		TimerTest timertest=new TimerTest();
		timertest.queue();
		ConsoleGUIModel.getTaskTimer().addToQueue(timertest);
	}

	public void createTimestamp() {
		ConsoleGUIModel.getSelenium().addToInstant(new Timestamp());

	}

	public void expandPartCatalog() {
		ConsoleGUIModel.getPageManager().expandPartCatalog();

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
			ConsoleGUIModel.getSeleniumModel().relaunchTor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scrapeAllParts() {
		ConsoleGUIModel.getPageManager().scrapeRemainingParts();
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
		ConsoleGUIModel.getGuiView().getConsoleOut().append(output.getCombined());
	}

	public Method packForHash(String s) throws Exception
	{
		return ConsoleController.class.getMethod(s);
	}

	private void redirectSystemStreams() {


		OutputStream out = new OutputStream() {
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }

		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {

		      updateTextAreaFromSystem(new String(b, off, len));
		    }

		    @Override
		    public void write(int b) throws IOException {
		      updateTextAreaFromSystem(String.valueOf((char) b));
		    }
		  };

		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));

	}




	//Read system text (exceptions, println, etc), and redirect to console out
	private void updateTextAreaFromSystem(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if (!text.contains("\n")&&text.length()>1)
		    	{
			    	new ConsoleOutput("System", text);
		    	}

		    }
		  });
		}
}
