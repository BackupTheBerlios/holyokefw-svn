/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.Config;
import java.io.File;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;


/**
 *
 * @author citibob
 */
public class hokexport {

static class MyParser extends HokCmdParser {
public MyParser()
	{ super("hokexport <options> [-- <pg_dump options>]"); }
};


public static void main(String[] args) throws Exception
{
//	CommandLine line = new MyParser().parse(args);
//	String launcherName = line.getOptionValue("launcher", null);

//	// Redirect stdout to stderr
//	PrintStream sout = System.out;
//	PrintStream serr = System.err;
//	System.setOut(serr);


	String launcherName = args[0];
	File configFile = new File(launcherName);

	Config config = HSTunnel.newConfig(configFile);
//	Properties props = config.loadAppProperties();

	ExportToPem.exportToPem(config);

Todo:
	
	1. Write stunnel.conf files
}

}
