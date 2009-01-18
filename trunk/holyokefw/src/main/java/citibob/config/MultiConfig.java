/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Stores a set of StreamSets, in order of decreasing priority.
 * @author citibob
 */
public class MultiConfig extends BaseConfig
{

ArrayList<Config> configs;

public MultiConfig()
{
	super(null);
	configs = new ArrayList();
}

public MultiConfig(Config... configs)
{
	this();
	for (Config config : configs) add(config);
}
public void add(Config config)
	{ configs.add(config); }
public Config get(int i)
	{ return configs.get(i); }
public int size() { return configs.size(); }

/** Opens the named stream from the first (highest priority) StreamSet that has it. */
public InputStream openStream(String name) throws IOException
{
	for (int i=0; i<size(); ++i) {
		InputStream in = get(i).openStream(name);
		if (in != null) return in;
	}
	return null;
}

/** Gets bytes from the named stream from the first (highest priority) StreamSet that has it. */
public byte[] getStreamBytes(String name) throws IOException
{
	for (int i=0; i<size(); ++i) {
		byte[] bytes = get(i).getStreamBytes(name);
		if (bytes != null) return bytes;
	}
	return null;	
}

/** Retrieves a composite properties file from the Config. */
public boolean loadProperties(String name, Properties props)
throws IOException
{
	int nloaded = 0;
	for (int i=size()-1; i >= 0; --i) {
		Config sset = get(i);
		
		// Load properties file
		Properties nprops = new Properties();
		if (sset.loadProperties(name, nprops)) ++nloaded;
			
		// Copy to our master properties
		for (Entry en : nprops.entrySet()) {
			String key = (String)en.getKey();
			String val = (String)en.getValue();
			props.setProperty(key, val);
		}
	}
	return (nloaded > 0);
}


public static MultiConfig merge(MultiConfig... configs)
{
	MultiConfig ret = new MultiConfig();
	ret.name = configs[0].getName();
	for (MultiConfig config : configs) {
		for (Config c : config.configs) ret.add(c);
//		ret.addAll(config);
	}
	return ret;
}

// =====================================================
/** Read configurations from config server */
public static MultiConfig loadFromStream(InputStream in)
throws IOException
{
	MultiConfig config = new MultiConfig();
	
	// Load the configurations over the port
	for (;;) {
		try {
			Config sset = ZipConfig.loadFromStream(in);
			if (sset == null) break;
			config.add(sset);
		} catch(EOFException e) {
			break;
		}
	}
	in.close();
	
	config.setNameFromFirst();
	return config;
}

void setNameFromFirst()
throws IOException
{
	// Determine the Config's name by parsing from the first StreamSet
	Config sset = get(0);
	InputStream xin = sset.openStream("app.properties");
	if (xin != null) {
		Properties props = new Properties();
		props.load(xin);
		xin.close();
		name = props.getProperty("config.name");
	}
}

/** Read configurations from launcher on local machine */
public static MultiConfig loadFromLauncher(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

/** Read configurations from config server (via SSL Socket) */
public static MultiConfig loadFromConfigServer(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

public static MultiConfig loadFromRawConfig(RawConfigChain rconfig)
throws IOException
{
	MultiConfig config = new MultiConfig();
	for (byte[] bytes : rconfig) {
		Config sset = ZipConfig.loadFromStream(new ByteArrayInputStream(bytes));
		config.add(sset);
	}
	return config;
}

}
