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
 * Model structure:
x * A. modelU <- sortModel <- colPermuteModel <- JTypeColTable
x *		Here, sortModelU == sortModel
 * B. modelU <- permuteModel <- JTypeColTable
 *		modelU may be an instance of SortableTableModel
x *		Here, sortModelU == modelU, sortModel == null
 */

package citibob.swing;

//import bsh.This;
import citibob.swing.typed.Swinger;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import citibob.sql.*;
import citibob.jschema.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.types.JType;
import java.awt.*;
import citibob.text.*;
import citibob.types.KeyedModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

/**
 * A table with one type per column.  Integrated with ColPermuteTable, so it's
 * convenient for editing JTypeTableModels.
 * @author citibob
 */
public class JTypeColTable
extends CitibobJTable
{

StringTableModel ttModel;	// Tooltips for each column (in view)
SFormat[] ttFmt;				// Formatter for each tooltip (in view)

JTypeTableModel modelU;			// Could be instance of SortableTableModel
	SortableTableModel sortModel;	// The sorter model (if modelU is instance)
ColPermuteTableModel permuteModel;	// The permuter model


// In wrapping order:
//CitibobTableModel modelU;		// Model the user gave us.
//CitibobTableModel sortModelU;		// Model the user gave us, sorted...


/** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
public String getTooltip(int row, int col)
{
	if (ttModel == null) return null;
	return (String)ttModel.getValueAt(row, col);
//	try {
//		if (ttFmt[col] == null) return null;
//		Object val = ttModel.getValueAt(row, col);
//		return ttFmt[col].valueToString(ttModel.getValueAt(row, col));
//
////		if (ttFmt[col] == null) return null;
////		int row_u = row;
////		if (sortModel != null) row_u = sortModel.viewToModel(row);
////		return ttFmt[col].valueToString(ttModel.getValueAt(row_u, col)); // + "\nHoi";
//	} catch(java.text.ParseException e) {
//		return "<JTypeColTable: ParseException>\n" + e.getMessage();
//	}
}

//public void setModelU(CitibobTableModel uModel, String[] colNames,
//String[] sColMap, boolean[] editable)
//{
//	super.setModelU(uModel, colNames, sColMap, editable);
//	this.modelU = uModel;
//	this.sortModelU = uModel;
//}

public void setModelU(JTypeTableModel modelU, SwingerMap smap)
{
	setModelU(modelU, null,null,null, smap);
	setAllEditable(false);
}

/** @param modelU Underling data buffer to use.  If it's an instance of
 * SortableTableModel, sortable features will be used.
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public void setModelU(JTypeTableModel modelU,
		String[] colNames, String[] sColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
//	modelU = wrapModel(modelU, sColMap);
	
	// Set underlying model
	this.modelU = modelU;
	
	// Set up column selection on top of modelU
	permuteModel = new ColPermuteTableModel(
		modelU, colNames, sColMap, editable);
	if (editable != null) permuteModel.setEditable(editable);

	
	// Set up table sorting stuff
	if (modelU instanceof SortableTableModel) {
		sortModel = (SortableTableModel)modelU;
		
		JTableHeader head = getTableHeader();
		head.addMouseListener(new MouseHandler());
		head.setDefaultRenderer(
			new SortableHeaderRenderer(
			head.getDefaultRenderer()));
	}

	// Now go!
	super.setModel(permuteModel);
	
	// Set the RenderEdit for each column, according to that column's SqlType.
//	for (int c=0; c<sColMap.length; ++c) {
	for (int col=0; col<getColumnCount(); ++col) {
		int col_u = permuteModel.getColU(col);
		if (col_u < 0) {
			System.out.println("ERROR: Column " + sColMap[col] + " is undefined!!!");
		}
		JType sqlType = modelU.getJType(0,col_u);
		if (sqlType == null) continue;
		String colName = modelU.getColumnName(col_u);
		if (smap != null) {
			Swinger swing = smap.newSwinger(sqlType, colName);
			if (swing == null) continue;
			setRenderEdit(col, swing);
			Comparator comp = swing.getComparator();
if (comp == null) {
	System.out.println("hoi");
}
			if (sortModel != null) sortModel.setComparator(col_u, comp);
		}
	}

}

/** @param modelU Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
 */
public void setModelU(JTypeTableModel modelU,
		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
	this.setModelU(modelU, colNames, sColMap, editable, smap);
	
	// Come up with model for all the tooltips
	ttModel = new StringTableModel(modelU, ttColMap, null, false, smap);
////	ttModel = new ColPermuteTableModel(modelU, colNames, ttColMap, editable);
//	ttFmt = new SFormat[ttModel.getColumnCount()];
//	for (int i=0; i<ttModel.getColumnCount(); ++i) {
//		int colU = ttModel.getColU(i);
//		if (colU < 0) continue;
//		JType jt = modelU.getJType(0, colU);
//		String colName = modelU.getColumnName(colU);
//		if (jt == null) continue;
//		ttFmt[i] = smap.newSwinger(jt, colName).getSFormat();
//	}
//new StringTableModel
}



/** Returns the TableModel that does sorting, if we have one */
//public CitibobTableModel getModelU()
//{ return sortModelU; }
public SortableTableModel getSortModel()
{ return sortModel; }

//public boolean isSorted() { return getSorter() != null; }

public ColPermuteTableModel getPermuteModel()
	{ return permuteModel; 	 }


///** Convenience function --- allows us to set formatter for common
// data types based soley on a format string. */
//public void setFormat(int col, String fmtString)
//{
//	JTypeTableModel model = (JTypeTableModel)this.getModel();
//	JType jtype = model.getJType(0, col);
//	Class klass = jtype.getClass();
//	Format fmt;
//	TableCellRendererer re = null;
//
//	if (Number.class.isAssignableFrom(klass)) {
//		fmt = new DecimalFormat(fmtString);
//	} else if (Date.class.isAssignableFrom(klass)) {
//		fmt = new SimpleDateFormat(fmtString);
//	}
//	if (fmt != null) setRenderer()
//		re = new citibob.swing.typed.FormatTableCellRenderer(fmt);
//}
///** @pa */
//public void setFormatU(String scol, Format fmt)


///** Returns the UNDERLYING table model set by the user in setModelU(). */
//public CitibobTableModel getModelU()
//{
//	return modelU;
////	
////	CitibobTableModel model = ((CitibobTableModel)getModel());
////	
////	if (model instanceof SortedJTypeTableModel) {
////		// JTypeColTable -> SortedJTypeTableModel -> ColPermuteTableModel -> Underlying Table Model
////		return model.getModelU().getModelU();
////	} else {
////		// JTypeColTable -> ColPermuteTableModel -> Underlying Table Model
////		return model.getModelU();
////	}
//}


///** Returns the value of a column (in the underlying table) of the first selected row. */
//public Object getOneSelectedValU(String colU)
//{
//	int row = getSelectedRow();
//	if (row < 0) return null;
//
//	int row_u = row;
////	if (sortModel != null) row_u = sortModel.viewToModel(row);
//	CitibobTableModel modelU = getSortModelU();
//	int col = modelU.findColumn(colU);
//	return modelU.getValueAt(row, col);	
//}


/** Non-standard way to access any column of the selected row. */
public Object getValue(int colU)
{
	int selRow = this.getSelectedRow();
	if (selRow < 0) return null;
	return getModelU().getValueAt(selRow, colU);
}
public Object getValue(String colNameU)
{
	int colU = getModelU().findColumn(colNameU);
	return getValue(colU);
}


//public int rowOfValueU(Object val, int col_u)
//	{ return rowOfValue(val, col_u, getSortModelU()); }
//public int rowOfValueU(Object val, String underlyingName)
//	{ return rowOfValue(val, getSortModelU().findColumn(underlyingName), getModelU()); }
//
//public void setSelectedRowU(Object val, int col_u)
//	{ setSelectedRow(val, col_u, getSortModelU()); }
////public void setSelectedRowU(Object val, String underlyingName)
////	{ setSelectedRow(val, getModelU().findColumn(underlyingName), getModelU()); }
//public void setSelectedRowU(Object val, String underlyingName)
//	{ setSelectedRowU(val, getSortModelU().findColumn(underlyingName)); }



// =====================================================
// From the old ColPermuteTable
public void setAllEditable(boolean edit)
{
	((ColPermuteTableModel)getModel()).setAllEditable(edit);
}

/** Convenience function, to be used by subclasses:
 * finds the column number in THIS table model based on a column name, not display name. */
public int findColumnU(String s)
{
	return ((ColPermuteTableModel)getModel()).findColumnU(s);
}
//public int getColumnU(int col_u)
//{
//	return ((ColPermuteTableModel)getModel()).getColumnU(col_u);
//}

public CitibobTableModel getModelU()
{ return modelU; }
//{ return ((ColPermuteTableModel)getModel()).getModelU(); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, Swinger swinger)
	{ setRenderEdit(findColumnU(underlyingName), swinger); }

public void setFormatU(String underlyingName, Swinger.RenderEdit re)
	{ setRenderEdit(findColumnU(underlyingName), re); }

public void setFormatU(String underlyingName, KeyedModel kmodel)
	{ setRenderEdit(findColumnU(underlyingName), kmodel); }

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, SFormat sfmt)
	{ setFormat(findColumnU(underlyingName), sfmt); }

