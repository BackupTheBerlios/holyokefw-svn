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
package citibob.swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import citibob.text.*;
import citibob.swing.table.*;
import citibob.swing.typed.*;
import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swingers.*;
import citibob.types.*;
//import de.chka.swing.components.*;

public class StyledTable extends JTable
implements MouseListener, MouseMotionListener
{

protected StyledTM styledModel = dummyStyledModel;
SortableTableModel sortModel;

private boolean highlightMouseover = false;		// SHould we highlight rows when mousing over?

/** Should we fill the ScrollPane with our table, even if there aren't
enough data rows to warrant it?
See: http://nadeausoftware.com/articles/2008/01/java_tip_how_add_zebra_background_stripes_jtable
 */
private boolean fillViewport = true;			// 

/** Size of the default font --- used to calculate row heights */
FontMetrics tableFontMetrics;

int mouseRow = -1;		// Row the mouse is currently hovering over.

/** Dummy for GUI builder */
static final JTypeTableModel nullTableModel = new DefaultJTypeTableModel();
static final StyledTM dummyStyledModel = new StyledTM(nullTableModel);


public StyledTable()
{
	// See: http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=4709394
	// Unfortunately, this "fix" breaks the JDateChooser date editor.
	// As soon as user selects a date, the focus is lost from the table,
	// BEFORE the JDateChooser has had a chance to update itself...
	// TODO: For now, I won't use it, but once JDateChooser is fixed, I'll turn
	// it back on.
	this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//	setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	setStyledModel(dummyStyledModel);
}

// ===============================================================
// Fundamental stuff for drawing the JTable and interacting with the model
public void setStyledTM(StyledTM stm)
{
	this.styledModel = stm;
	
	// Set up table sorting stuff
	JTypeTableModel modelU = stm.getModelU();
	if (modelU instanceof SortableTableModel) {
		sortModel = (SortableTableModel)modelU;
		
//		// Set comparators in sortModel
//		if (ext.getCompModelU() != null)
//			sortModel.setComparators(ext.getCompModelU());
		
		// Sort when user clicks headers
		JTableHeader head = getTableHeader();
		head.addMouseListener(new SortableMouseHandler());
		head.setDefaultRenderer(
			new SortableHeaderRenderer(
			head.getDefaultRenderer()));
	}
	
	
	super.setModel(stm.getModel());
}

public StyledTM getStyledTM()
	{ return styledModel; }

public TableCellRenderer getCellRenderer(int row, int col)
{
	RenderEdit re = styledModel.getRenderEdit(row, col);
	if (re != null) return re.getRenderer(styledModel.isEditable(row, col));
	return super.getCellRenderer(row, col);

//	TableCellRenderer ret = ext.getRenderer(row, col);
//	if (ret != null) return ret;
//	return super.getCellRenderer(row, col);
}

public TableCellEditor getCellEditor(int row, int col)
{
	RenderEdit re = styledModel.getRenderEdit(row, col);
	if (re != null) return re.getEditor();
	return super.getCellEditor(row, col);
	
//	TableCellEditor ret = ext.getEditor(row, col);
//	if (ret != null) return ret;
//	return super.getCellEditor(row, col);
}

// Stuff to highlight on mouseover
// See: http://forum.java.sun.com/thread.jspa?threadID=280692&messageID=1091824
// TODO: Actually, we need to use different colors (and fonts) if this is being
// used in a popup.  We should subclass for that...  But it's OK for now.
Color cTextHighlightBg = UIManager.getDefaults().getColor("List.selectionBackground");
Color cTextBg = UIManager.getDefaults().getColor("List.background");
Color cTextHighlightFg = UIManager.getDefaults().getColor("List.selectionForeground");
Color cTextFg = UIManager.getDefaults().getColor("List.foreground");
public Component prepareRenderer(TableCellRenderer renderer, int row, int col)
{
	
	Component c = super.prepareRenderer(renderer, row, col);

	// Tooltips
	if (c instanceof JComponent) {
		JComponent jc = (JComponent)c;
		String ttip = styledModel.getTooltip(row, col);
		jc.setToolTipText(ttip);
	}

	// Highlight row the mouse is over
	if (row == mouseRow || this.getSelectionModel().isSelectedIndex(row)) {
		c.setBackground(cTextHighlightBg);
		c.setForeground(cTextHighlightFg);
	} else {
		Color color;
		color = styledModel.getBgColor(row, col);
		c.setBackground(color == null ? cTextBg : color);
		color = styledModel.getFgColor(row, col);
		c.setForeground(color == null ? cTextFg : color);
	}
	
	// Set up the font
	Font font = styledModel.getFont(row, col);
	if (font != null) c.setFont(font);
	
	return c;
}
// ==========================================================
// Allow font changes (and keep track of font metrics)
public void setFont(Font tableFont)
{
//	this.tableFont = tableFont;
	super.setFont(tableFont);
	tableFontMetrics = getFontMetrics(tableFont);
}
/** @Override
 * Adjust the height of the rows based on our chosen font.
 */
public int getRowHeight()
{
	if (tableFontMetrics == null) return super.getRowHeight();
	return tableFontMetrics.getHeight();
}
// ==========================================================
// Highlight rows when the mouse is over them
public void setHighlightMouseover(boolean b)
{
	if (highlightMouseover == b) return;

	highlightMouseover = b;
	if (b) {
		this.addMouseListener(this); //new MyMouseAdapter());
		this.addMouseMotionListener(this); //new MyMouseMotionAdapter());
	} else {
		this.removeMouseListener(this); //new MyMouseAdapter());
		this.removeMouseMotionListener(this); //new MyMouseMotionAdapter());		
	}
}
public boolean isHighlightMouseover() { return highlightMouseover; }
// --------------------------------------
// MouseListener
public void mouseClicked(MouseEvent e) {}
public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {
	if (!highlightMouseover) return;

	JTable aTable =  (JTable)e.getSource();
	mouseRow = -1;
	aTable.repaint();
}
// --------------------------------------
// MouseMotionListener
public void mouseDragged(MouseEvent e) {}
public void mouseMoved(MouseEvent e) {
	if (!highlightMouseover) return;
	
	JTable aTable =  (JTable)e.getSource();
	int oldRow = mouseRow;
	mouseRow = aTable.rowAtPoint(e.getPoint());
//	itsColumn = aTable.columnAtPoint(e.getPoint());
	if (oldRow != mouseRow) aTable.repaint();
}

// ==========================================================
// Handle the fillViewport flag
public boolean isFillViewport()
	{ return fillViewport; }

public void setFillViewport(boolean fillViewport)
	{ this.fillViewport = fillViewport; }

/** Force the table to fill the viewport's height, if fillViewport. */
public boolean getScrollableTracksViewportHeight( )
{
	if (!fillViewport) return false;
	
	final java.awt.Component p = getParent( );
	if ( !(p instanceof javax.swing.JViewport) )
		return false;
	return ((javax.swing.JViewport)p).getHeight() > getPreferredSize().height;
}
// ==========================================================
//// Handle the selected row (and data lookup in it)
///** Returns the row a value is found on (or -1 if no such row) */
//public int rowOfValue(Object val, int col)
//	{ return rowOfValue(val, col, getModel()); }
//
///** Returns the row a value is found on (or -1 if no such row) */
//protected int rowOfValue(Object val, int col, TableModel model)
//{
//	for (int i=0; i<model.getRowCount(); ++i) {
//		Object rval = model.getValueAt(i, col);
//		boolean eq = (rval == null ? val == null : val.equals(rval));
//		if (eq) return i;
//	}
//	return -1;
//}

// =======================================================================





//private boolean isHighlightSelected = true;		// Should we highlight selected rows?
//public void setHighlightSelected(boolean b) { this.isHighlightSelected = b; }
//public boolean getHighlightSelected() { return this.isHighlightSelected; }


//RowHeightUpdater rhu;


//public void setModel(TableModel model)
//{
//	super.setModel(model);
//	if (model instanceof CitibobTableModel) {
//		CitibobTableModel ctm = (CitibobTableModel)model;
////		rhu = new RowHeightUpdater(this, ctm.getPrototypes());
////		rhu.setEnabled(true);
//	}
//}

public JTypeTableModel getCBModel()
{
	TableModel m = super.getModel();
	return (JTypeTableModel)m;
}


public void setRenderEdit(int colNo, Swinger swinger)
{
	if (swinger == null) return;
	Swinger.RenderEdit re = swinger.newRenderEdit();
	setRenderEdit(colNo, re);
}

public void setRenderEdit(int colNo, KeyedModel kmodel)
{
	setRenderEdit(colNo,
		new KeyedRenderEdit(kmodel));
}

public boolean isCellEditable(int row, int col)
{
	return getStyledTM().isEditable(row, col);
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderEdit(int colNo, Swinger.RenderEdit re)
{
	if (re == null) return;		// Don't change, if we don't know what to set it TO.
	
	TableColumn col = getColumnModel().getColumn(colNo);
	TableCellRenderer rr = re.getRenderer(isCellEditable(0, colNo));
		if (rr != null) col.setCellRenderer(rr);
	TableCellEditor ee = re.getEditor();
		if (ee != null) col.setCellEditor(ee);
}

//public void setRenderEdit(int colNo, TypedWidget tw)
//{
//	TableColumn col = getColumnModel().getColumn(colNo);
//	col.setCellRenderer(new SFormatRenderer(sformat));
//	JTypedTextField tw = new JTypedTextField();
//	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
//	col.setCellEditor(new TypedWidgetEditor(tw));
//}

/** Sets a text-based renderer and editor pair at once, for a column,
without going through Swingers or anything.  Works for simpler text-based
renderers and editors ONLY. */
public void setFormat(int colNo, SFormat sformat)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(new SFormatRenderer(sformat));
	JTypedTextField tw = new JTypedTextField();
	tw.setJType((JType)null, sformat);		// We don't really know about JTypes at CitibobJTable anyway
	col.setCellEditor(new TypedWidgetEditor(tw));
}

public void setFormat(int colNo, java.text.Format fmt)
{
	SFormat sfmt = (fmt instanceof NumberFormat ?
		new FormatSFormat(fmt, "", SFormat.RIGHT) :
		new FormatSFormat(fmt, "", SFormat.LEFT));
	setFormat(colNo, sfmt);
}

/** Sets up a renderer and editor based on a format string.  Works for a small
number of well-known types, this is NOT general. */
public void setFormat(int colNo, String fmtString)
{
	Class klass = getModel().getColumnClass(colNo);
	Format fmt = FormatUtils.newFormat(klass, fmtString);
//if (fmt == null) {
//	Class klass3 = getModel().getColumnClass(colNo);	
//}
	setFormat(colNo, fmt);
}

/** Sets a renderer and editor pair at once, for a column. */
public void setRenderer(int colNo, TableCellRenderer re)
{
	TableColumn col = getColumnModel().getColumn(colNo);
	col.setCellRenderer(re);
}

// ======================================================================
/** NOTE: Third Party Code.
 * Mouse Handler is Copyright (c) 1995 - 2008 by Sun Microsystems.
 * See ArrowIcon.java for full copyright notice. */
private class SortableMouseHandler extends MouseAdapter {
public void mouseClicked(MouseEvent e) {
	JTableHeader h = (JTableHeader) e.getSource();
	TableColumnModel columnModel = h.getColumnModel();
	int viewColumn = columnModel.getColumnIndexAtX(e.getX());
	int col_h = columnModel.getColumn(viewColumn).getModelIndex();
	if (col_h == -1) return;
	
	// Find this column in the main model (or sort) table
//	int col_u = permuteModel.getColU(col_h);
	int col_u = styledModel.getModel().getColU(col_h);

	// Obtain current sorting for possible change
	SortSpec spec = sortModel.getSortSpec();
	int dir = spec.getSortDir(col_u);
	if (!e.isControlDown()) {
		spec.clear();
	}

	// Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or 
	// {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed. 
	dir = dir + (e.isShiftDown() ? -1 : 1);
	dir = (dir + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
	spec.setSortDir(col_u, dir);
System.out.println("Sort by column " + col_u + " direction " + dir);
	
	// Do the refresh
	sortModel.resort();
	getTableHeader().repaint();
	fireSortChanged(spec);
}}
void fireSortChanged(SortSpec spec)
{
	firePropertyChange("sortSpec", null, spec.getStringVal());
}
public void setSortString(String sspec)
{
	if (!(getStyledTM().getModelU() instanceof SortableTableModel)) return;
	SortableTableModel modelU = (SortableTableModel)getStyledTM().getModelU();
	modelU.getSortSpec().setStringVal(sspec);
}
public String getSortString()
{
	SortSpec spec = getSortSpec();
	if (spec == null) return null;
	return getSortSpec().getStringVal();	
}
public SortSpec getSortSpec()
{
	if (!(getStyledTM().getModelU() instanceof SortedTableModel))
		return null;
	SortedTableModel modelU = (SortedTableModel)getStyledTM().getModelU();
	return modelU.getSortSpec();
}

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
		int col_u = styledModel.getModel().getColU(col_h);
//		int col_u = permuteModel.getColU(col_h);
		
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
//// ==========================================================
//// Toltips
///** Set the tooltips to be used */
//public void setTTModel(JTypeTableModel ttModel)
//{
//	this.ttModel = ttModel;
//}
///** Override this to do tooltips in custom manner.  For now, we return the "tooltip column" */
//public String getTooltip(int row, int col)
//{
//	if (ttModel == null) return null;
//	return (String)ttModel.getValueAt(row, col);
//}
// ==========================================================

// ==========================================================


// ================================================================


// =====================================================================
// ================================================================
}
