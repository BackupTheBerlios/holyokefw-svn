/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import org.apache.commons.cli.CommandLine;

/**
 *
 * @author citibob
 */
public class hok_dump {

static class MyParser extends HokCmdParser {
public MyParser()
	{ super("hok_dump <options> [-- <pg_dump options>]"); }
};

public static void main(String[] args) throws Exception
{
	CommandLine line = new MyParser().parse(args);
	hoksql.runPsqlCommand("pg_dump", line);
}
}
