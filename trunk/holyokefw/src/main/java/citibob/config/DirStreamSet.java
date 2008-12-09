/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author citibob
 */
public class DirStreamSet implements StreamSet
{

File root;
	
public DirStreamSet(File root)
{
	this.root = root;
}

public InputStream openStream(String name)
throws IOException
{
	File f = new File(root, name);
	if (!f.exists()) return null;
	return new FileInputStream(f);
}

public byte[] getStreamBytes(String name) throws IOException
{
	InputStream in = openStream(name);
	byte[] ret = IOUtils.getBytes(in);
	in.close();
	return ret;
}

}