public void setFormatU(String underlyingName, java.text.Format fmt)
	{ setFormat(findColumnU(underlyingName), fmt); }

public void setFormatU(String underlyingName, String fmtString)
	{ setFormat(findColumnU(underlyingName), fmtString); }

public int rowOfValueU(Object val, int col_u)
	{ return rowOfValue(val, col_u, getModelU()); }
public int rowOfValueU(Object val, String underlyingName)
	{ return rowOfValue(val, getModelU().findColumn(underlyingName), getModelU()); }

public void setSelectedRowU(Object val, int col_u)
	{ setSelectedRow(val, col_u, getModelU()); }
//public void setSelectedRowU(Object val, String underlyingName)
//	{ setSelectedRow(val, getModelU().findColumn(underlyingName), getModelU()); }
public void setSelectedRowU(Object val, String underlyingName)
	{ setSelectedRowU(val, getModelU().findColumn(underlyingName)); }


/** Sets a renderer on a colum, by UNDERLYING column name.  Only sets
renderer, not editor; only used in special cases. */
public void setRendererU(String underlyingName, TableCellRenderer renderer)
{
	setRenderer(findColumnU(underlyingName), renderer);
}



///** Sets the render and editor (and JType) on a column */
//public void setSwinger(int col, Swinger swing)
//{
//	ColPermuteTableModel model = (ColPermuteTableModel)getModel();
//	setRenderEdit(col, swing.newRenderEdit(model.isCellEditable(0, col)));
//}
//	
///** Sets a render/edit on a colum, by UNDERLYING column name,
// * according to the columns declared SqlType: getColumnJType(). */
//public void setRenderEditU(String underlyingName, RenderEditSet res)
//{
//	int col = findColumnU(underlyingName);
//	SchemaBuf model = (SchemaBuf)getModel();
//	RenderEdit re = res.getRenderEdit(model.getColumnJType(col));
//	setRenderEdit(col, re);
//}
// ======================================================================
/** NOTE: Third Party Code.
 * Mouse Handler is Copyright (c) 1995 - 2008 by Sun Microsystems.
 * See ArrowIcon.java for full copyright notice. */
