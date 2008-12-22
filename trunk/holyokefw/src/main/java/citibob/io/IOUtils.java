/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public static final FileFilter trueFilter = new FileFilter() {
public boolean accept(File arg0) { return true; }};

public static final FileFilter falseFilter = new FileFilter() {
public boolean accept(File arg0) { return false; }};

	
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
public static void copy(File fin, File fout) throws IOException
{
	InputStream in = new FileInputStream(fin);
	OutputStream out = new FileOutputStream(fout);
	copy(in, out);
	in.close();
	out.close();
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
// ---------------------------------------------------------------
/** Strips baseDir off a pathname, leaving a relative pathname.  f must
 be a descendant of baseDir*/
public static String getRelative(File baseDir, File f)
{
	String rel = f.getPath().substring(baseDir.getPath().length()+1);
	return rel.replace(File.separatorChar, '/');
}

// http://www.exampledepot.com/egs/java.io/DeleteDir.html
// Deletes all files and subdirectories under dir.
// Returns true if all deletions were successful.
// If a deletion fails, the method stops attempting to delete and returns false.
public static boolean deleteDir(File dir) {
	if (dir.isDirectory()) {
		String[] children = dir.list();
		for (int i=0; i<children.length; i++) {
			boolean success = deleteDir(new File(dir, children[i]));
			if (!success) {
				return false;
			}
		}
	}

	// The directory is now empty so delete it
	return dir.delete();
}

}