/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.crypt.DialogPBEAuth;
import citibob.crypt.PBEAuth;
import citibob.io.IOUtils;
import citibob.reflect.ClassPathUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Reads a Zip file into memory, setting up the streams found in
 * it as the StreamSet.
 * @author citibob
 */
public class ZipStreamSet extends MemStreamSet
{

public ZipStreamSet(InputStream in) throws IOException
{
	byte[] buf = new byte[1024];
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	
	ZipInputStream zin = new ZipInputStream(new BufferedInputStream(in));
	for (;;) {
		ZipEntry ze = zin.getNextEntry();
		if (ze == null) break;
        int len;
        while ((len = zin.read(buf)) > 0) bout.write(buf, 0, len);
//System.out.println("Read entry " + ze.getName() + " of size " + bout.size());
		streams.put(ze.getName(), bout.toByteArray());
	}
	zin.close();
}

public static void main(String[] args) throws Exception
{
	File root = ClassPathUtils.getMavenProjectRoot();
	InputStream in = new FileInputStream(new File(root, "config.zip"));
//	PBEAuth auth = new ConstPBEAuth("password".toCharArray());
	PBEAuth auth = new DialogPBEAuth(null, "Please enter launcher password:");
	StreamSet sset = new PBEStreamSet(new ZipStreamSet(in), auth);
	
	InputStream inn = sset.openStream("app.properties");
	Reader reader = new InputStreamReader(inn);
	System.out.println(IOUtils.getString(reader));
}


}
