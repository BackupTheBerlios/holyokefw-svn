/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.hokserver.admin;

import citibob.config.Config;
import citibob.io.IOUtils;
import citibob.io.sslrelay.SSLRelayClient;
import citibob.sql.ConfigConnFactory;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

/**
 *
 * @author citibob2
 */
public class ExportToPem {




/** @param */
public static void jksCertificateToPem(
File jksFile, char[] jksPass,
String keyName, char[] keyPass, OutputStream pemOut)
throws Exception
{
	// Self-sign certificate (or export the certificate)
	File cerFile = File.createTempFile("dumpkey", ".cer");
	String cmd = "keytool -export -rfc" +
		" -alias " + keyName +
		" -keystore " + jksFile +
		" -file " + cerFile +
		" -storepass " + new String(jksPass);
	ConfigAdmin.exec(cmd, "", null);

	// Convert server certificate to PEM format (it's already in standard X.509
	// format, just need to re-encode the bytes)
	cmd = "openssl enc -in " + cerFile.getPath();
	ConfigAdmin.exec(cmd, "", pemOut);
	cerFile.delete();
}

/** @param */
public static void jksPrivateKeyToPem(
File jksFile, char[] jksPass,
String keyName, char[] keyPass, OutputStream pemOut)
throws Exception
{
//	OutputStream pemOut = new BufferedOutputStream(new FileOutputStream(pemFile));


	// -------------- Export the private key
	// Usage: DumpKey jks storepass alias keypass out
//	File keyFile = new File(SERVERSSLDIR, "server-private-bin.key");
	File keyFile = File.createTempFile("dumpkey-private", "-bin.key");
	DumpKey.dumpKey(jksFile, jksPass, keyName, keyPass, keyFile);

	// Convert the key from binary to PEM format
	Writer wout = new OutputStreamWriter(pemOut);
	wout.write("-----BEGIN PRIVATE KEY-----\n");
	wout.flush();
	String cmd = "openssl enc -in " +
		keyFile.getPath() + " -a";
	ConfigAdmin.exec(cmd, "", pemOut);
	wout.write("-----END PRIVATE KEY-----\n\n");
	wout.flush();

	keyFile.delete();


	// ----------- Export the certificate
	jksCertificateToPem(jksFile, jksPass, keyName, keyPass, pemOut);



//	pemOut.close();
}





static void exportToPem(Config config) throws Exception
{
		// Start the port forwarding
	Properties props = config.loadAppProperties();
	SSLRelayClient.Params prm = ConfigConnFactory.newtSSLRelayClientParams(config, props);
	String sslUser =
		props.getProperty("ssl.user",
		props.getProperty("db.user"));

	// ============ Convert our keystore (public/private key pair) to PEM format
	String STOREPASS = "keyst0re";
	String KEYPASS = STOREPASS;

	// -------- client-store.jks
	// Create .jks file
	File storeFile = File.createTempFile("client-store", ".jks");
	storeFile.deleteOnExit();
	IOUtils.copy(prm.storeBytes, storeFile);

	// Convert it to PEM
	File storePem = new File("client-store.pem");
	OutputStream pemOut = new FileOutputStream(storePem);
	jksPrivateKeyToPem(storeFile, STOREPASS.toCharArray(),
		sslUser + "-private", KEYPASS.toCharArray(), pemOut);
	pemOut.close();

	// -------- client-trust.jks
	// Create .jks file
	File trustFile = File.createTempFile("client-trust", ".jks");
	storeFile.deleteOnExit();
	IOUtils.copy(prm.trustBytes, trustFile);

	// Convert it to PEM
	File trustPem = new File("client-trust.pem");
	pemOut = new FileOutputStream(trustPem);
	jksCertificateToPem(trustFile, STOREPASS.toCharArray(),
		"server-cert", KEYPASS.toCharArray(), pemOut);
	pemOut.close();

}


}
