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
import java.text.ParseException;
import javax.swing.table.*;

/**
 * Wraps a TableModel, converting everything to String.
 WARNING: Does not bother to pass up events from the TableModel it wraps.
 * @author citibob
 */
public class StringTableModel extends ColPermuteTableModel {

SFormat[] formatters;		// Formatter for each column
//JTypeTableModel mod;
//int[] colMap;					// The columns we want to do

/** This will create a "default" colMap, including only the VISIBLE
 * columns in the underlying TableModel. The array of formatters
 * must be of the same length.
 * @param mod
 * @param formatters
 */
public StringTableModel(JTypeTableModel mod, SFormatMap smap)
{
	init(mod, null, null, false, smap);
}
public StringTableModel(JTypeTableModel mod, String[] sColMap,
boolean[] editable, boolean forwardEvents, SFormatMap smap)
{
	init(mod, sColMap, editable, forwardEvents, smap);
}
protected void init(JTypeTableModel mod, String[] sColMap, boolean[] editable,
boolean forwardEvents, SFormatMap smap)
{
	super.init(mod, null, sColMap, null, forwardEvents);
	
	formatters = new SFormat[getColumnCount()];
	for (int col=0; col<formatters.length; ++col) {
		int colU = getColU(col);
		SFormat fmt = null;
		if (colU >= 0) fmt = smap.newSFormat(
			mod.getJType(0,colU), mod.getColumnName(colU));
		if (fmt == null) fmt = NullSFormat.instance;
		formatters[col] = fmt;
	}
	
//	if (formatters.length != getColumnCount()) {
//		throw new IllegalArgumentException(
//			"Formatters must have " + getColumnCount() +
//			" elements, it has only " + formatters.length);
//	}
//	this.formatters = formatters;
//	
//	// Eliminate nulls
//	for (int i=0; i<formatters.length; ++i)
//		if (formatters[i] == null) formatters[i] = NullSFormat.instance;
}

//public StringTableModel(JTypeTableModel mod, String[] sColMap,
//SFormatMap smap)
//{
//	SFormat[] formatters = new SFormat[sColMap.length];
//	for (for)
//}


//
//	model_u, colNames, colNames, editable, forwardEvents)
//	this.mod = mod;
//	this.formatters = formatters;
//	
//	// ColMap by default will just be non-__ columns
//	int ngood = 0;
//	for (int i=0; i<mod.getColumnCount(); ++i) {
//		String name = mod.getColumnName(i);
//		if (!name.startsWith("__")) ++ngood;
//	}
//	colMap = new int[ngood];
//	int j=0;
//	for (int i=0; i<mod.getColumnCount(); ++i) {
//		String name = mod.getColumnName(i);
//		if (!name.startsWith("__")) {
//			colMap[j++] = i;
//		}
//	}
//}


///** @param colNames Name of each column in finished report --- Null if use underlying column names
// @param sColMap Name of each column in underlying uModel  --- Null if wish to use all underlying columns */
//public StringTableModel(JTypeTableModel mod,
//SFormatMap sfmap)
//{
//	this(mod, sfmap.newSFormats(mod));
//}
//public StringTableModel(JTypeTableModel mod,
//SFormatMap sfmap, String[] scol, SFormat[] sfmt)
//{
//	this(mod, sfmap.newSFormats(mod, scol, sfmt));
//}
/** Used to set a special (non-default) formatter for a particular column. */
public void setSFormat(String uname, SFormat fmt)
{
	int col = model_u.findColumn(uname);
	formatters[col] = fmt;
}

/** Convenience function. */
public void setSFormat(String uname, java.text.Format fmt)
	{ setSFormat(uname, new FormatSFormat(fmt)); }
// -----------------------------------------------------------------------

public int getRowCount() { return model_u.getRowCount(); }
public int getColumnCount() { return colMap.length; }
public String getColumnName(int column) { return model_u.getColumnName(colMap[column]); }
public Object getValueAt(int row, int col) {
	try {
		int colU = colMap[col];
		Object val = model_u.getValueAt(row,colU);
		SFormat fmt = formatters[col];
		return fmt.valueToString(val);
	} catch(Exception e) {
		e.printStackTrace();
		return e.toString();
	}
}

/** Replaces the usual setValueAt(). */
public void setStringValueAt(String stringVal, int row, int col)
throws ParseException
{
	Object val;

	int mcol = colMap[col];
	val = formatters[mcol].stringToValue(stringVal);
	model_u.setValueAt(val, row, mcol);
}
public Class getColumnClass(int columnIndex) { return String.class; }
}
