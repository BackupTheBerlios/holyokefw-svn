/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.Config;
import citibob.config.ConfigUtils;
import citibob.config.DirConfig;
import citibob.config.ListableConfig;
import citibob.config.MemConfig;
import citibob.config.MultiConfig;
import citibob.config.WriteableConfig;
import citibob.config.WriteablePBEConfig;
import citibob.config.ZipConfig;
import citibob.hokserver.ConfigApp;
import citibob.io.IOUtils;
import citibob.sql.RemoveSqlCommentsReader;
import citibob.sql.SqlRun;
import citibob.sql.UpdTasklet;
import citibob.sql.pgsql.SqlBool;
import citibob.sql.pgsql.SqlString;
import com.Ostermiller.util.RandPass;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import joptsimple.OptionParser;

/**
 *
 * @author citibob
 */
public class ConfigAdmin {

//File appDir;
ConfigApp app;
File configRoot;		// Root of config file tree
File keyRoot;		// Root of keys file tree
String psqlCmd;		// Command line flags to run psql on our customer database server, logging in as administrator

public ConfigAdmin() throws Exception
//ConfigApp app, File configRoot, File keyRoot, String psqlCmd)
{
//	this.appDir = appDir;
	createApp();
//	this.app = app;
//	this.configRoot = configRoot;
//	this.keyRoot = keyRoot;
//	this.psqlCmd = psqlCmd;
}

void createApp() throws Exception
{
	app = new ConfigApp();
	configRoot = app.configRoot();
	keyRoot = app.keyRoot();
	psqlCmd = app.props().getProperty("psql.cmd", "psql -U postgres");
}



String app_custs_dbname(String appName, String custName)
{
	return custName + "_" + appName;
}
String app_vers_dbname(String appName, String versName)
{
	return appName + "_" + versName;
}


// =========================================================

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
		" CREATE ROLE " + custName + " LOGIN PASSWORD " +
		SqlString.sql(password) + "\n" +
		"    VALID UNTIL 'infinity'\n" +
		"    CONNECTION LIMIT 100;\n";
	psql("template1", sql);
	
	
	// Make sure we changed the password; in case the user was already created.
	sql =
		" ALTER ROLE " + custName + " password " + SqlString.sql(password);
	psql("template1", sql);
	
	return password;
}

boolean createDatabase() {
	return app.props().getProperty("create.database").equalsIgnoreCase("true");
}

void w_custs_add(SqlRun str, String custName)
throws IOException, InterruptedException
{
	// Set up map to do templates in config directory
	Map<String,Object> map = new TreeMap();
	map.put("db.user", custName);

	// Create customer role in database server
	String password = "";
	if (createDatabase()) {
		System.out.println("===== Creating PostgreSQL user " + custName);
		password = createCustRole(custName, null);
	}
	map.put("db.password", password);
	
	// Create config directory
	// ... for custs
	String tableName = "custs";
	File custsDir = new File(configRoot, tableName);
	System.out.println("===== Creating configuration " + custsDir);
	File inDir = new File(app.protoDir(), tableName);
//	File inDir = new File(custsDir, "_proto");
	File outDir = new File(custsDir, custName);
//	if (!outDir.exists()) {
		DirConfig inConfig = new DirConfig(inDir);
		DirConfig outConfig = new DirConfig(outDir);
		ConfigUtils.copy(inConfig, outConfig, map);
//	}
	
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
	System.out.println("===== Deleting keys for " + custName);
	deleteClientKeys(custName);
	
	// Set up record in configuration database
	System.out.println("===== Deleting config database for " + custName);
	str.execSql("select w_custs_del(" + SqlString.sql(custName) + ");");
	
}

