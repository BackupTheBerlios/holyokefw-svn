/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.hokserver.ConfigApp;
import citibob.sql.pgsql.SqlString;
import java.io.File;


/**
 *
 * @author citibob
 */
public abstract class DirNamer
{
	public abstract String getTableName();
	
	/** Returns a where clause, parsing key values out of directory name */
	public abstract String getWhereClause(String dirName);
	
	/** Returns a configuration based on the key values */
	public String getDirName(String... keys)
		{ return keys[0]; }

	public File getDir(ConfigApp app, String... keys)
	{
		File parentDir = new File(app.configRoot(), getTableName());
		File ret = new File(parentDir, getDirName(keys));
		System.out.println("DirNamer.getDir() returning " + ret);
		return ret;
	}
	
//	public DirNamer(ConfigApp app) { this.app = app; }
	
//	/** Copies a config directory from a File to a ConfigWriter */
//	void copyConfig(ConfigApp app, DirConfig src, WriteableConfig dest)
//	throws IOException {
//		ConfigUtils.copy(src, dest, null);
//	}

// ---------------------------------------------------------------
static class app_custs extends DirNamer {
	public String getDirName(String... keys)
		{ return keys[1] + "_" + keys[0]; }
	public String getTableName() { return "app_custs"; }
	public String getWhereClause(String dirName) {
		int dash = dirName.indexOf('_');
		String cust = dirName.substring(0, dash);
		String app = dirName.substring(dash+1);

		return
			"(appid = (select appid from apps where name = " + SqlString.sql(app) + ")" +
			" and " +
			"custid = (select appid from custs where name = " + SqlString.sql(cust) + "))";
	}
}
// ---------------------------------------------------------------
static class app_vers extends DirNamer {
public String getDirName(String... keys)
	{ return keys[0] + "_" + keys[1]; }
public String getTableName() { return "app_vers"; }
public String getWhereClause(String dirName) {
	int dash = dirName.indexOf('_');
	String app = dirName.substring(0, dash);
	String vers = dirName.substring(dash+1);
	
	return
		"(appid = (select appid from apps where name = " + SqlString.sql(app) + ")" +
		" and version = " + SqlString.sql(vers) + ")";
}}
// ---------------------------------------------------------------

static class apps extends DirNamer {
public String getTableName() { return "apps"; }
public String getWhereClause(String dirName) {
	return
		"appid = (select appid from apps where name = " + SqlString.sql(dirName) + ")";
}}
// ---------------------------------------------------------------
static class custs extends DirNamer {
public String getTableName() { return "custs"; }
public String getWhereClause(String dirName) {
	return
		"custid = (select custid from custs where name = " + SqlString.sql(dirName) + ")";
}}
// ============================================================
//static final DirNamer[] dirsInPriority = {
//	new app_custs(), new custs(), new app_vers(), new apps()
//};
}
