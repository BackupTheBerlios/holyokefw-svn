/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.mobilecode;

import citibob.reflect.ReflectUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

/**
 *
 * @author fiscrob
 */
public class MobileCodeLoader extends ClassLoader
{

TreeMap<String,MobileClass> classes = new TreeMap();

public void addClasses(MobileClass[] xclasses)
{
	for (MobileClass mclass : xclasses) classes.put(mclass.className, mclass);
}

public Class findClass(String name) throws ClassNotFoundException
{
	MobileClass mclass = classes.get(name);
	if (mclass == null) throw new ClassNotFoundException(name);
	return defineClass(name, mclass.bytecode, 0, mclass.bytecode.length);
}

protected URL findResource(String name)
{
	try {
		return new URL("http://resource/" + name);
	} catch(MalformedURLException e) {
		e.printStackTrace();
		return null;
	}
}

public InputStream getResourceAsStream(String name)
{
	InputStream in = getParent().getResourceAsStream(name);
	if (in == null) {
		name = ReflectUtils.resourceToClass(name);
		MobileClass mclass = classes.get(name);
		if (mclass != null) in =  new ByteArrayInputStream(mclass.bytecode);
	}
System.out.println("MobileCodeLoader.getResourceAsStream(" + name + ") = " + in);
	return in;
}


}
