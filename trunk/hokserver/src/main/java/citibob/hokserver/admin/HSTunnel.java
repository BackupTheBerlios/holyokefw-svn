/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.Config;
import citibob.config.ConfigMaker;
import citibob.config.MultiConfigMaker;
import citibob.config.PBEConfig;
import citibob.crypt.DialogPBEAuth;
import citibob.io.sslrelay.InOutRelay;
import citibob.io.sslrelay.SSLRelayClient;
import citibob.sql.ConfigConnFactory;
import citibob.task.SimpleExpHandler;
import java.io.File;
import java.util.Properties;

/**
 *
 * @author citibob
 */
public class HSTunnel {

public static void main(String[] args) throws Exception
{
	// Load the specified configuration
	File fconfig = new File(args[0]);
	ConfigMaker cmaker = new MultiConfigMaker(new Object[]{fconfig});
	Config config = cmaker.newConfig(null);
	config = new PBEConfig(config, new DialogPBEAuth(null, "Please enter configuration password."));

	// Start the port forwarding
	Properties props = config.loadAppProperties();
	SSLRelayClient.Params prm = ConfigConnFactory.newtSSLRelayClientParams(config, props);
	SSLRelayClient relay = new SSLRelayClient(prm, new SimpleExpHandler(System.err, true));
	relay.startRelays();

	// Look up database connection details
	String user = props.getProperty("db.user", null);
	String password = props.getProperty("db.password", null);
	int port = relay.getLocalPort();
	String database = props.getProperty("db.database", null);

	System.out.println(
		"port = " + port + "\n" +
		"database = " + database + "\n" +
		"user = " + user + "\n" +
		"password = " + password);


	// Forward to psql process
	String[] cmd = new String[] {"psql",
		"-h", "127.0.0.1",
		"-p", (""+port),
		"-U", user,
		database};
	String[] env = new String[] {
		"PGPASSWORD", password};
	Process proc = Runtime.getRuntime().exec(cmd, env);

	InOutRelay.Listener listener = new InOutRelay.Listener() {
	public void onConnectionClosed(Exception e) {
		// Exit when our connection to psql is broken
		System.exit(-1);
	}};

	}
	Thread t1 = new InOutRelay(
		System.in, proc.getOutputStream(), null);
	Thread t2 = new InOutRelay(
		proc.getInputStream(), System.out, null);

	try {
		t1.wait();
	} catch(InterruptedException ie) {}

}
}
