/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author citibob
 */
public class ResourceStreamSet implements StreamSet
{

ClassLoader cl;
String base;

public ResourceStreamSet(String xbase)
{
	this(ResourceStreamSet.class.getClassLoader(), xbase);
}
public ResourceStreamSet(ClassLoader cl, String xbase)
{
	this.cl = cl;
	this.base = xbase;
	if (!base.endsWith("/")) base = base + "/";
}

public InputStream openStream(String name) throws IOException
{
	return cl.getResourceAsStream(base + name);
}
	
}
