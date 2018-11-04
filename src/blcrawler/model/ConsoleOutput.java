package blcrawler.model;

public class ConsoleOutput {
	
	private String text;
	private String source;
	private String combined;
	
	public ConsoleOutput(String source, String text) {
		this.text=text;
		this.source=source;
		this.combined = source+": "+text+"\n";
		if (!source.equalsIgnoreCase("System"))
		{
			System.out.println("created a console output: "+combined);
		}
		ConsoleGUIModel.getConsoleController().outputConsole(this);
		ConsoleGUIModel.getGuiView().getConsoleOut().setCaretPosition(ConsoleGUIModel.getGuiView().getConsoleOut().getDocument().getLength());
	}

	/**
	 * @return the combined
	 */
	public String getCombined() {
		return combined;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param combined the combined to set
	 */
	public void setCombined(String combined) {
		this.combined = combined;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
