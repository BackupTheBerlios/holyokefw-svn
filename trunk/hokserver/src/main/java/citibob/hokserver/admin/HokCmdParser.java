/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author citibob2
 */
public abstract class HokCmdParser {

private String cmdName;
private Options options;

protected void addOptions(Options options)
{
	options.addOption(OptionBuilder
		.withArgName("file")
		.hasArg()
		.isRequired()
		.withDescription("Launcher JAR file used to connect to database")
		.create("launcher"));
	options.addOption(OptionBuilder
		.withArgName("file")
		.hasArg()
		.withDescription("Dump output to this file (compress if ends in .gz)")
		.create("o"));
	options.addOption(OptionBuilder
		.withArgName("file")
		.hasArg()
		.withDescription("Take SQL input from this file (compress if ends in .gz)")
		.create("i"));

}

public HokCmdParser(String cmdName) { this.cmdName = cmdName; }

public void printHelp()
{
	HelpFormatter hf = new HelpFormatter();
	hf.printHelp("hoksql", options);
	System.exit(0);
}


public CommandLine parse(String[] args)
{
	options = new Options();
	options.addOption(new Option(
		"help", "print this message"));
	addOptions(options);

    // create the parser
    CommandLineParser parser = new GnuParser();
    try {
        // parse the command line arguments
//        CommandLine line = parser.parse( options, args );
        CommandLine line = parser.parse( options, args, false);

		if (line.hasOption("help")) printHelp();
		return line;

    } catch( ParseException exp ) {
        // oops, something went wrong
        System.err.println( "Command line parsing failed.  Reason: " + exp.getMessage() );
		printHelp();
		return null;
	}
}

/** Utility method */
static String[] flattenArgs(Object... oargs)
{
	// Get the length
	int len = 0;
	for (Object o : oargs) {
		if (o instanceof String[])
			len += ((String[])o).length;
		else len += 1;
	}

	// Allocate and copy
	String[] ret = new String[len];
	int j=0;
	for (Object o : oargs) {
		if (o instanceof String[]) {
			for (String s : (String[])o) ret[j++] = s;
		} else {
			ret[j++] = o.toString();
		}
	}

	return ret;
}


}
