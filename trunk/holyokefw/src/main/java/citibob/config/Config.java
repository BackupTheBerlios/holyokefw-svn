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
public class Config extends ArrayList<StreamSet>
{

String name;


public void setName(String name) { this.name = name; }

/** Descriptive name of this configuration */
public String getName() { return name; }
	
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
public Properties getProperties(String name)
throws IOException
{
	Properties props = new Properties();
	for (int i=size()-1; i >= 0; --i) {
		StreamSet sset = get(i);
		InputStream xin = sset.openStream(name);
		if (xin != null) {
			// Load properties file
			Properties nprops = new Properties();
			nprops.load(xin);
			xin.close();
			
			// Copy to our master properties
			for (Entry en : nprops.entrySet()) {
				String key = (String)en.getKey();
				String val = (String)en.getValue();
				props.setProperty(key, val);
			}
		}
	}
	return props;
}


public static Config merge(Config... configs)
{
	Config ret = new Config();
	ret.setName(configs[0].getName());
	for (Config config : configs) {
		ret.addAll(config);
	}
	return ret;
}

// =====================================================
/** Read configurations from config server */
public static Config loadFromStream(InputStream in)
throws IOException
{
	Config config = new Config();
	
	// Load the configurations over the port
	for (;;) {
		try {
			StreamSet sset = ZipStreamSet.loadFromStream(in);
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
	StreamSet sset = get(0);
	InputStream xin = sset.openStream("app.properties");
	if (xin != null) {
		Properties props = new Properties();
		props.load(xin);
		xin.close();
		setName(props.getProperty("config.name"));
	}
}

/** Read configurations from launcher on local machine */
public static Config loadFromLauncher(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

/** Read configurations from config server (via SSL Socket) */
public static Config loadFromConfigServer(InetAddress host, int port)
throws UnknownHostException, IOException
{
	if (host == null) host = InetAddress.getLocalHost();
	Socket sock = new Socket(host, port);
	InputStream in = sock.getInputStream();
	return loadFromStream(in);
}

public static Config loadFromRawConfig(RawConfig rconfig)
throws IOException
{
	Config config = new Config();
	for (byte[] bytes : rconfig) {
		StreamSet sset = ZipStreamSet.loadFromStream(new ByteArrayInputStream(bytes));
		config.add(sset);
	}
	return config;
}


}
