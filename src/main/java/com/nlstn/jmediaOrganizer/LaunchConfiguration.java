package com.nlstn.jmediaOrganizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creation: 23.12.2017
 *
 * @author Niklas Lahnstein
 */
public class LaunchConfiguration {

	private static Logger log;

	static {
		log = LogManager.getLogger(LaunchConfiguration.class);
	}

	public static LaunchConfiguration parse(String[] args) {
		return new LaunchConfiguration(args);
	}

	private CommandLine		cmd;
	private Options			options;
	private HelpFormatter	help;

	private LaunchConfiguration(String[] args) {
		log.trace("Building CommandLineParser");
		options = new Options();

		Option headless = new Option("h", false, "Run in headless mode");
		headless.setRequired(false);
		options.addOption(headless);

		Option input = Option.builder("i").hasArg().desc("The folder to convert").longOpt("inputFolder").build();
		options.addOption(input);

		Option invalidTypes = Option.builder("t").hasArg().desc("Types to delete").longOpt("invalidTypes").build();
		options.addOption(invalidTypes);

		Option id3ToNameEnabled = Option.builder("id3").hasArg().desc("Enable ID3ToNameConverter").longOpt("id3ToNameEnabled").build();
		options.addOption(id3ToNameEnabled);

		Option id3ToNamePattern = Option.builder("id3p").hasArg().desc("ID3ToNameConverter Pattern").longOpt("id3ToNamePattern").build();
		options.addOption(id3ToNamePattern);

		Option outputFolder = Option.builder("out").hasArg().desc("Output Folder").longOpt("outputFolder").build();
		options.addOption(outputFolder);

		Option threadCount = Option.builder("tc").hasArg().desc("Thread Count for Conversion").longOpt("threadCount").build();
		options.addOption(threadCount);

		Option saveSettings = Option.builder("save").desc("Save overwritten settings").build();
		options.addOption(saveSettings);

		CommandLineParser parser = new DefaultParser();
		help = new HelpFormatter();

		try {
			cmd = parser.parse(options, args);
		}
		catch (ParseException e) {
			System.out.println(e.getMessage());
			help.printHelp("JMediaOrganizer", options);
			System.exit(1);
		}
		validateArgs();
	}

	private void validateArgs() {
		if (cmd.hasOption("h") && !cmd.hasOption("i")) {
			System.out.println("You need to specify an input folder (-i), if you run in headless mode!");
			help.printHelp("JMediaOrganizer", options);
			System.exit(1);
		}
	}

	public boolean isHeadlessMode() {
		return cmd.hasOption("h");
	}

	public String getInputFolder() {
		return cmd.getOptionValue("i");
	}

}
