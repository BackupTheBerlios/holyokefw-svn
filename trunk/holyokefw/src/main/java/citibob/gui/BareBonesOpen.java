/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.gui;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author citibob
 */
public class BareBonesOpen {

public static void open(java.io.File f) throws IOException
{
	String osName = System.getProperty("os.name");
	if (osName.startsWith("Mac")) {
		Runtime.getRuntime().exec("open " + f.getPath());
	} else if (osName.startsWith("Windows")) {
		Runtime.getRuntime().exec("start " + f.getPath());
	} else { //assume Unix or Linux
		Runtime.getRuntime().exec("gnome-open " + f.getPath());
	}
}
public static void main(String[] args) throws Exception
{
    open(new File("c:\\x.pdf"));
}
}
