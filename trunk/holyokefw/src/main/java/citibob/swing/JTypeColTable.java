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
import java.awt.*;
import citibob.text.*;
import citibob.types.KeyedModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A table with one type per column.  Integrated with ColPermuteTable, so it's
 * convenient for editing JTypeTableModels.
 * @author citibob
 * @deprecated
 */
public class JTypeColTable
extends SingleSelectStyledTable
{

public JTypeColTable()
{
	super.setHighlightMouseover(false);
}
	
public void setModelU(JTypeTableModel modelU, SwingerMap smap)
{
	DelegateStyledTM stm = new DelegateStyledTM(modelU);
	stm.setDefaultModel(smap);
	setAllEditable(false);
	super.setStyledModel(stm);
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
	DelegateStyledTM stm = new DelegateStyledTM(modelU);
	stm.setColumns(smap, colNames, sColMap, editable);
	super.setStyledModel(stm);
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
	DelegateStyledTM stm = new DelegateStyledTM(modelU);
	stm.setColumns(smap, colNames, sColMap, editable);
	stm.setTooltipModel(new ColPermuteTableModel(modelU, null, ttColMap));
}

public JTypeTableModel getModelU() { return styledModel.getModelU(); }


// =====================================================
// From the old ColPermuteTable
public void setAllEditable(boolean edit)
{
	((DelegateStyledTM)styledModel).setEditableModel(new ConstDataGrid(Boolean.FALSE));
}

/** Sets a render/edit on a colum, by UNDERLYING column name. */
public void setFormatU(String underlyingName, Object fmt)
{
	DelegateStyledTM stm = (DelegateStyledTM)styledModel;
	RenderEditCols re = (RenderEditCols)stm.getRenderEditModel();
	re.setFormatU(underlyingName, fmt);
}

}
