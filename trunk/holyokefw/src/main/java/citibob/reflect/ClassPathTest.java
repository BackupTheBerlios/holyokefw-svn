/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.reflect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ClassPathTest {
	

static final int ST_INIT = 0;
static final int ST_INCP = 1;
	
/** Adds to a classpath the stuff found in the Class-Path of a manifest file. */
public static void getClassPath(URL jarURL, List<JarURL> out)
throws MalformedURLException, IOException
{
//	List<JarURL> urls = new LinkedList();
//	URL url = clurl.findResource("META-INF/MANIFEST.MF");
	URL manifestURL = new URL("jar:" + jarURL.toString() + "!/META-INF/MANIFEST.MF");
	
	// Open the Manifest file and read the Class-Path item
	StringBuffer sb = new StringBuffer();
	BufferedReader in = new BufferedReader(new InputStreamReader(
		manifestURL.openStream()));
	String line;
	int state = ST_INIT;
	outer : while ((line = in.readLine()) != null) {
		switch(state) {
			case ST_INIT : {
				int colon = line.indexOf(':');
				if (colon < 0) continue;
				String label = line.substring(0, colon).trim().toLowerCase();
				if (!"class-path".equals(label)) continue;
				state = ST_INCP;
				sb.append(line.substring(colon+1));
			} break;
			case ST_INCP : {
				if (line.length() > 0 && line.charAt(0) == ' ') {
					sb.append(line.substring(1));
				} else {
					state = ST_INIT;
					break outer;		// Only one Class-Path item, we're done!
				}
			}
		}
	}
	in.close();

	// Parse the classpath
	String[] sclasspath = sb.toString().trim().split(" ");
	for (String spec : sclasspath) {
		out.add(new JarURL(new URL(jarURL, spec)));
	}
	
//	return urls;
}

/** Recursively gets classpath, looking into all MANIFEST files. 
 Starts from a base classpath of jar files. */
public static void expandClassPath(URLClassLoader loader, List<JarURL> base, List<JarURL> out)
throws MalformedURLException, IOException
{
	// Look at each item nominally on classloader's classpath
	for (JarURL url : base) {
		out.add(url);
		if (url.getUrl().getPath().endsWith(".jar")) {
			// We have a jar URL; add in any items on the jar's classpath
			getClassPath(url.getUrl(), out);
		}
	}
}

/** Recursively gets classpath, looking into all MANIFEST files. */
public static List<JarURL> getBaseClassPath(URLClassLoader loader)
throws MalformedURLException, IOException
{
	List<JarURL> out = new LinkedList();
	// Look at each item nominally on classloader's classpath
	URL[] urls = loader.getURLs();
	for (URL url : urls) out.add(new JarURL(url));
	return out;
}

static void eliminateDups(List<JarURL> urls)
{
	Set<JarURL> set = new HashSet();
	for (Iterator<JarURL> ii = urls.iterator(); ii.hasNext(); ) {
		JarURL url = ii.next();
		if (set.contains(url)) {
			ii.remove();
		} else {
			set.add(url);
		}
	}
}

///** Computes A - B */
//static void subtractCP(List<JarURL> aa, List<JarURL> bb)
//{
//	Set<URL> bbset = new HashSet();
//	for (URL url : bb) bbset.add(url);
//	for (Iterator<URL> ii = urls.iterator(); ii.hasNext(); ) {
//		URL url = ii.next();
//		if (set.contains(url)) {
//			ii.remove();
//		} else {
//			set.add(url);
//		}
//	}
//}

static List<JarURL> getClassPath(URLClassLoader loader)
throws MalformedURLException, IOException
{
	List<JarURL> out = new LinkedList();
	expandClassPath(loader, getBaseClassPath(loader), out);
	eliminateDups(out);
	return out;
}
public static void main(String[] args) throws Exception
{
	ClassLoader clMain = ClassPathTest.class.getClassLoader();
	System.out.println("====== Parent Classloaders");
	for (ClassLoader loader = clMain; loader != null; loader = loader.getParent()) {
		// Main classloader
		System.out.println(loader);
		
		// The classloader's inheritence structure
		Class klass = loader.getClass();
		while (klass != null) {
			System.out.println("     " + klass.getName());
			klass = klass.getSuperclass();
		}
		
		// The Classloader's classpath
		System.out.println("     ---- Classpath:");
		try {
			URLClassLoader urlcl = (URLClassLoader)loader;
//			URL[] urls = urlcl.getURLs();
			List<JarURL> urls = getClassPath(urlcl);
			for (JarURL url : urls) System.out.println("      " + url);
		} catch(ClassCastException e) {}
	}

	URLClassLoader clurl = (URLClassLoader)clMain;
	System.out.println("======= Sample resource searches");
	System.out.println(clurl.getResource("citibob/app/App.class"));
	System.out.println(clurl.getResource("bsh/Interpreter.class"));
	System.out.println(clurl.getResource("java/lang/Integer.class"));
	System.out.println(clurl.findResource("META-INF/MANIFEST.MF"));
	System.out.println(clurl.getResource("META-INF/MANIFEST.MF"));
	
	// Get the project directory from the URL of the main classpath
	
//	// Parse the manifest 
//	URL url = clurl.findResource("META-INF/MANIFEST.MF");
//	Reader in = new InputStreamReader(url.openStream());
//	int c;
//	while ((c = in.read()) >= 0) System.out.print((char)c);
//	in.close();
}
}