private class MouseHandler extends MouseAdapter {
public void mouseClicked(MouseEvent e) {
	JTableHeader h = (JTableHeader) e.getSource();
	TableColumnModel columnModel = h.getColumnModel();
	int viewColumn = columnModel.getColumnIndexAtX(e.getX());
	int col_h = columnModel.getColumn(viewColumn).getModelIndex();
	if (col_h != -1) {
		// Find this column in the main model (or sort) table
		int col_u = permuteModel.getColU(col_h);
		
		// Obtain current sorting for possible change
		SortSpec spec = sortModel.getSortSpec();
		int dir = spec.getSortDir(col_u);
		if (!e.isControlDown()) spec.clear();
		
		// Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or 
		// {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed. 
		dir = dir + (e.isShiftDown() ? -1 : 1);
		dir = (dir + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
		spec.setSortDir(col_u, dir);
System.out.println("Sort by column " + col_u + " direction " + dir);

		// Do the refresh
		sortModel.resort();
		getTableHeader().repaint();
	}
}}
// ======================================================================
/** NOTE: Third Party Code.
 * SortableHeaderRenderer is Copyright (c) 1995 - 2008 by Sun Microsystems.
 * See ArrowIcon.java for full copyright notice. */
private class SortableHeaderRenderer implements TableCellRenderer {
private TableCellRenderer sub;

public SortableHeaderRenderer(TableCellRenderer sub) {
	this.sub = sub;
}

public Component getTableCellRendererComponent(
JTable table,  Object value, boolean isSelected, 
boolean hasFocus, int row, int column)
{		
	SortSpec spec = sortModel.getSortSpec();
	Component c = sub.getTableCellRendererComponent(table, 
			value, isSelected, hasFocus, row, column);
	if (c instanceof JLabel) {
		JLabel l = (JLabel) c;
		l.setHorizontalTextPosition(JLabel.LEFT);

		// User might have rearranged columns in the JTable
		int col_h = table.convertColumnIndexToModel(column);

		// Find this column in the main model (or sort) table
		int col_u = permuteModel.getColU(col_h);
		
		// Select the icon to display
		Icon icon = null;
		int dir = spec.getSortDir(col_u);
		if (dir != 0) icon = new ArrowIcon(
			dir < 0, l.getFont().getSize(),
			spec.getSortIndex(col_u));
		l.setIcon(icon);
	}
	return c;
}

}
}
