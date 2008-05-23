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
 * JTypeColTable.java
 *
 * Created on March 13, 2006, 9:28 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

/**
 * A table with one type per column.  Integrated with ColPermuteTable, so it's
 * convenient for editing JTypeTableModels.
 * @author citibob
 */
public class JTypeColTable
extends ColPermuteTable
{

ColPermuteTableModel ttModel;	// Tooltips for each column (in view)
SFormat[] ttFmt;				// Formatter for each tooltip (in view)

// In wrapping order:
ColPermuteTableModel permuteModel;	// The permuter model
SortedJTypeTableModel sortModel;	// The sorter model (if there is one)
CitibobTableModel modelU;		// Model the user gave us.
CitibobTableModel sortModelU;		// Model the user gave us, sorted...


/** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
public String getTooltip(int row, int col)
{
	if (ttModel == null) return null;
	try {
		if (ttFmt[col] == null) return null;
		int row_u = row;
		if (sortModel != null) row_u = sortModel.viewToModel(row);
		return ttFmt[col].valueToString(ttModel.getValueAt(row_u, col)); // + "\nHoi";
	} catch(java.text.ParseException e) {
		return "<JTypeColTable: ParseException>\n" + e.getMessage();
	}
}

public JTypeTableModel setModelU(JTypeTableModel jtModel,
		citibob.swing.typed.SwingerMap smap)
{
	return setModelU(jtModel, false, smap);
}
public JTypeTableModel setModelU(JTypeTableModel modelU,
		String[] colNames, String[] sColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
	return setModelU(modelU, colNames, sColMap, editable, false, smap);
}		
public void setModelU(JTypeTableModel jtModel,
		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
		citibob.swing.typed.SwingerMap smap)
{
	setModelU(jtModel, colNames, sColMap, ttColMap, editable, false, smap);
}		

		
		
		
public JTypeTableModel setModelU(JTypeTableModel jtModel,
		boolean sortable, citibob.swing.typed.SwingerMap smap)
{
	JTypeTableModel jtmod = setModelU(jtModel, null,null,null, sortable, smap);
	setAllEditable(false);
	return jtmod;
}
///** @param jtModel Underling data buffer to use
// * @param typeCol Name of type column in the schema
// * @param xColNames Columns (other than type and status) from schema to display
// */
//public JTypeTableModel setModelU(JTypeTableModel jtModel,
//		String[] colNames, String[] sColMap, boolean[] editable,
//		citibob.swing.typed.SwingerMap smap)
//{
//	setModelU(jtModel, colNames, sColMap, editable, true, smap);
//}

/** Returns the TableModel that does sorting, if we have one */
public CitibobTableModel getSortModelU()
{ return sortModelU; }
public SortedJTypeTableModel getSortModel()
{ return sortModel; }

//public boolean isSorted() { return getSorter() != null; }

/** @param jtModel Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 */
public JTypeTableModel setModelU(JTypeTableModel modelU,
		String[] colNames, String[] sColMap, boolean[] editable,
		boolean sortable, citibob.swing.typed.SwingerMap smap)
{
//	jtModel = wrapModel(jtModel, sColMap);
	
	// Set up model wrapping structure
	this.modelU = modelU;
	if (sortable) {
		sortModel = new SortedJTypeTableModel(modelU);
		sortModelU = sortModel;
		super.setModelU(sortModel, colNames, sColMap, editable);
	} else {
		sortModelU = modelU;
		super.setModelU(modelU, colNames, sColMap, editable);		
	}
	permuteModel = (ColPermuteTableModel)getModel();
	
	if (editable != null) permuteModel.setEditable(editable);
	
	// Set the RenderEdit for each column, according to that column's SqlType.
//	for (int c=0; c<sColMap.length; ++c) {
	for (int col=0; col<this.getColumnCount(); ++col) {
		int col_u = permuteModel.getColMap(col);
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
//if (comp == null) {
//	System.out.println("hoi");
//}
			if (sortModel != null) sortModel.setComparator(col_u, comp);
		}
	}

	// Set up table sorting stuff
	if (sortable) {
		JTableHeader head = getTableHeader();
		head.addMouseListener(new MouseHandler());
		head.setDefaultRenderer(
			new SortableHeaderRenderer(
			head.getDefaultRenderer()));
	}
	
	return modelU;
}

/** @param jtModel Underling data buffer to use
 * @param typeCol Name of type column in the schema
 * @param xColNames Columns (other than type and status) from schema to display
 * @param ttColMap Column in underlying table to display as tooltip for each column in displayed table.
 */
public void setModelU(JTypeTableModel jtModel,
		String[] colNames, String[] sColMap, String[] ttColMap, boolean[] editable,
		boolean sortable, citibob.swing.typed.SwingerMap smap)
{
	jtModel = this.setModelU(jtModel, colNames, sColMap, editable, sortable, smap);
	
	// Come up with model for all the tooltips
	ttModel = new ColPermuteTableModel(jtModel, colNames, ttColMap, editable);
	ttFmt = new SFormat[ttModel.getColumnCount()];
	for (int i=0; i<ttModel.getColumnCount(); ++i) {
		int colU = ttModel.getColMap(i);
		if (colU < 0) continue;
		JType jt = jtModel.getJType(0, colU);
		String colName = jtModel.getColumnName(colU);
		if (jt == null) continue;
		ttFmt[i] = smap.newSwinger(jt, colName).getSFormat();
	}
}

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


/** Returns the UNDERLYING table model set by the user in setModelU(). */
public CitibobTableModel getModelU()
{
	return modelU;
//	
//	CitibobTableModel model = ((CitibobTableModel)getModel());
//	
//	if (model instanceof SortedJTypeTableModel) {
//		// JTypeColTable -> SortedJTypeTableModel -> ColPermuteTableModel -> Underlying Table Model
//		return model.getModelU().getModelU();
//	} else {
//		// JTypeColTable -> ColPermuteTableModel -> Underlying Table Model
//		return model.getModelU();
//	}
}


/** Returns the value of a column (in the underlying table) of the first selected row. */
public Object getOneSelectedValU(String colU)
{
	int row = getSelectedRow();
	if (row < 0) return null;

	int row_u = row;
//	if (sortModel != null) row_u = sortModel.viewToModel(row);
	CitibobTableModel modelU = getSortModelU();
	int col = modelU.findColumn(colU);
	return modelU.getValueAt(row, col);	
}

public int rowOfValueU(Object val, int col_u)
	{ return rowOfValue(val, col_u, getSortModelU()); }
public int rowOfValueU(Object val, String underlyingName)
	{ return rowOfValue(val, getSortModelU().findColumn(underlyingName), getModelU()); }

public void setSelectedRowU(Object val, int col_u)
	{ setSelectedRow(val, col_u, getSortModelU()); }
//public void setSelectedRowU(Object val, String underlyingName)
//	{ setSelectedRow(val, getModelU().findColumn(underlyingName), getModelU()); }
public void setSelectedRowU(Object val, String underlyingName)
	{ setSelectedRowU(val, getSortModelU().findColumn(underlyingName)); }






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
void refresh()
{
	sortModel.refresh();
	getTableHeader().repaint();
}
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
		int col_u = permuteModel.getColMap(col_h);
		
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
		refresh();
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
		int col_u = permuteModel.getColMap(col_h);
		
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
