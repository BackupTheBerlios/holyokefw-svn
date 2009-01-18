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
public class DirConfig extends BaseConfig
{

File root;

public DirConfig(File root, String name)
{
	super(name);
	this.root = root;

}
public DirConfig(File root)
{
	this(root, root.getAbsolutePath());

}

public InputStream openStream(String name)
throws IOException
{
	File f = new File(root, name);
	if (!f.exists()) return null;
	return new FileInputStream(f);
}


}
