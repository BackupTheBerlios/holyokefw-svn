/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver;

import citibob.config.RawConfig;
import citibob.config.StreamSetWriter;
import citibob.config.ZipStreamSetWriter;
import citibob.sql.RssTasklet;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlString;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author citibob
 * 
 * 
create table apps (
appid serial primary key,
name varchar(100) not null,
defaultVersion varchar(30),
config bytea);

create table app_vers (
appid int not null,
version varchar(30) not null,
url varchar(200) not null,
config bytea,
primary key(appid, version));

create table custs (
custid serial primary key,				-- Application for which this is a config (eg: offstagearts)
name varchar(100) not null,		-- Name of customer (eg: ballettheatre)
config bytea);			-- The binary configuartion file (see Config class)

create table app_custs (
appid int not null,
custid int not null,
version varchar(30),	-- Version currently used by this customer
config bytea,
primary key(appid, custid));

-- Users for a particular application/customer combo
create table users (
appid int not null,
custid int not null,
name varchar(100) not null,
password_md5 varchar(100) not null,		-- Encrypted via MD5
config bytea,
primary key(appid, custid, name));





Cascading hierarchies of configurations:

1. Config for all apps configured via this server: apps.appid=-1
2. App-specific config (offstagearts): apps.appid
3. App-version-specific config (offstagearts 1.9): appversopms
4. Customer-specific config (ballettheatre)
5. Customer-app specific config (oa-ballettheatre)
6. User-specific config (from DB)
7. User-specific config (generated on-the-fly)

 * 
 */
public class ConfigDbX {

/** Creates the on-the-fly StreamSet (in byte[] format) */
static byte[] userRawStreamSet(ResultSet rs)
throws SQLException, IOException
{
	String version = rs.getString("version");
	String url = rs.getString("url");
	String users_name = rs.getString("users_name");
	String apps_name = rs.getString("apps_name");
	String custs_name = rs.getString("custs_name");

	Properties props = new Properties();
	props.setProperty("app.name", apps_name);
	props.setProperty("app.version", version);
	props.setProperty("app.url", url);
	props.setProperty("cust.name", custs_name);
	props.setProperty("user.name", users_name);

	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	StreamSetWriter zout = new ZipStreamSetWriter(bout);
	zout.writeEntry("config.properties", props, false);
	zout.close();
	
	return bout.toByteArray();
}
	
public static RawConfig getRawConfig(SqlRun str,
String appName, String custName, String userName, char[] password)
{
	String sql =
		// rss[0]
		" select\n" +
		" apps.config as apps_config,\n" +
		" 	apps.defaultversion,\n" +
		"    apps.name as apps_name,\n" +
		" av.version,\n" +
		" case when apps.defaultversion = av.version then 1 else 0 end as priority," +
		" av.url,\n" +
		" av.config as app_vers_config,\n" +
		" custs.name as custs_name,\n" +
		" custs.config as custs_config,\n" +
		" ac.config as app_custs_config,\n" +
		" users.password_md5,\n" +
		" users.config as users_config,\n" +
		" users.name as users_name\n" +
		" from apps\n" +
		" inner join custs on (custs.name = " + SqlString.sql(custName) + ")\n" +
		" inner join users on (users.name = " + SqlString.sql(userName) + "\n" +
		" 	and users.appid = apps.appid\n" +
		" 	and users.custid = custs.custid)\n" +
		" inner join app_custs ac on (\n" +
		" 	ac.appid = apps.appid\n" +
		" 	and ac.custid = custs.custid)\n" +
		" inner join app_vers av on (\n" +
		" 	av.appid = apps.appid\n" +
		" 	and (av.version = ac.version or av.version = apps.defaultversion))\n" +
		" where apps.name = " + SqlString.sql(appName) + "\n" +
		" order by priority;\n" +
		
		// rss[1]: Overall system default config
		" select config from apps where appid = 0;\n";		
	final RawConfig config = new RawConfig();
	str.execSql(sql, new RssTasklet() {
		void add(byte[] bconfig) throws IOException {
			if (bconfig == null) return;
			config.add(bconfig);
//			InputStream in = new ByteArrayInputStream(bconfig);
//			StreamSet sset = ZipStreamSet.loadFromStream(in);
//			config.add(sset);
		}
		public void run(ResultSet[] rss)
		throws SQLException, IOException {
			ResultSet rs = rss[0];
			
			if (!rs.next()) return;		// Nothing here!  config will be zero length.

			// Create a config on-the-fly (highest priority)
			add(userRawStreamSet(rs));
			
			// Add configs pulled from database
			add(rs.getBytes("users_config"));
			add(rs.getBytes("app_custs_config"));
			add(rs.getBytes("custs_config"));
			add(rs.getBytes("app_vers_config"));
			add(rs.getBytes("apps_config"));

			// Add site-wide config
			rs = rss[1];
			if (!rs.next()) return;	// No systemwide default; ignore
			add(rs.getBytes("config"));
		}
	});
	
	return config;
}
// =================================================================
}
