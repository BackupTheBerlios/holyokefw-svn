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
public class JarURL {

String name;
URL url;

public JarURL(URL url) {
	this.url = url;
	this.name = ReflectUtils.getLeaf(url);
}

public String getName() {
	return name;
}

public URL getUrl() {
	return url;
}

}
