package citibob.config;

import citibob.config.Config;
import citibob.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;



/**
 *
 * @author citibob
 */
public class ResourceConfig implements Config
{

ClassLoader cl;
String base;

public ResourceConfig(String xbase)
{
	this(ResourceConfig.class.getClassLoader(), xbase);
}
public ResourceConfig(ClassLoader cl, String xbase)
{
	this.cl = cl;
	this.base = xbase;
	if (!base.endsWith("/")) base = base + "/";
}

public InputStream openStream(String name) throws IOException
{
	return cl.getResourceAsStream(base + name);
}
public byte[] getStreamBytes(String name) throws IOException
{
	InputStream in = openStream(name);
	byte[] ret = IOUtils.getBytes(in);
	in.close();
	return ret;
}
	
}