void w_apps_addcust(SqlRun str, String appName, String custName, String version)
throws IOException, InterruptedException
{
	// Set up map to do templates in config directory
	Map<String,Object> map = new TreeMap();
	String dbName = app_custs_dbname(appName, custName);
	map.put("db.database", dbName);
	
	// Create config directory
	// ... for custs
	String tableName = "app_custs";
	File custsDir = new File(configRoot, tableName);
	File inDir = new File(app.protoDir(), tableName);
//	File inDir = new File(custsDir, "_proto");
	File outDir = new File(custsDir, dbName);
	if (!outDir.exists()) {
		DirConfig inConfig = new DirConfig(inDir);
		DirConfig outConfig = new DirConfig(outDir);
		ConfigUtils.copy(inConfig, outConfig, map);
	}
	
	// Make the database for this customer
	if (createDatabase()) {
		createClientDb(dbName, custName);	
	}

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
	if (createDatabase()) {
		psql("template1", "drop database " + dbName + ";");
	}
	
	// Update in the config database
	str.execSql("select w_apps_delcust(" +
		SqlString.sql(appName) + ", " +
		SqlString.sql(custName) + ");");	
}
// ------------------------------------------------------
// =================================================================
// Initialization Stuff
/**
 * Make the prototype config directories, if they're not already made.
 * @throws java.io.IOException
 */
public void initProtoDirs()
throws IOException
{
	initProtoDir("app_custs");
	initProtoDir("custs");
}
void initProtoDir(String tableName)
throws IOException
{
	ClassLoader cl = getClass().getClassLoader();
	File file;
	File configDir = new File(app.protoDir(), tableName);
	configDir.mkdirs();
	File appProps = new File(configDir, "app.properties.template");
	
	if (!appProps.exists()) {
		InputStream in = cl.getResourceAsStream(app.name() + "/protos/" + tableName + "/app.properties.template");
		IOUtils.copy(in, appProps);
	}
}
/**
 * Create the database, no schema yet
 */
public void initDatabase()
throws IOException, InterruptedException
{
	String dbDatabase = app.props().getProperty("db.database");
	String dbUser = app.props().getProperty("db.user");
	String password = app.props().getProperty("password");
	
	// Create the user and database if not already created.
	createCustRole(dbUser, password);
	createClientDb(dbDatabase, dbUser);
}
/** Creates the server's public/private keypair */
public void initServerKeys()
throws Exception
{
	File SERVERDIR = new File(keyRoot, "server");
	File SERVERSSLDIR = new File(SERVERDIR, "ssl");
	File CLIENTDIR = new File(keyRoot, "client");
	File SERVERSTORE = new File(SERVERDIR, "server-store.jks");
	String STOREPASS = "keyst0re";
	String KEYPASS = STOREPASS;
	
	SERVERSSLDIR.mkdirs();
	CLIENTDIR.mkdirs();
	SERVERDIR.mkdirs();
	

	// Generate server's public/private key pair
	SERVERSTORE.delete();
	String cmd = "keytool -genkey -validity 36500" +
		" -alias serverprivate" +
		" -keystore " + SERVERSTORE +
		" -keyalg rsa -keysize 2048" +
		" -storepass " + STOREPASS +
		" -keypass " + KEYPASS;
	String input = "\n\nOffstageArts\n\n\nUS\nyes\n";
	exec(cmd, input, null);

	// Self-sign certificate
	cmd = "keytool -export -rfc" +
		" -alias serverprivate" +
		" -keystore " + SERVERSTORE +
		" -file " + SERVERDIR + "/server.cer" +
		" -storepass " + STOREPASS;
	exec(cmd, "", null);


	// ====================================================
	// OpenSSL Conversion

	// Export the private key
	// Usage: DumpKey jks storepass alias keypass out
	File keyFile = new File(SERVERSSLDIR, "server-private-bin.key");
	DumpKey.dumpKey(SERVERSTORE, STOREPASS.toCharArray(),
		"serverprivate", KEYPASS.toCharArray(), keyFile);

	// Convert the key from binary to PEM format
	File PEM = new File(SERVERSSLDIR, "server.pem");
	OutputStream out = new BufferedOutputStream(new FileOutputStream(PEM));
	Writer wout = new OutputStreamWriter(out);
	wout.write("-----BEGIN PRIVATE KEY-----\n");
	wout.flush();
	cmd = "openssl enc -in " +
		keyFile.getPath() + " -a";
	exec(cmd, "", out);
	wout.write("-----END PRIVATE KEY-----\n\n");
	wout.flush();
	
	// Convert server certificate to PEM format (it's already in standard X.509
	// format, just need to re-encode the bytes)
	File cerFile = new File(SERVERDIR, "server.cer");
	cmd = "openssl enc -in " + cerFile.getPath();
	exec(cmd, "", out);

	exec("chmod 600 " + PEM.getPath(), "", null);
	out.close();
}
public void initServer(SqlRun str)
throws Exception
{
	initDatabase();
	initSchema(str);
	initProtoDirs();
	initServerKeys();
}
public void initSchema(SqlRun str)
throws IOException
{
	// Fill in the schema
	ClassLoader cl = getClass().getClassLoader();
	InputStream in0 = cl.getResourceAsStream(app.name() + "/" + app.name() + ".sql");
	Reader in = new RemoveSqlCommentsReader(new InputStreamReader(in0));
	String sql = "BEGIN;\n" + IOUtils.getString(in) + ";\nCOMMIT;\n";
	str.execSql(sql);
}
// =================================================================
// =================================================================
// ========== Generate a launcher
static void exec(File dir, String... cmds) throws IOException, InterruptedException
{
	Process proc = Runtime.getRuntime().exec(cmds, null, dir);
	InputStream in = proc.getInputStream();
	int c;
	while ((c = in.read()) >= 0) System.out.write(c);
	proc.waitFor();
	System.out.println("---> exit value = " + proc.exitValue());
}

