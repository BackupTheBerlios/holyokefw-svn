/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.crypt.PBECrypt;
import citibob.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ConfigWriter {
	
char[] password;




public ConfigWriter(char[] password)
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

public void writeEntry(String name, byte[] clearBytes, boolean encrypt)
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
	} else bytes = clearBytes;
	
	writeSimpleEntry(name, bytes);
}

public void writeEntry(String name, InputStream in, boolean encrypt)
throws IOException
{
	byte[] clearBytes = IOUtils.getBytes(in);
	in.close();
	
	writeEntry(name, clearBytes, encrypt);
}
public void writeEntry(String name, String clearText, boolean encrypt)
throws IOException
{
	writeEntry(name, clearText.getBytes(), encrypt);
}


/** Writes a Java properties file */
public void writeEntry(String name, Properties props, boolean encrypt)
throws IOException
{
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	props.store(bout, null);
	writeEntry(name, bout.toByteArray(), encrypt);
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
	if (includeFiles == null) includeFiles = IOUtils.trueFilter;
	if (cryptFiles == null) {
		if (password == null) cryptFiles = IOUtils.falseFilter;
		else cryptFiles = new FileFilter() {
		public boolean accept(File dir) {
			// Encrypt everything not already encrypted
			if (dir.getName().endsWith(".crypt")) return false;
			return true;
		}};
	}
	writeDir(dir, dir, includeDirs, includeFiles, cryptFiles);
}


private void writeDir(File baseDir, File dir,
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
			String name = IOUtils.getRelative(baseDir, f);
			
			writeEntry(name, new FileInputStream(f), encrypt);
		}
	}
}
}
