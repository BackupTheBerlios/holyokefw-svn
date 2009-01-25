/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.DirConfig;
import citibob.config.MultiConfig;
import citibob.hokserver.ConfigApp;
import citibob.sql.RssTasklet2;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlString;
import java.io.File;
import java.sql.ResultSet;

/**
 *
 * @author citibob
 */
public class ConfigAndLaunchInfo {

String appName;
String custName;
String appURL;
String version;

public ConfigAndLaunchInfo(SqlRun str, ConfigApp app, String _appName, String _custName)
{
	this.appName = _appName;
	this.custName = _custName;
	String sql =
		"select r_configs_get('rs1', " +
		SqlString.sql(appName) + ", " +
		SqlString.sql(custName) + ");\n" +
		"fetch all in rs1;\n";
	str.execSql(sql, new RssTasklet2() {
	public void run(SqlRun str, ResultSet[] rss) throws Exception {
		ResultSet rs = rss[1];
		rs.next();	// Look at first line of result set
		appURL = rs.getString("url");
		version = rs.getString("version");
	}});
}

private void addDirConfig(MultiConfig ret, File dir)
{
	if (dir == null) return;
	if (!dir.exists()) return;
	ret.add(new DirConfig(dir));
}

public MultiConfig newDirConfig(ConfigApp app)
{
	MultiConfig ret = new MultiConfig();
	DirNamer dn;
	
	dn = new DirNamer.app_custs();
	addDirConfig(ret, dn.getDir(app, appName, custName));
	
	dn = new DirNamer.custs();
	addDirConfig(ret, dn.getDir(app, custName));

	dn = new DirNamer.app_vers();
	addDirConfig(ret, dn.getDir(app, appName, version));

	dn = new DirNamer.apps();
	addDirConfig(ret, dn.getDir(app, appName));
	
	ret.setName(custName);
	return ret;
}

}
