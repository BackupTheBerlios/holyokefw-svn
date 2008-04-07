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
public static void getClassPath(URL jarURL, List<URL> out)
throws MalformedURLException, IOException
{
//	List<URL> urls = new LinkedList();
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
		out.add(new URL(jarURL, spec));		
	}
	
//	return urls;
}

/** Recursively gets classpath, looking into all MANIFEST files. */
public static void getClassPath(URLClassLoader loader, List<URL> out)
throws MalformedURLException, IOException
{
	// Look at each item nominally on classloader's classpath
	URL[] urls = loader.getURLs();
	for (URL url : urls) {
		out.add(url);
		if (url.getPath().endsWith(".jar")) {
			// We have a jar URL; add in any items on the jar's classpath
			getClassPath(url, out);
		}
	}
}

static void eliminateDups(List<URL> urls)
{
	Set<URL> set = new HashSet();
	for (Iterator<URL> ii = urls.iterator(); ii.hasNext(); ) {
		URL url = ii.next();
		if (set.contains(url)) {
			ii.remove();
		} else {
			set.add(url);
		}
	}
}

static List<URL> getClassPath(URLClassLoader loader)
throws MalformedURLException, IOException
{
	List<URL> out = new LinkedList();
	getClassPath(loader, out);
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
			List<URL> urls = getClassPath(urlcl);
			for (URL url : urls) System.out.println("      " + url);
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
