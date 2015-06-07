package blcrawler.model;

public class ConsoleOutput {
	
	private String text;
	private String source;
	private String combined;
	
	public ConsoleOutput(String source, String text) {
		this.text=text;
		this.source=source;
		combined = source+": "+text+"\n";
		GUIModel.getConsoleController().outputConsole(this);
		GUIModel.getGuiView().getConsoleOut().setCaretPosition(GUIModel.getGuiView().getConsoleOut().getDocument().getLength());
	}

	/**
	 * @return the combined
	 */
	public String getCombined() {
		return combined;
	}

	/**
	 * @param combined the combined to set
	 */
	public void setCombined(String combined) {
		this.combined = combined;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
