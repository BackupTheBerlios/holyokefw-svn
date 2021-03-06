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
 * CSVReportOutput.java
 *
 * Created on February 14, 2007, 11:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.table.*;
import java.sql.*;
import citibob.text.*;
import citibob.sql.*;
import java.io.*;
import javax.swing.table.*;

/**
 * Wraps a TableModel, converting everything to String.
 WARNING: Does not bother to pass up events from the TableModel it wraps.
 * @author citibob
 */
public class StringTableModel extends AbstractTableModel {

SFormat[] formatters;		// Formatter for each column
JTypeTableModel mod;
int[] colMap;					// The columns we want to do

public StringTableModel(JTypeTableModel mod, SFormat[] formatters)
{
	this.mod = mod;
	this.formatters = formatters;
	
	// ColMap by default will just be non-__ columns
	int ngood = 0;
	for (int i=0; i<mod.getColumnCount(); ++i) {
		String name = mod.getColumnName(i);
		if (!name.startsWith("__")) ++ngood;
	}
	colMap = new int[ngood];
	int j=0;
	for (int i=0; i<mod.getColumnCount(); ++i) {
		String name = mod.getColumnName(i);
		if (!name.startsWith("__")) {
			colMap[j++] = i;
		}
	}
}


/** @param colNames Name of each column in finished report --- Null if use underlying column names
 @param sColMap Name of each column in underlying uModel  --- Null if wish to use all underlying columns */
public StringTableModel(JTypeTableModel mod,
SFormatMap sfmap)
{
	this(mod, sfmap.newSFormats(mod));
}
public StringTableModel(JTypeTableModel mod,
SFormatMap sfmap, String[] scol, SFormat[] sfmt)
{
	this(mod, sfmap.newSFormats(mod, scol, sfmt));
}
/** Used to set a special (non-default) formatter for a particular column. */
public void setSFormat(String uname, SFormat fmt)
{
	int col = mod.findColumn(uname);
	formatters[col] = fmt;
}

/** Convenience function. */
public void setSFormat(String uname, java.text.Format fmt)
	{ setSFormat(uname, new FormatSFormat(fmt)); }
// -----------------------------------------------------------------------

public int getRowCount() { return mod.getRowCount(); }
public int getColumnCount() { return colMap.length; }
public String getColumnName(int column) { return mod.getColumnName(colMap[column]); }
public Object getValueAt(int row, int col) {
	try {
		int mcol = colMap[col];
		return formatters[mcol].valueToString(mod.getValueAt(row,mcol));
	} catch(Exception e) {
		return e.toString();
	}
}
public Class getColumnClass(int columnIndex) { return String.class; }
}
