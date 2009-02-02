/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver;

import citibob.app.App;
import citibob.config.DirConfig;
import citibob.config.MultiConfig;
import citibob.config.ResourceConfig;
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

/** Prototype configurations in <protoDir>/<tableName> */
protected File appDir;
public File protoDir() { return new File(appDir, "protos"); }
public File configRoot() { return new File(appDir, "data/configs"); }
public File keyRoot() { return new File(appDir, "data/keys"); }
public File appDir() { return appDir; }

public ConfigApp()
throws Exception
{
	// Check environment variable
	File hsHome;
	String env = System.getenv("HOKSERVER_HOME");
	if (env == null) {
		hsHome = new File(System.getProperty("user.home"), ".hokserver");
	} else {
		hsHome = new File(env);
	}
	
	init(hsHome);
}

/**
 * @param config A configuration, probably read from a directory
 * @throws java.io.IOException
 */
public ConfigApp(File appDir) throws Exception
{
	init(appDir);
}
protected void init(File appDir) throws Exception
{
	this.name = "hokserver";
	this.appDir = appDir;
	
	File thisConfigDir = new File(appDir, "config");
	this.config = new MultiConfig(
		new DirConfig(thisConfigDir),
		new ResourceConfig("hokserver/defaultconfig"));
	expHandler = new SimpleExpHandler(System.out, false);
	props = config.loadAppProperties();
	ConnFactory cf = new ConfigConnFactory(config, props, expHandler);
	super.setSqlRun(cf);
	
	swingerMap = new PgsqlSwingerMap(TimeZone.getDefault());
	sFormatMap = swingerMap;
	TimeZone tz = TimeZone.getDefault();
//	schemaSet = new HokconfigSchemaSet(sqlRun(), null, tz);
}
//public ConfigApp(Config config) throws Exception
//{
//	init(config);
//}
//public ConfigApp(File configDir) throws Exception
//{
//	MultiConfig config0 = new MultiConfig();
//	config0.add(new DirConfig(configDir));
//	init(config0);
//	
//}

	
}
