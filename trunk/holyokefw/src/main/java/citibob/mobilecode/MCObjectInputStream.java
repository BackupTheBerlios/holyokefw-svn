/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.mobilecode;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;


public class MCObjectInputStream extends ObjectInputStream
{

public MCObjectInputStream(InputStream in) throws IOException { super(in); }
	
}
