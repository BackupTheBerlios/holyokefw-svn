/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

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

public static byte[] getBytes(InputStream in) throws IOException
{
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	copy(in, bout);
	return bout.toByteArray();
}

public static void copy(InputStream in, OutputStream out) throws IOException
{
	byte[] buf = new byte[2048];
	int n;
	while ((n = in.read(buf)) > 0) {
		out.write(buf,0,n);
	}
	in.close();
}
// ---------------------------------------------------------------
public static String getString(Reader in) throws IOException
{
	StringWriter bout = new StringWriter();
	copy(in, bout);
	return bout.toString();
}
public static void copy(Reader in, Writer out) throws IOException
{
	char[] buf = new char[2048];
	int n;
	while ((n = in.read(buf)) > 0) {
		out.write(buf,0,n);
	}
	in.close();
}


}