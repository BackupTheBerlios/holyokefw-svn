/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.Config;
import citibob.config.ConfigMaker;
import citibob.config.MultiConfigMaker;
import citibob.config.PBEConfig;
import citibob.io.sslrelay.InOutRelay;
import citibob.io.sslrelay.SSLRelayClient;
import citibob.sql.ConfigConnFactory;
import citibob.task.SimpleExpHandler;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;
import citibob.crypt.DialogPBEAuth;

/**
 *
 * @author citibob
 */
public class HSTunnel {

private int port;
private String user;
private String database;
private String password;

	public String getDatabase() {
		return database;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}



public static Config newConfig(File launcherFile)
throws IOException
{
	// Load the specified configuration
	ConfigMaker cmaker = new MultiConfigMaker(new Object[]{launcherFile});
	Config config = cmaker.newConfig(null);
//	config = new PBEConfig(config, new DialogPBEAuth(null, "Please enter configuration password."));
	String envName = "HOK_PASSWORD";
	citibob.crypt.PBEAuth auth = System.console() != null ?
		new ConsolePBEAuth(envName) :
		new DialogPBEAuth(null, "Please enter launcher password", envName);
	config = new PBEConfig(config, auth);

	return config;
}


public HSTunnel(File launcherFile) throws Exception
{
	Config config = newConfig(launcherFile);

	// Start the port forwarding
	Properties props = config.loadAppProperties();


props.list(System.out);
	SSLRelayClient.Params prm = ConfigConnFactory.newtSSLRelayClientParams(config, props);
	SSLRelayClient relay = new SSLRelayClient(prm, new SimpleExpHandler(System.err, false));
	relay.startRelays();

	// Look up database connection details
	user = props.getProperty("db.user", null);
	password = props.getProperty("db.password", null);
	port = relay.getLocalPort();
	database = props.getProperty("db.database", null);

}

static Process exec(String[] cmd, String[] env,
String istreamName, String ostreamName, String estreamName) throws IOException
{
	InputStream istream = openIstream(istreamName, System.in);
	OutputStream ostream = openOstream(ostreamName, System.out);
	OutputStream estream = openOstream(estreamName, System.err);
	return exec(cmd, env, istream, ostream, estream);
}
/** Starts a process running, exits the JVM when any of the process
 I/O streams end. */
static Process exec(String[] cmd, String[] env,
final InputStream in, final OutputStream out, final OutputStream err) throws IOException
{
	// Print the command we're running
	for (String s : cmd) System.err.print(s + " ");
	System.err.println();

	Process proc = Runtime.getRuntime().exec(cmd, env);


	InOutRelay.Listener inListener = new InOutRelay.Listener() {
	public void onConnectionClosed(Exception e) {
		try {
			out.write('\04');
			out.flush();
		} catch(IOException e2) {}
	}};
	Thread t1 = new InOutRelay(
		in, proc.getOutputStream(), inListener);


	InOutRelay.Listener listener = new InOutRelay.Listener() {
	public void onConnectionClosed(Exception e) {
		// Exit when our connection to psql is broken
		System.exit(0);
	}};

	Thread t2 = new InOutRelay(
		proc.getInputStream(), out, listener);
	Thread t3 = new InOutRelay(
		proc.getErrorStream(), err, listener);

	return proc;
}
static OutputStream openOstream(String oname, OutputStream deflt) throws IOException
{
	if (oname == null) return deflt;
	OutputStream ostream = new BufferedOutputStream(
		new FileOutputStream(new File(oname)));
	if (oname.endsWith(".gz")) ostream = new GZIPOutputStream(ostream);
	return ostream;
}
static InputStream openIstream(String iname, InputStream deflt) throws IOException
{
	if (iname == null) return deflt;
	InputStream istream = new BufferedInputStream(
		new FileInputStream(new File(iname)));
	if (iname.endsWith(".gz")) istream = new java.util.zip.GZIPInputStream(istream);
	return istream;
}


}
