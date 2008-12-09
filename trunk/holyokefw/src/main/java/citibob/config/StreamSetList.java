/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Stores a set of StreamSets, in order of decreasing priority.
 * @author citibob
 */
public class StreamSetList extends ArrayList<StreamSet>
{

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
}