public void makeLauncher(SqlRun str,
String appName, String custName,
final String spassword, final File outJar)
throws Exception
{
	final ConfigAndLaunchInfo info = new ConfigAndLaunchInfo(str, app, appName, custName);
	str.execUpdate(new UpdTasklet() {
	public void run() throws Exception {
		makeLauncher(app, info, spassword, outJar);
	}});
}

/**
 * 
 * @param version
 * @param configDir
 * @param outJar
 * @param spassword
 * @throws java.lang.Exception
 */
static void makeLauncher(ConfigApp app,
ConfigAndLaunchInfo info, String spassword,
File outJar)
throws Exception
{
	File inJar = new File(app.props().getProperty("launcher.jar"));
	File tmpDir = new File(app.appDir(), "tmp");
	IOUtils.deleteDir(tmpDir);
	tmpDir.mkdirs();
	
	// Unjar the oalaunch.jar file into the temporary directory
	exec(tmpDir, "jar", "xvf", inJar.getAbsolutePath());
	
	// Merge the existing mutliple dir config into one mem config
	MultiConfig multiConfig = info.newDirConfig(app);
	MemConfig config = new MemConfig();
	WriteableConfig wconfig = (spassword == null ? config :
		new WriteablePBEConfig(config, spassword.toCharArray()));
	for (Iterator<Config> ii = multiConfig.iterator(); ii.hasNext();) {
		Config mc = ii.next();
		ConfigUtils.merge(wconfig, (ListableConfig)mc);
	}

	// Create config.zips
	File configZips = new File(tmpDir, "config.zips");
	OutputStream out = new FileOutputStream(configZips);
	ZipConfig.writeToStream(config, out);
	out.close();
	
	// Create config.properties
	File configProps = new File(tmpDir, "config.properties");
	BufferedWriter writer = new BufferedWriter(new FileWriter(configProps));
	writer.write(
		"app.name=" + info.appName + "\n" +
		"app.url=" + info.appURL + "\n" +
		"config.name=" + multiConfig.getName() + "(" + app.props().getProperty("server.name") + ")\n");
	writer.close();

	// Jar it back up
	exec(tmpDir, "jar", "cvfm", outJar.getAbsolutePath(),
		"META-INF/MANIFEST.MF", ".");
	
	// Sign it
	exec(null, "jarsigner", "-storepass", "keyst0re", outJar.getAbsolutePath(),
			"offstagearts");
	
	// Remove the tmp directory
	IOUtils.deleteDir(tmpDir);
	
	System.out.println("Done writing launcher: " + outJar.getAbsolutePath());
}
// =======================================================================
static class MyOptions extends OptionParser {
public MyOptions() {
	// see: http://jopt-simple.sourceforge.net/examples.html	
//	accepts( "name" ).withRequiredArg().ofType( String.class )
//		.describedAs( "count" );

}}

