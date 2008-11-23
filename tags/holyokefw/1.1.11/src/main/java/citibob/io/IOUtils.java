/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author citibob
 */
public class IOUtils {

public static String readerToString(Reader reader) throws IOException
{
	char[] buf = new char[2048];
	StringBuffer sb = new StringBuffer();
	int n;
	while ((n = reader.read(buf)) > 0) {
		sb.append(buf, 0, n);
	}
	reader.close();
	return sb.toString();
}
}