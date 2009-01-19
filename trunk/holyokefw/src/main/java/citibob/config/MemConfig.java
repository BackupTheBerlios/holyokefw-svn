/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author citibob
 */
public class MemConfig extends BaseConfig
{
	
Map<String,byte[]> streams = new TreeMap();

public MemConfig() {
	super(null);
}

/** Stores a StreamSet in memory... */
public InputStream openStream(String name) throws IOException
{
	byte[] bytes = streams.get(name);
	if (bytes == null) return null;
	return new ByteArrayInputStream(bytes);
}

public byte[] getStreamBytes(String name) throws IOException
{
	return streams.get(name);
}

public int size() { return streams.size(); }

}