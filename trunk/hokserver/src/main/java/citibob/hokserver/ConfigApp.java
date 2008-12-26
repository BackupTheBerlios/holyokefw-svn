/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver;

import citibob.app.App;
import citibob.config.ConfigChain;
import citibob.config.DirConfig;
import citibob.sql.ConfigConnFactory;
import citibob.sql.ConnFactory;
import citibob.sql.pgsql.PgsqlSwingerMap;
import citibob.task.SimpleExpHandler;
import java.io.File;
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
protected void init(ConfigChain config) throws Exception
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

public ConfigApp(File configDir) throws Exception
{
	ConfigChain config0 = new ConfigChain();
	config0.add(new DirConfig(configDir));
	init(config0);
	
}

	
}
