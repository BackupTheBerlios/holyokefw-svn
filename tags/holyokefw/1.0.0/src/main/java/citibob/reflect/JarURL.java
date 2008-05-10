/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
