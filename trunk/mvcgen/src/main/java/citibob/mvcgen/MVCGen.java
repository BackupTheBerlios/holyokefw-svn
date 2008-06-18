/*
 * MVCGen.java
 *
 * Created on January 9, 2006, 8:50 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.mvcgen;

import java.io.*;
import java.util.*;

/**
 *
 * @author fiscrob
 */
public interface MVCGen {

/** Returns suffix of files this processes, with dot. */
public String getSuffix();
	
public void open(Reader in) throws IOException;

public void doWrite(Writer out) throws IOException;


}
