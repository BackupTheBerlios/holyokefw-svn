/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.reflect;

import java.net.URL;

/**
 *
 * @author fiscrob
 */
public class JarURL implements Comparable<JarURL>
{

String name;
URL url;

public JarURL(URL url) {
	this.url = url;
	name = ReflectUtils.getLeaf(url);
	int dash = name.indexOf('-');
	if (dash >= 0) name = name.substring(0,dash);
}

public String getName() {
	return name;
}

public URL getUrl() {
	return url;
}

public String toString() { return url.toString(); }

public int compareTo(JarURL b) {
	return getName().compareTo(b.getName());
}

}
