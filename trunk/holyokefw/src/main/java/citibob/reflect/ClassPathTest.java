/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.reflect;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassPathTest {
public static void main(String[] args)
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
			URL[] urls = urlcl.getURLs();
			for (URL url : urls) System.out.println("      " + url);
		} catch(ClassCastException e) {}
	}

	System.out.println("======= Sample resource searches");
	System.out.println(clMain.getResource("citibob/app/App.class"));
	System.out.println(clMain.getResource("bsh/Interpreter.class"));
	System.out.println(clMain.getResource("java/lang/Integer.class"));
}
}
