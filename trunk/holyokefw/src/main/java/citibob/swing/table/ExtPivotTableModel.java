/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.jschema.DbBuf;
import citibob.swing.JTypeColTable;
import citibob.swing.typed.SwingerMap;
import citibob.text.SFormat;

/**
 * Glues together a main table and a pivot table
 * @author citibob
 */
public class ExtPivotTableModel
extends MultiJTypeTableModel implements DbBuf
{
DbBuf mainU;
PivotTableModel pivotU;
				// Also: setdisplay status to edited when either part of the table is edited.


public ExtPivotTableModel(DbBuf mainU, PivotTableModel pivotU)
{
	super(mainU, pivotU);
	this.mainU = mainU;
	this.pivotU = pivotU;
}

public void setPivotValFormat(JTypeColTable table, SFormat sfmt)
{
	// Find first pivoted column
	ColPermuteTableModel pm = table.getPermuteModel();
	int firstColU = mainU.getColumnCount();
	int firstCol = pm.getColUInv(firstColU);		// First pivot column in main table
	
	for (int i=firstCol; i<table.getColumnCount(); ++i)
		table.setFormat(i, sfmt);
}

public void setModelU(JTypeColTable table,
String[] xcolNames, String[] xsColMap,
String[] xttColMap,
boolean[] xeditable, boolean pEditable,
SwingerMap smap)
{
	int nmcol = xcolNames.length;
	int npcol = pivotU.getColumnCount();
	
	if (xttColMap == null) xttColMap = new String[nmcol];
	if (xeditable == null) xeditable = new boolean[nmcol];
	
	boolean[] editable = new boolean[nmcol + npcol];
	String[] colNames = new String[nmcol + npcol];
	String[] sColMap = new String[nmcol + npcol];
	String[] ttColMap = new String[nmcol + npcol];
	int i;
	for (i=0; i<nmcol; ++i) {
		colNames[i] = xcolNames[i];
		sColMap[i] = xsColMap[i];
		ttColMap[i] = xttColMap[i];
		editable[i] = xeditable[i];
	}
	for (int j=0; j<npcol; ++j) {
		colNames[i+j] = pivotU.getColumnName(j);
		sColMap[i+j] = pivotU.getColumnName(j);
		ttColMap[i+j] = null;
		editable[i+j] = pEditable;
	}
	
	table.setModelU(this, colNames, sColMap, ttColMap, editable, smap);
}



	/** Mark a row for deletion. */
	public void deleteRow(int rowIndex)
		{ mainU.deleteRow(rowIndex); }

	/** Mark all rows for deletion. */
	public void deleteAllRows()
		{ mainU.deleteAllRows(); }

	public void undeleteRow(int rowIndex)
		{ mainU.undeleteRow(rowIndex); }

	/** Mark all rows for deletion. */
	public void undeleteAllRows()
		{ mainU.undeleteAllRows(); }


}
