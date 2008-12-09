/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.crypt.PBECrypt;
import citibob.crypt.PBEAuth;
import citibob.io.IOUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author citibob
 */
public class PBEStreamSet implements StreamSet
{
StreamSet sub;
PBEAuth auth;
//PBECrypt crypt;

public PBEStreamSet(StreamSet sub, PBEAuth auth)
{
	this.sub = sub;
	this.auth = auth;
//	crypt = new PBECrypt();
}

public InputStream openStream(String name) throws IOException
{
	byte[] bytes = getStreamBytes(name);
	if (bytes == null) return null;
	return new ByteArrayInputStream(bytes);
}

public byte[] getStreamBytes(String name) throws IOException
{
	// First search for unencrypted version of resource
	InputStream iin = sub.openStream(name);
	if (iin != null) return IOUtils.getBytes(iin);
	
	// Unencrypted version not found; try encrypted version
	iin = sub.openStream(name + ".crypt");
	if (iin == null) return null;
	
	// ========== Encrypted version found, decrypt it now
	
	// Read the encrypted version
	String cipherText = new String(IOUtils.getBytes(iin));
	byte[] clearBytes = PBECrypt.decrypt(cipherText, auth);
	if (clearBytes == null) return null;

	return clearBytes;
}
// =============================================================
static FileFilter trueFilter = new FileFilter() {
public boolean accept(File arg0) { return true; }};

static FileFilter falseFilter = new FileFilter() {
public boolean accept(File arg0) { return false; }};


static public void makeFromDir(File dir,
FileFilter includeDirs,
FileFilter includeFiles,
OutputStream out)
throws IOException
{
	makeFromDir(dir, includeDirs, includeFiles, out, null, null);
}
/**
 * 
 * @param dir
 * @param includeFiles Include these files in the output
 * @param cryptFiles Encrypt these files in the output
 * @param out
 * @param password Used to encrypt files indicated by cryptFiles
 */
static public void makeFromDir(File dir,
FileFilter includeDirs,
FileFilter includeFiles,
OutputStream out,
FileFilter cryptFiles,
char[] password)
throws IOException
{
	if (includeDirs == null) includeDirs = trueFilter;
	if (includeFiles == null) includeFiles = trueFilter;
	if (cryptFiles == null) cryptFiles = falseFilter;
	ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(out));
	makeFromDir(dir, dir, includeDirs, includeFiles, cryptFiles, zout, password);
}

/** Strips baseDir off a pathname, leaving a relative pathname.  f must
 be a descendant of baseDir*/
static String getRelative(File baseDir, File f)
{
	String rel = f.getPath().substring(baseDir.getPath().length());
	return rel.replace(File.separatorChar, '/');
}

static void makeFromDir(File baseDir, File dir,
FileFilter includeDirs,
FileFilter includeFiles,
FileFilter cryptFiles,
ZipOutputStream zout,
char[] password)
throws IOException
{
	// Do files in this directory
	File[] files = dir.listFiles();
	for (File f : files) {
		if (f.isDirectory() && includeDirs.accept(f)) {
			makeFromDir(baseDir, f, includeDirs, includeFiles, cryptFiles, zout, password);
		} else {
			// Copy this file to the output...
			if (!includeFiles.accept(f)) continue;

			// Create entry in Zip file
			boolean encrypt = cryptFiles.accept(f);
			String name = getRelative(baseDir, f);
			if (encrypt) name = name + ".crypt";
			ZipEntry ze = new ZipEntry(name);
			zout.putNextEntry(ze);
			
			// Copy the actual file
			if (encrypt) {
				// Copy encrypted
				byte[] clearBytes = IOUtils.getBytes(new FileInputStream(f));
				try {
					String cipherText = PBECrypt.encrypt(clearBytes, password);
					Writer writer = new OutputStreamWriter(zout);
					writer.write(cipherText);
					writer.flush();
				} catch(IOException ioe) {
					throw ioe;
				} catch(Exception e) {
					IOException ioe = new IOException(e.getMessage());
					ioe.initCause(e);
					throw ioe;
				}
			} else {
				// Copy unencrypted
				InputStream in = new BufferedInputStream(new FileInputStream(f));
				IOUtils.copy(in, zout);
				in.close();
			}
		}
	}
}
// ================================================================
public static void main(String[] args)
{
}
}
