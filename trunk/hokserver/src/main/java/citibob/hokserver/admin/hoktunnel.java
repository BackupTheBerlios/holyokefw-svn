///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.hokserver.admin;
//
//import java.io.File;
//import java.io.PrintStream;
//import org.apache.commons.cli.CommandLine;
//import org.apache.commons.cli.OptionBuilder;
//import org.apache.commons.cli.Options;
//
///**
// *
// * @author citibob
// */
//public class hoktunnel {
//
//static class MyParser extends HokCmdParser {
//public MyParser()
//	{ super("hoktunnel <options> [-- <psql options>]"); }
//};
//
//static void runTunnel(String exeName, CommandLine line) throws Exception
//{
//	// Redirect stdout to stderr
//	PrintStream sout = System.out;
//	PrintStream serr = System.err;
//	System.setOut(serr);
//
//
//	String launcherName = line.getOptionValue("launcher", null);
//	System.out.println(launcherName);
//	File configFile = new File(launcherName);
//	HSTunnel tunnel = new HSTunnel(configFile);
//
//	// Forward to psql process
//	String[] cmd0 = new String[] {exeName,
//		"-h", "127.0.0.1",
//		"-p", (""+ tunnel.getPort()),
//		"-U", tunnel.getUser(),
//		tunnel.getDatabase()};
//	String[] cmd = HokCmdParser.flattenArgs(cmd0, line.getArgs());
//
//	String[] env = new String[] {
//		"PGPASSWORD=" + tunnel.getPassword()};
//
//	// Pipe result of command to our actual stdout
//	tunnel.exec(cmd, env,
//		HSTunnel.openIstream(line.getOptionValue("i", null), System.in),
//		HSTunnel.openOstream(line.getOptionValue("o", null), System.out),
//		System.err);
//}
//
//
//
//
//
//public static void main(String[] args) throws Exception
//{
//	CommandLine line = new MyParser().parse(args);
//	runPsqlCommand("psql", line);
//}
//
//}
