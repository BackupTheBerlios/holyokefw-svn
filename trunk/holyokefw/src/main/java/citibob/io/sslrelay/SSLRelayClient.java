package citibob.io.sslrelay;

/**  ******************************************************
Disclaimer
This example code is provided "AS IS" without warranties of any kind.
Use it at your Risk!

Our relay client relay application that will accept the normal 
socket connection from a client app and forward these to our secured SSL 
connection 

Chianglin Jan 2003 

******************************************************************* */


import java.net.*;
import javax.net.ssl.*;
import java.io.*;


public class SSLRelayClient
extends SSLConnection
{
	
private SSLSocket ss;
private ServerSocket locals;
private DataInputStream in , secureIn ;
private DataOutputStream out, secureOut ;
private InetAddress dest;
private int destPort;

RelayIntoOut ApptoProxy;
RelayIntoOut ProxytoApp;

Thread thread;


public static class Params
{
	public URL storeURL;		// JKS file for our public/private keypair
	public URL trustURL;		// JKS file for server certificate(s)
	public char[] storePass;		// Our public & private key pair
	public char[] storeKeyPass;	// Password to our private key in clientStorePass
	public char[] trustPass;		// Certificate of server we connect to
	public InetAddress dest;		// Server we're tunneling to
	public int destPort;			// Port on server to connect to
	public int localPort = -1;		// Port we'll conn-1 means choose a free port
}
	
public int getLocalPort() { return locals.getLocalPort(); }

//default constructor
public SSLRelayClient(Params prm)
throws Exception
{
	super(prm.storeURL, prm.trustURL,
		prm.storePass, prm.storeKeyPass, prm.trustPass);
	System.out.println("Starting relayapp ...");
	this.dest = prm.dest;
	this.destPort = prm.destPort;
	initLocalConnection(prm.localPort);
//	startListen();
}


//creates the secured SSL link
public void initSecuredConnection(InetAddress dest , int destport) throws Exception
{

	//get the Socketfactory from the SSLContext 	   
	SSLSocketFactory sf = getMySSLContext().getSocketFactory();
	ss = (SSLSocket)sf.createSocket(dest , destport );
	ss.startHandshake(); //begin handshake

	SSLSession current = ss.getSession();

	System.out.println("Cipher suite in use is " + current.getCipherSuite());
	System.out.println("Protocol is " + current.getProtocol());

	//get the input and output streams from the SSL connection
	secureIn = new DataInputStream( 
		new BufferedInputStream(		// Not sure we should be buffering...
		ss.getInputStream()));
	secureOut = new DataOutputStream(
		new BufferedOutputStream(
		ss.getOutputStream()));

	System.out.println("Got remote secured connection");
}

public void initLocalConnection(int localPort)
throws IOException
{
	if (localPort < 0) {
		locals = new ServerSocket();
	} else {
		locals = new ServerSocket(localPort);
	}
}


public void startRelays()
throws Exception
{
	Socket sock=locals.accept();

	initSecuredConnection(dest, destPort);   
	in = new DataInputStream (
		new BufferedInputStream(	// Not sure this should be buffered...
		sock.getInputStream() ));
	out = new DataOutputStream(
		new BufferedOutputStream(
		sock.getOutputStream() ));

	ApptoProxy = new RelayIntoOut(in ,secureOut, "ApptoSecureout");
	ProxytoApp = new RelayIntoOut(secureIn , out, "SecureintoApp"  );   
}

public void stopRelays()
{
	ApptoProxy.closeall();
	ProxytoApp.closeall();
}

public static void main(String[] args) throws Exception {     
	SSLRelayClient.Params prm = new SSLRelayClient.Params();
		prm.storeURL = new File("/Users/citibob/mvn/oassl/clienttruststore").toURL();
		prm.trustURL = prm.storeURL;
		prm.storePass = "oaclient7".toCharArray();
		prm.trustPass = prm.storePass;
		prm.dest = InetAddress.getByAddress(new byte[] {127,0,0,1});
		prm.destPort = 5433;
		prm.localPort = 4001;	// Set this to -1
	new SSLRelayClient(prm);
//	new SSLRelayClient(key, trust, clientStorepassword.toCharArray(), clientStorepassword.toCharArray(),
//		"127.0.0.1", 5433, 4001);
}


}//end of class

