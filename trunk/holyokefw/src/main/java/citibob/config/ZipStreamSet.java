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
public class ZipStreamSet
{

public static StreamSet loadFromStream(InputStream in) throws IOException
{
	MemStreamSet ret = new MemStreamSet();
	byte[] buf = new byte[1024];
	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	
	ZipInputStream zin = new ZipInputStream(new BufferedInputStream(in));
	for (;;) {
		ZipEntry ze = zin.getNextEntry();
		if (ze == null) break;
        int len;
        while ((len = zin.read(buf)) > 0) bout.write(buf, 0, len);
//System.out.println("Read entry " + ze.getName() + " of size " + bout.size());
		ret.streams.put(ze.getName(), bout.toByteArray());
	}
	return ret;
//	zin.close();
}

public static StreamSet loadFromFile(File file) throws IOException
{
	InputStream in = new FileInputStream(file);
	try {
		return loadFromStream(in);
	} finally {
		in.close();
	}
	
}


//public static Config loadFromURL(URL url,String user, String password) throws IOException
//{
//	url.toString() + "&user=" + 
//	InputStream in = url.openStream();
//	try {
//		
//		return loadFromStream(in);
//	} finally {
//		in.close();
//	}
//}

/*
 * Stuff that goes in config.properties (which should NEVER be encrypted):
 * app.url = URL of Java WebStart app to launch
 * config.name = Printable name of database/configuration we're connecting to
 * app.user = End-user identified as running this app
 * 
 * Stuff that the TCP server might dynamically place in app.properties:
 * db.user
 * db.databse
 * db.password
 */


public static void main(String[] args) throws Exception
{
	File root = ClassPathUtils.getMavenProjectRoot();
//	InputStream in = new FileInputStream();
	File file = new File(root, "config.zip");
//	PBEAuth auth = new ConstPBEAuth("password".toCharArray());
	PBEAuth auth = new DialogPBEAuth(null, "Please enter launcher password:");
	StreamSet sset = new PBEStreamSet(ZipStreamSet.loadFromFile(file), auth);
	
	InputStream inn = sset.openStream("app.properties");
	Reader reader = new InputStreamReader(inn);
	System.out.println(IOUtils.getString(reader));
}


}
