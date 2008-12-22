/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver;

import citibob.app.App;
import citibob.config.Config;
import citibob.sql.ConfigConnFactory;
import citibob.sql.ConnFactory;
import citibob.sql.pgsql.PgsqlSwingerMap;
import citibob.task.SimpleExpHandler;
import java.util.TimeZone;

/**
 * App class for the program that edits and reads configs database
 * @author citibob
 */
public class ConfigApp extends App
{

/**
 * @param config A configuration, probably read from a directory
 * @throws java.io.IOException
 */
public ConfigApp(Config config) throws Exception
{
	this.config = config;
	expHandler = new SimpleExpHandler(System.out, false);
	ConnFactory cf = new ConfigConnFactory(config, "app.properties", expHandler);
	super.setSqlRun(cf);
	
	swingerMap = new PgsqlSwingerMap(TimeZone.getDefault());
	sFormatMap = swingerMap;
	TimeZone tz = TimeZone.getDefault();
//	schemaSet = new HokconfigSchemaSet(sqlRun(), null, tz);
}
	
}
