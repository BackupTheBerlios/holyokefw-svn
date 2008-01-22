/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

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
 * AbstractLineWriter.java
 *
 * Created on December 6, 2005, 11:13 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.io;

import java.io.*;
import java.util.*;

public abstract class AbstractLineWriter extends Writer
{
StringBuffer line = new StringBuffer();

public void close() throws IOException {
	processLine();
}
public  void flush() {}
//public void write(char[] cbuf)
//	{ write(cbuf, 0,cbuf.length); }
public  void write(char[] cbuf, int off, int len) throws IOException
{
	int a=off;
	int b=off;
	int end = off + len;
	// Find next newline after a
	for (b=a; ; ++b) {
		if (b == end) {		// it ended w/o newline				
			line.append(cbuf,a,b-a);
			return;
		}
		if (cbuf[b] == '\n' || cbuf[b] == '\r') {
			line.append(cbuf,a,b-a);
			a=b+1;
			if (cbuf[b] == '\n') processLine();
		}
	}
}
public void write(int c) throws IOException
{
	if (c == '\n' || c == '\r') {
		processLine();
	} else {
		line.append(c);
	}
}
//public void write(String str)
//{
//	write(str.toCharArray());
//}
//public void write(String str, int off, int len)
//{
//	write(str.toCharArray(),off,len);	
//}
// ----------------------------------------------------------------
abstract void processLine() throws IOException;
}
