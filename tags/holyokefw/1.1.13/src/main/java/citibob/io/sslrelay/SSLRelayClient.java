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


import citibob.task.ExpHandler;
import citibob.task.SimpleExpHandler;
import java.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.sql.Connection;


public class SSLRelayClient
extends SSLConnection
{
	
private SSLSocket ss;
private ServerSocket locals;
private DataInputStream in , secureIn ;
private DataOutputStream out, secureOut ;
private InetAddress dest;
private int destPort;

MyRelay ApptoProxy;
MyRelay ProxytoApp;

Thread thread;
ExpHandler expHandler;

Connection dbb;
boolean calledConnectionClosed = false;

public Connection getDbb() { return dbb; }
public void setDbb(Connection dbb) { this.dbb = dbb; }

public static class Params
{
	public byte[] storeBytes;		// JKS file for our public/private keypair
	public byte[] trustBytes;		// JKS file for server certificate(s)
	public char[] storePass;		// Our public & private key pair
	public char[] storeKeyPass;	// Password to our private key in clientStorePass
	public char[] trustPass;		// Certificate of server we connect to
	public InetAddress dest;		// Server we're tunneling to
	public int destPort;			// Port on server to connect to
	public int localPort = 0;		// Port we'll present as server; 0 means choose a free port
}
	
public int getLocalPort() { return locals.getLocalPort(); }

//default constructor
public SSLRelayClient(Params prm, ExpHandler expHandler)
throws Exception
{
	super(prm.storeBytes, prm.trustBytes,
		prm.storePass, prm.storeKeyPass, prm.trustPass);
//	System.out.println("Starting relayapp ...");
	this.dest = prm.dest;
	this.destPort = prm.destPort;
	locals = new ServerSocket(prm.localPort);
	this.expHandler = expHandler;
//	startListen();
}


//creates the secured SSL link
public void initSecuredConnection(InetAddress dest , int destport)
throws Exception
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

public void startRelays()
throws Exception
{
	initSecuredConnection(dest, destPort);   
	thread = new Thread() {
	public void run() {
		if (this.isInterrupted()) {
			callConnectionClosed();
			return;
		}
		try {
			// "Server" listens for JUST ONE socket connection
			// from JDBC, then exits.
			Socket sock=locals.accept();

			in = new DataInputStream (
				new BufferedInputStream(	// Not sure this should be buffered...
				sock.getInputStream() ));
			out = new DataOutputStream(
				new BufferedOutputStream(
				sock.getOutputStream() ));
		} catch(Exception e) {
			if (expHandler == null) e.printStackTrace();
			else expHandler.consume(e);
		}

		ApptoProxy = new MyRelay(in ,secureOut, "ApptoSecureout");
		ProxytoApp = new MyRelay(secureIn , out, "SecureintoApp"  ); 
//RelayIntoOut needs to be an inner class.
//And it needs to call connectionClosed() as appropriate.
//And since there are TWO threads here, it needs to
//arbitrate which one "gets" to call connectionClosed().
//		connectionClosed();
	}};
	thread.start();
}

/** Override this for a callback */
protected void connectionClosed() {}

synchronized void callConnectionClosed() {
	if (!calledConnectionClosed) {
		calledConnectionClosed = true;	// Prevent infinitie recursion
		connectionClosed();
	}
}

public void stopRelays()
{
	ApptoProxy.closeall();
	ProxytoApp.closeall();
	thread.interrupt();		// This will be ineffective...
	callConnectionClosed();	// This is redundant
}

//public static void main(String[] args) throws Exception {     
//	SSLRelayClient.Params prm = new SSLRelayClient.Params();
//		prm.storeURL = new File("/Users/citibob/mvn/oassl/clienttruststore").toURL();
//		prm.trustURL = prm.storeURL;
//		prm.storePass = "oaclient7".toCharArray();
//		prm.trustPass = prm.storePass;
//		prm.dest = InetAddress.getByAddress(new byte[] {127,0,0,1});
//		prm.destPort = 5433;
//		prm.localPort = 4001;	// Set this to -1
//	new SSLRelayClient(prm, new SimpleExpHandler());
////	new SSLRelayClient(key, trust, clientStorepassword.toCharArray(), clientStorepassword.toCharArray(),
////		"127.0.0.1", 5433, 4001);
//}


// ===============================================================
class MyRelay extends Thread {


	private DataInputStream in ;
	private DataOutputStream out ;
	private String name;

	public MyRelay ( DataInputStream in,
	DataOutputStream out , String name) 
	{
		super(name);
		this.name = name;
		this.in = in;
		this.out = out ;
		setDaemon(true);
		this.start();
	}

	public MyRelay ( DataInputStream in, DataOutputStream out) 
	{
		this.name = getName();
		this.in = in;
		this.out = out ;
		setDaemon(true);
		this.start();
	}



	public void run(){

		int size ;
		byte[] buffer = new byte[8192];

		try {
			while(true) {
				size = in.read(buffer);
				if(size > 0 ) {
					System.out.println(name + " receive from in connection" + size);
					out.write(buffer,0, size);
					out.flush();
					System.out.println(name  + " finish forwarding to out connection");    	       
				} else if (size == -1) { //end of stream 
					System.out.println(name + " EOF detected!");
					out.close();
					return ;
				}	    
			}
		}
		catch(Exception e){
			e.printStackTrace();
			//	       System.err.println(name + e);
			closeall();
		} 


	}

	/** Causes the thread to stop! */
	public void closeall(){
		try{
			if(in != null ) in.close();
			if(out != null) out.close();
			callConnectionClosed();
		} catch(IOException e){
			e.printStackTrace();
	//		    System.err.println(e);
		}
	}
}

}//end of class

