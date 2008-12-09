/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.crypt.PBECrypt;
import citibob.io.IOUtils;
import citibob.reflect.ClassPathUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class StreamSetWriter {
	
char[] password;


static FileFilter trueFilter = new FileFilter() {
public boolean accept(File arg0) { return true; }};

static FileFilter falseFilter = new FileFilter() {
public boolean accept(File arg0) { return false; }};


public StreamSetWriter(char[] password)
{
	this.password = password;
}

// ----------------------------------------------------------------------	
public void finish() throws IOException
{}
public void close() throws IOException
{}
// ---------------------------------------------------------------------
abstract void writeSimpleEntry(String name, byte[] bytes)
throws IOException;

void writeEntry(String name, byte[] clearBytes, boolean encrypt)
throws IOException
{
	byte[] bytes = null;
	
	// Copy the actual file
	if (encrypt) {
		// Copy encrypted
		try {
			name = name + ".crypt";
			String cipherText = PBECrypt.encrypt(clearBytes, password);
			bytes = cipherText.getBytes();
		} catch(Exception e) {
			IOException ioe = new IOException(e.getMessage());
			ioe.initCause(e);
			throw ioe;
		}
	}
	writeSimpleEntry(name, bytes);
}

void writeEntry(String name, InputStream in, boolean encrypt)
throws IOException
{
	byte[] clearBytes = IOUtils.getBytes(in);
	in.close();
	
	writeEntry(name, clearBytes, encrypt);
}
void writeEntry(String name, String clearText, boolean encrypt)
throws IOException
{
	writeEntry(name, clearText.getBytes(), encrypt);
}
// -------------------------------------------------------------------
/**
 * 
 * @param dir
 * @param includeFiles Include these files in the output
 * @param cryptFiles Encrypt these files in the output
 * @param out
 * @param password Used to encrypt files indicated by cryptFiles
 */
public void writeDir(File dir,
FileFilter includeDirs,
FileFilter includeFiles,
FileFilter cryptFiles)
throws IOException
{
	if (includeDirs == null) includeDirs = new FileFilter() {
	public boolean accept(File dir) {
		if (dir.getName().startsWith(".")) return false;
		return true;
	}};
	if (includeFiles == null) includeFiles = trueFilter;
	if (cryptFiles == null) {
		if (password == null) cryptFiles = falseFilter;
		cryptFiles = new FileFilter() {
		public boolean accept(File dir) {
			if (dir.getName().endsWith(".crypt")) return false;
			return true;
		}};
	}
	writeDir(dir, dir, includeDirs, includeFiles, cryptFiles);
}

/** Strips baseDir off a pathname, leaving a relative pathname.  f must
 be a descendant of baseDir*/
static String getRelative(File baseDir, File f)
{
	String rel = f.getPath().substring(baseDir.getPath().length()+1);
	return rel.replace(File.separatorChar, '/');
}

void writeDir(File baseDir, File dir,
FileFilter includeDirs,
FileFilter includeFiles,
FileFilter cryptFiles)
throws IOException
{
	// Do files in this directory
	File[] files = dir.listFiles();
	for (File f : files) {
		if (f.isDirectory()) {
			if (includeDirs.accept(f))
				writeDir(baseDir, f, includeDirs, includeFiles, cryptFiles);
		} else {
			// Copy this file to the output...
			if (!includeFiles.accept(f)) continue;

			// Create entry in Zip file
			boolean encrypt = cryptFiles.accept(f);
			String name = getRelative(baseDir, f);
			
			writeEntry(name, new FileInputStream(f), encrypt);
		}
	}
}
}
