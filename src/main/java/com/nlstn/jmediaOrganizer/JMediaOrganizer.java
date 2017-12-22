package com.nlstn.jmediaOrganizer;

import java.io.File;

import com.nlstn.jmediaOrganizer.gui.Window;

/**
 * Main class, handles initialization.<br>
 * <br>
 * Creation: 09.12.2017<br>
 *
 * @author Niklas Lahnstein
 */
public class JMediaOrganizer {

	/**
	 * The reference to the main window
	 */
	private static Window	window;

	/**
	 * The currently chosen input folder, or null, if no input folder was chosen
	 */
	private static File		inputFolder	= null;

	public static void main(String[] args) {
		Settings.loadSettings();
		ProjectProperties.loadProjectProperties();
		System.out.println(ProjectProperties.getVersion());
		window = new Window();
	}

	public static Window getWindow() {
		return window;
	}

	public static File getInputFolder() {
		return inputFolder;
	}

	public static void setInputFolder(File inputFolder) {
		JMediaOrganizer.inputFolder = inputFolder;
	}

}
