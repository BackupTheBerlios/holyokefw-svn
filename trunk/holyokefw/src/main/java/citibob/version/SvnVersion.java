/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads the output of the svnversion command
 * @author citibob
 */
public class SvnVersion {

public int minVersion;
public int maxVersion;
public char modifier = '\0';

public SvnVersion(InputStream xin) throws IOException
{
	BufferedReader in = new BufferedReader(new InputStreamReader(xin));
	String line = in.readLine();
	in.close();
	
	// ================= Parse it
	
	// Get the trailing modifier
	char last = line.charAt(line.length() - 1);
	if (!Character.isDigit(last)) {
		modifier = last;
		line = line.substring(0, line.length() - 1);
	}
	
	// Get min version and max version
	int colon = line.indexOf(':');
	if (colon < 0) {
		maxVersion = Integer.parseInt(line);
		minVersion = maxVersion;
	} else {
		minVersion = Integer.parseInt(line.substring(0,colon));
		maxVersion = Integer.parseInt(line.substring(colon+1));
	}
}
}
