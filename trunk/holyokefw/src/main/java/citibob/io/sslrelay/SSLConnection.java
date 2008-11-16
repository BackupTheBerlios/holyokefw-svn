package citibob.io.sslrelay;

/** ********************************************************************
Disclaimer
This example code is provided "AS IS" without warranties of any kind.
Use it at your Risk!

SSLConnection class that will holds the common traits for both the 
client and the server relay. The client and server proxy will inherit from this 
class 

Chianglin Jan 2003

************************************************************ */


import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.security.cert.CertificateException;



public class SSLConnection {


private SSLContext ctx ;
private KeyStore mykey , mytrust ;
private URL key, trust ;


//Default constructor takes the filename of the keystore and truststore , 
//the password of the stores and the password of the private key
public SSLConnection(URL key , URL trust , char[] storepass, char[]
storeKeyPass, char[] trustPass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException, UnrecoverableKeyException, KeyManagementException
{
	this.key = key;
	this.trust = trust ;
	initSSLContext(storepass , storeKeyPass, trustPass);
}




/* mykey holding my own certificate and private key, mytrust holding all the 
certificates that I trust */
public void initKeyStores(URL key , URL trust , char[] storepass, char[] trustpass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException
{
 	//get instances of the Sun JKS keystore
 	mykey = KeyStore.getInstance("JKS" , "SUN");
	mytrust = KeyStore.getInstance("JKS", "SUN");

	//load the keystores
	InputStream storeStream = null;
	InputStream trustStream = null;
	try {
		storeStream = key.openStream();
		trustStream = trust.openStream();
		mykey.load(storeStream, storepass);
		mytrust.load(trustStream, trustpass );
	} finally {
		storeStream.close();
		trustStream.close();
	}
}



public void initSSLContext(char[] storePass, char[] storeKeyPass , char[] trustPass)
throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
CertificateException, IOException, UnrecoverableKeyException, KeyManagementException
{ 
    ctx = SSLContext.getInstance("TLSv1" , "SunJSSE");
    initKeyStores(key , trust , storePass, trustPass);
    //Create the key and trust manager factories for handing the cerficates 
    //in the key and trust stores
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509" , 
    "SunJSSE");
    tmf.init(mytrust);
    
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509" , 
    "SunJSSE");
    kmf.init(mykey , storeKeyPass);
    
    ctx.init(kmf.getKeyManagers() , tmf.getTrustManagers() ,null) ;

}

public SSLContext getMySSLContext() {
return ctx ;
}

}