// ------------------------------------------------------
static final int APP = 0;
static final int CUST = 1;
static final int USER = 2;
static final int APPCUST = 3;
static final int APPVERS = 4;
public void runMain(SqlRun str, String[] args)
throws Exception
//throws ParseException, IOException, InterruptedException
{
	boolean add;	// True for add, false for delete

	initProtoDirs();
	
	// add/del
	String sadd = args[0].toLowerCase();
	if (sadd.equalsIgnoreCase("makelauncher")) {
		System.out.println("Usage: ConfigAdmin makelauncher <app-name> <cust-name> <launcher-jar-file> [password]");
		String appName = args[1];
		String custName = args[2];
		File outJar = new File(args[3]);
		String password = (args.length <= 4 ? null : args[4]);
		
		this.makeLauncher(str, appName, custName, password, outJar);
		
		return;
	}
	if (sadd.equalsIgnoreCase("add")) add = true;
	else if (sadd.equalsIgnoreCase("del")) add = false;
	else throw new IllegalArgumentException("args[0] must be \"add\" or \"del\"");
	
	// Set up legal table names
	
	TreeMap<String,Integer> tabs = new TreeMap();
	tabs.put("app", APP);
	tabs.put("cust", CUST);
	tabs.put("user", USER);
	tabs.put("appcust", APPCUST);
	tabs.put("appvers", APPVERS);
	
	// Get table name
	String stab = args[1];
	Integer itab = tabs.get(stab);
	if (itab == null) throw new IllegalArgumentException("Unknown table name in args[1]: " + stab);
	String usage = "Usage: ConfigAdmin <add|del|makelauncher> ";
	switch(itab.intValue()) {
		case APP : {	// app
			System.out.println(usage + "app <app-name>");
			String appName = args[2];
			str.execSql("select w_apps_" + sadd + "(" +
				SqlString.sql(appName) + ");");	
		} break;
		case CUST : {	// cust
			System.out.println(usage + "cust <customer-name>");
			String custName = args[2];
			if (add) {
				w_custs_add(str, custName);
			} else {
				w_custs_del(str, custName);
			}
		} break;
		case USER : {
			System.out.println(usage + "user <app-name> <customer-name> <user-name> (password)");
			String appName = args[2];
			String custName = args[3];
			String userName = args[4];
	
			if (add) {
				String password = args[5];
				str.execSql("select w_users_add(" +
					SqlString.sql(appName) + ", " +
					SqlString.sql(custName) + ", " +
					SqlString.sql(userName) + ", " +
					SqlString.sql(password) + ")");
			} else {
				str.execSql("select w_users_del(" +
					SqlString.sql(appName) + ", " +
					SqlString.sql(custName) + ", " +
					SqlString.sql(userName) + ")");
			}
		} break;
		case APPCUST : {
			System.out.println(usage + "appcust <app-name> <customer-name> [version]");
			String appName = args[2];
			String custName = args[3];
			if (add) {
				String version = (args.length >= 5 ? args[4] : null);
				w_apps_addcust(str, appName, custName, version);
			} else {
				w_apps_delcust(str, appName, custName);
			}
		} break;
		case APPVERS : {
			System.out.println(usage + "appvers <app-name> <version> (url) (default?)");
			String appName = args[2];
			String version = args[3];
			if (add) {
				String url = args[4];
				boolean isDefault = Boolean.parseBoolean(args[5]);
				str.execSql("select w_apps_add(" +
					SqlString.sql(appName) + ");");	
				str.execSql("select w_apps_addvers(" +
					SqlString.sql(appName) + ", " +
					SqlString.sql(version) + ", " +
					SqlString.sql(url) + ", " +
					SqlBool.sql(isDefault) + ")");
			} else {
				str.execSql("select w_apps_delvers(" +
					SqlString.sql(appName) + ", " +
					SqlString.sql(version) + ")");
			}
		} break;
	}
}
// ------------------------------------------------------
public static void main(String[] args) throws Exception
{
	// Set up connection to config database
//	String sHomeDir = System.getProperty("user.home");
//	File appDir = new File(sHomeDir, ".hokserver");
	
//args = new String[] {"makelauncher", "offstagearts",
//"ballettheatre", "xxx.jar", "tiger"};
	
	ConfigAdmin ca = new ConfigAdmin();
	SqlRun str = ca.app.sqlRun();
	
	ca.runMain(str, args);

// Not yet in cmd line...
//	ca.initServer(str);
//	ConfigDb.uploadAllConfigs(str, ca.app.configRoot(), null);
//	ca.makeLauncher(str, ca.app, "offstagearts", "ballettheatre",
//		null, new File("bt.jar"));
	
	
	str.flush();
}
}
