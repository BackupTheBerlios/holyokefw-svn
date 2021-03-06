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

/**
 * Buffers one line at a time
 */
public class LineBufferWriter extends AbstractLineWriter
{

Writer out;

public LineBufferWriter(Writer out)
{
	this.out = out;
}
public void processLine() throws IOException
{
	//if (line.length() == 0) return;
	out.write(line.toString());
	out.write('\n');
	line.delete(0, line.length());
}
}

