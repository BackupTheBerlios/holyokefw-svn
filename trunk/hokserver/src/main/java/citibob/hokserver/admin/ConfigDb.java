/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.ConfigUtils;
import citibob.config.DirConfig;
import citibob.config.ListableConfig;
import citibob.config.MemConfig;
import citibob.config.WriteableConfig;
import citibob.config.ZipConfig;
import citibob.hokserver.ConfigApp;
import citibob.io.IOUtils;
import citibob.sql.RsTasklet;
import citibob.sql.RsTasklet2;
import citibob.sql.SqlRun;
import citibob.sql.ansi.SqlTimestamp;
import citibob.sql.pgsql.SqlBytea;
import citibob.sql.pgsql.SqlString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Stuff to read and write confrigs to the database
 * @author citibob
 */
public class ConfigDb {
// =========================================================
// ------------------------------------------------------------
static SqlTimestamp gmtTimestamp = new SqlTimestamp("GMT");
public static void uploadConfig(SqlRun str,
byte[] ssetBytes, Date lastModified,
String tableName, String whereClause)
{
	String sql =
		" update " + tableName +
		" set config = " + SqlBytea.sql(ssetBytes) + ", " +
		" configgmt = " + gmtTimestamp.toSql(lastModified) +
		" where " + whereClause;
	str.execSql(sql);
}

public static void uploadConfig(SqlRun str,
final File oneConfigDir,
final char[] password,
final String tableName, final String whereClause)
{
	// Get last modified in the database
	String sql =
		" select configgmt from " + tableName +
		" where " + whereClause;
	str.execSql(sql, new RsTasklet2() {
	public void run(SqlRun str, ResultSet rs)
	throws IOException, SQLException {
		Date dbTS = null;
		if (rs.next()) dbTS = (Date)gmtTimestamp.get(rs, "configgmt");
		
		long fileMS;
		fileMS = IOUtils.maxLastModified(oneConfigDir);
System.out.println("fileMS = " + new Date(fileMS));
		if (dbTS == null || fileMS > dbTS.getTime()) {
			// Convert directory to zipped bytes
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ZipConfig.writeToStream(new DirConfig(oneConfigDir), bout);
//			ZipConfigWriter zout = new ZipConfigWriter(bout, password);
//			zout.writeDir(oneConfigDir,null, null, null);
//			zout.close();

			// Update it!
			uploadConfig(str, bout.toByteArray(), new Date(fileMS),
				tableName, whereClause);
				
		}
	}});
}

/**
 * Uploads all the configs for one table name
 * @param str
 * @param configRoot
 * @param password Should be null.
 * @param dn
 */
public static void uploadConfigs(SqlRun str,
final File configRoot,
final char[] password,
final DirNamer dn)
{
	File configDir = new File(configRoot, dn.getTableName());
	File[] files = configDir.listFiles();
	if (files == null) return;		// Nothing for this level
	for (File file : files) {
		if (!file.isDirectory()) continue;	// ignore non-directories
		System.out.println("Uploading config " + file);
		uploadConfig(str, file, password,
			dn.getTableName(), dn.getWhereClause(file.getName()));
	}
}

public static void uploadAllConfigs(SqlRun str,
final File configRoot,
final char[] password)
{
	DirNamer[] dns = new DirNamer[] {
		new DirNamer.apps(),
		new DirNamer.custs(),
		new DirNamer.app_vers(),
		new DirNamer.app_custs()
	};
	for (DirNamer dn : dns)
		uploadConfigs(str, configRoot, password, dn);
}


public static MemConfig readConfig(SqlRun str,
String tableName, String whereClause)
{
	final MemConfig config = new MemConfig();
	String sql =
		" select config from " + tableName +
		" where " + whereClause;
	str.execSql(sql, new RsTasklet() {
	public void run(ResultSet rs)
	throws IOException, SQLException {
		if (!rs.next()) return;
		byte[] bytes = (byte[])rs.getObject("config");
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ZipConfig.loadFromStream(config, bin);		
	}});
	return config;
}

}
