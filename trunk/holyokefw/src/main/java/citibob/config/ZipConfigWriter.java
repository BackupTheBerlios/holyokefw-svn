/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.reflect.ClassPathUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipConfigWriter extends ConfigWriter
{
	
ZipOutputStream zout;

public ZipConfigWriter(OutputStream out)
{
	this(out, null);
	
}
public ZipConfigWriter(OutputStream out, char[] password)
{
	super(password);
	zout = new ZipOutputStream(new BufferedOutputStream(out));
}


void writeSimpleEntry(String name, byte[] bytes)
throws IOException
{
	ZipEntry ze = new ZipEntry(name);
		ze.setSize(bytes.length);
	zout.putNextEntry(ze);
	zout.write(bytes);
	zout.closeEntry();
}

public void finish() throws IOException
{ zout.finish(); }
public void close() throws IOException
{ zout.close(); }
// =================================================================
public static void main(String[] args) throws Exception
{
	File root = ClassPathUtils.getMavenProjectRoot();
	OutputStream out = new FileOutputStream(new File(root, "config.zip"));
	char[] password = "password".toCharArray();
	ZipConfigWriter zzout = new ZipConfigWriter(out, password);

	zzout.writeEntry("url.txt", "http://offstagearts.org", false);
	zzout.writeDir(new File(root, "../oamisc/ballettheatre/config_lan"),
		null, null, null);
	// Add in JKS files...
	// Parse properties files to figure out which JKS files to use.
	// (Use a base properties files from server directory)
	zzout.close();
}
}
