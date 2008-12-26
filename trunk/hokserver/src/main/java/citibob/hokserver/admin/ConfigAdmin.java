/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.hokserver.ConfigApp;
import citibob.app.App;
import citibob.config.ConfigChain;
import citibob.config.DirConfig;
import citibob.config.MemConfig;
import citibob.io.IOUtils;
import citibob.sql.SqlRun;
import citibob.sql.pgsql.SqlString;
import com.Ostermiller.util.RandPass;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author citibob
 */
public class ConfigAdmin {

App app;
File configRoot;		// Root of config file tree
File keyRoot;		// Root of keys file tree
String psqlCmd;		// Command line flags to run psql on our customer database server, logging in as administrator

public ConfigAdmin(App app, File configRoot, File keyRoot, String psqlCmd)
{
	this.app = app;
	this.configRoot = configRoot;
	this.keyRoot = keyRoot;
	this.psqlCmd = psqlCmd;
}

// ------------------------------------------------------
static int exec(String cmd, String input, OutputStream out)
throws IOException, InterruptedException
{
	return exec(cmd, input, out, true);
}
static int exec(String cmd, String input, OutputStream out, boolean reportError)
throws IOException, InterruptedException
{
	if (out == null) out = System.out;
	System.out.println(">>> " + cmd);
	Process proc = Runtime.getRuntime().exec(cmd);
	
	// Provide the given input to the process
	if (input != null) {
		OutputStream lout = proc.getOutputStream();
		lout.write(input.getBytes());
		lout.flush();
	}
	
	InputStream in = proc.getInputStream();
	IOUtils.copy(in, out);
	out.flush();
	
	InputStream err = proc.getErrorStream();
	IOUtils.copy(err, System.out);
	System.out.flush();
	
	int ret = proc.waitFor();
	if (reportError & ret != 0) {
		throw new IOException("Command returned error code " + ret);
	}
	System.out.println(">>> [return value = " + ret + "]");
	
//	proc.destroy();
	
	return ret;
}
int psql(String database, String sql)
throws IOException, InterruptedException
{
	System.out.println(sql);
	return exec(psqlCmd + " " + database, sql + "\n\\q\n", System.out);
}
// ------------------------------------------------------
void deleteClientKeys(String custName)
throws IOException, InterruptedException
{
	String USERNAME = custName;
	File SERVERDIR = new File(keyRoot, "server");
	File CLIENTDIR = new File(keyRoot, "client");
	File CLIENTSSLDIR = new File(CLIENTDIR, "ssl");
	
	new File(CLIENTDIR, USERNAME + "-store.jks").delete();
	new File(CLIENTDIR, USERNAME + "-trust.jks").delete();
	new File(CLIENTDIR, USERNAME + ".cer").delete();
	new File(CLIENTDIR, "ssl/" + USERNAME + ".pem").delete();
	
	// Also need to delete some symlinks in the CAPATH
	// and remove client from the server's jks files
}
/**
 * 
 * @param custName
 * @param configDir Config dir under config/custs; symlinks for jks
 * files will be placed here.
 * @throws java.io.IOException
 * @throws java.lang.InterruptedException
 */
void makeClientKeys(String custName, File configDir)
throws IOException, InterruptedException
{
	
	File SERVERDIR = new File(keyRoot, "server");
	File CLIENTDIR = new File(keyRoot, "client");
	String USERNAME = custName;
	File CLIENTSTORE = new File(CLIENTDIR, USERNAME + "-store.jks");
	File CLIENTTRUST = new File(CLIENTDIR, USERNAME + "-trust.jks");
	File SERVERTRUST = new File(SERVERDIR, "server-trust.jks");
	String STOREPASS = "keyst0re";
	String KEYPASS = STOREPASS;
	File SERVERSSLDIR = new File(SERVERDIR, "ssl");
	File CLIENTSSLDIR = new File(CLIENTDIR, "ssl");
	File CAPATH = new File(SERVERSSLDIR, "capath");
	
	CLIENTDIR.mkdirs();
	SERVERDIR.mkdirs();
	CLIENTSSLDIR.mkdirs();
	CAPATH.mkdirs();

	CLIENTSTORE.delete();
	String cmd = "keytool -genkey -validity 36500" +
		" -alias " + USERNAME + "-private" +
		" -keystore " + CLIENTSTORE +
		" -keyalg rsa -keysize 2048" +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	String input = "\n\n" + USERNAME + "\n\n\nUS\nyes\n";
	exec(cmd, input, null);
	
	// Self-sign certificate
	File usernameCer = new File(CLIENTDIR, USERNAME + ".cer");
	cmd = "keytool -rfc -export" +
		" -alias " + USERNAME + "-private" +
		" -keystore " + CLIENTSTORE +
		" -file " + usernameCer +
		" -storepass " + STOREPASS + " -keypass " + KEYPASS;
	exec(cmd, null, null);

	// Import client into server's set of trusted certificates
	cmd = "keytool -delete" +
		" -alias " + USERNAME + "-cert" +
		" -keystore " + SERVERTRUST +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	exec(cmd, null, null, false);

	cmd = "keytool -import" +
		" -alias " + USERNAME + "-cert" +
		" -file " + usernameCer +
		" -keystore " + SERVERTRUST +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	exec(cmd, "yes\n", null);

	// Import server into client's set of trusted sertificates
	cmd = "keytool -delete -alias server-cert" +
		" -keystore " + CLIENTTRUST +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	exec(cmd, null, null, false);
	cmd = "keytool -import -alias server-cert" +
		" -file " + new File(SERVERDIR, "server.cer") +
		" -keystore " + CLIENTTRUST +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	exec(cmd, "yes\n", null);

	// ====================================================
	// OpenSSL Conversion

	// Convert client certificate to PEM format
	File PEM = new File(CLIENTSSLDIR, USERNAME + ".pem");
	OutputStream pemOut = new FileOutputStream(PEM);
	cmd = "openssl enc -in " + usernameCer;
	exec(cmd, null, pemOut);
	pemOut.close();
	
	// Import client into server's set of trusted keys
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	cmd = "openssl x509 -hash -noout -in " + PEM;
	exec(cmd, null, bout);
	byte[] bytes = bout.toByteArray();
	String hash = new String(bytes).trim();
	File PEMHASH = new File(SERVERSSLDIR, "capath/" + hash + ".0");
	PEMHASH.delete();
	
//	IOUtils.getRelative(configDir, PEM)
	cmd = "ln -s " + PEM.getPath() + " " + PEMHASH.getPath();
	exec(cmd, null, null);

	// ========================================================
	// Symlink into config dir
	if (configDir != null) {
		File store = new File(configDir, CLIENTSTORE.getName());
		store.delete();
		cmd = "ln -s " + CLIENTSTORE + " " + configDir;
		exec(cmd, null, null);
		
		File trust = new File(configDir, CLIENTTRUST.getName());
		trust.delete();
		cmd = "ln -s " + CLIENTTRUST + " " + configDir;
		exec(cmd, null, null);
	}
}

/**
 * 
 * @param custName
 * @param password
 * @return database name
 */
String createClientDb(String dbName, String custName)
throws IOException, InterruptedException
{
	String sql =
		" CREATE DATABASE " + dbName + "\n" +
		"   WITH ENCODING='UTF8'\n" +
		"        OWNER=" + custName + ";\n";
	psql("template1", sql);
	
	sql =
		" CREATE PROCEDURAL LANGUAGE plpgsql;\n" +
		" ALTER SCHEMA public OWNER TO " + custName + ";\n";
	psql(dbName, sql);

	return dbName;
}

String createCustRole(String custName, String password)
throws IOException, InterruptedException
{
	if (password == null) {
		RandPass rp = new RandPass();
		password = rp.getPass();
	}
	
	String sql =
		" CREATE ROLE " + custName + " LOGIN PASSWORD '" + password + "'\n" +
		"    VALID UNTIL 'infinity'\n" +
		"    CONNECTION LIMIT 100;\n";
	psql("template1", sql);
	
	return password;
}

void w_custs_add(SqlRun str, String custName)
throws IOException, InterruptedException
{
	// Set up map to do templates in config directory
	Map<String,Object> map = new TreeMap();
	map.put("db.user", custName);

	// Create customer role in database server
	System.out.println("===== Creating PostgreSQL user " + custName);
	String password = createCustRole(custName, null);
	map.put("db.password", password);
	
	// Create config directory
	// ... for custs
	File custsDir = new File(configRoot, "custs");
	System.out.println("===== Creating configuration " + custsDir);
	File inDir = new File(custsDir, "_proto");
	File outDir = new File(custsDir, custName);
	ConfigTree ct = new ConfigTree(null, null, null);
	ct.copyConfig(inDir, outDir, map);
	
	// Create keys (and symlink them into config too)
	System.out.println("===== Creating keys for " + custName);
	makeClientKeys(custName, outDir);
	
	// Set up record in configuration database
	System.out.println("===== Setting up config database for " + custName);
	str.execSql("select w_custs_add(" + SqlString.sql(custName) + ");");
	
}
void w_custs_del(SqlRun str, String custName)
throws IOException, InterruptedException
{
	// Create customer role in database server
	psql("template1", "drop user " + custName + ";");
	
	// Drop config directory
	File custsDir = new File(configRoot, "custs");
	File outDir = new File(custsDir, custName);
	IOUtils.deleteDir(outDir);
	
	// Delete keys
	System.out.println("===== Creating keys for " + custName);
	deleteClientKeys(custName);
	
	// Set up record in configuration database
	System.out.println("===== Setting up config database for " + custName);
	str.execSql("select w_custs_del(" + SqlString.sql(custName) + ");");
	
}

String app_custs_dbname(String appName, String custName)
{
	return custName + "_" + appName;
}
String app_vers_dbname(String appName, String versName)
{
	return appName + "_" + versName;
}

void w_apps_addcust(SqlRun str, String appName, String custName, String version)
throws IOException, InterruptedException
{
	// Set up map to do templates in config directory
	Map<String,Object> map = new TreeMap();
	String dbName = app_custs_dbname;
	map.put("db.database", dbName);
	
	// Create config directory
	// ... for custs
	File custsDir = new File(configRoot, "app_custs");
	File inDir = new File(custsDir, "_proto");
	File outDir = new File(custsDir, dbName);
	ConfigTree ct = new ConfigTree(null, null, null);
	ct.copyConfig(inDir, outDir, map);
	
	// Make the database
	createClientDb(dbName, custName);

	// Update in the config database
	str.execSql("select w_apps_addcust(" +
		SqlString.sql(appName) + ", " +
		SqlString.sql(custName) + ", " +
		SqlString.sql(version) + ");");
	
}
void w_apps_delcust(SqlRun str, String appName, String custName)
throws IOException, InterruptedException
{
	// Delete the config directory
	String dbName = custName + "_" + appName;
	File custsDir = new File(configRoot, "app_custs");
	File outDir = new File(custsDir, dbName);
	IOUtils.deleteDir(outDir);

	// Drop the database
	psql("template1", "drop database " + dbName + ";");
	
	// Update in the config database
	str.execSql("select w_apps_addcust(" +
		SqlString.sql(appName) + ", " +
		SqlString.sql(custName) + ");");	
}
// ------------------------------------------------------
// ------------------------------------------------------
public static void main(String[] args) throws Exception
{
	// Set up connection to config database
	String sconfig = "/export/home/citibob/tmp/config.sset";
	ConfigChain config0 = new ConfigChain();
	config0.add(new DirConfig(new File(sconfig)));
	App app = new ConfigApp(new File("/export/home/citibob/tmp/config.sset"));

	File configRoot = new File("/export/home/citibob/mvn/hokserver/configs");
	File keyRoot = new File("/export/home/citibob/mvn/hokserver/keys");
	String psqlCmd = "psql -U postgres";
	ConfigAdmin ca = new ConfigAdmin(app, configRoot, keyRoot, psqlCmd);

	ConfigTree ct = new ConfigTree(null, null, null);
	
	
	SqlRun str = app.sqlRun();
	
//	ca.w_custs_add(str, "ballettheatre");
	ConfigDb.uploadConfig(str,
		new File(configRoot, "custs/ballettheatre"),
		new File(keyRoot, "client"),
		null, "custs", "name = " + SqlString.sql("ballettheatre"));
	MemConfig config = ConfigDb.readConfig(str, "custs", "name = " + SqlString.sql("ballettheatre"));
	
//	ca.w_apps_addcust(str, "oa", "cust1", null);
	str.flush();
	
	System.out.println(new String(config.getStreamBytes("app.properties")));
	
	System.out.println("hoi");
}
}
