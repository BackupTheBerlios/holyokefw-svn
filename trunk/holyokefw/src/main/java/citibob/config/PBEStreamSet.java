/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author citibob
 */
public class PBEStreamSet implements StreamSet
{
StreamSet sub;
PBEAuthenticator auth;

public PBEStreamSet(StreamSet sub, PBEAuthenticator auth)
{
	
}

public InputStream openStream(String name) throws IOException
{
	BufferedReader in = new BufferedReader(new InputStreamReader(sub.openStream(name)));
	
	
	
	throw new UnsupportedOperationException("Not supported yet.");
}

}
