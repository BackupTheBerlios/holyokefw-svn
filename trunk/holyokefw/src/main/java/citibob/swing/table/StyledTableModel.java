/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger.RenderEdit;
import citibob.util.ObjectUtil;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author fiscrob
 */
public class StyledTableModel {

protected JTypeTableModel modelU;		// Some underlying table model

/** The model to display; may be a projection of modelU, may be the same. */
protected JTypeTableModel model;

public StyledTableModel(JTypeTableModel modelU)
{
	this.modelU = modelU;
	this.model = new ColPermuteTableModel(modelU);
}
public StyledTableModel(JTypeTableModel modelU, JTypeTableModel model)
{
	this.modelU = modelU;
	this.model = model;
}
/** The main table model */
public JTypeTableModel getModel() { return model; }
/** The main table model */
public JTypeTableModel getModelU() { return modelU; }

///** Formatting extensions */
//public SFormat getSFormat(int row, int col)
//{
//	Swinger swinger = getSwinger(row, col);
//	if (swinger != null) return swinger.getSFormat();
//	return null;
//}
//public Swinger getSwinger(int row, int col) { return null; }
//public abstract TableCellRenderer getRenderer(int row, int col);
//public abstract TableCellEditor getEditor(int row, int col);
public RenderEdit getRenderEdit(int row, int col) { return null; }

public String getTooltip(int row, int col) { return null; }
public Color getBgColor(int row, int col) { return getExtBgColor(row, col); }
public Color getFgColor(int row, int col) { return null; }
/** Background color for rows BEYOND the end of the TableModel; used to
 * paint zebra stripes below the table data. */
public Color getExtBgColor(int row, int col) { return null; }
/** Font must be the same size as the font previously set via StyledTable.setFont() */
public Font getFont(int row, int col) { return null; }
public boolean isEditable(int row, int col)
	{ return model.isCellEditable(row, col); }

//public int getRowOfValue(Object val, int col)
//	{ return getRowOfValue(val, col, getModel()); }
//public int getRowOfValueU(Object val, int colU)
//	{ return getRowOfValue(val, colU, getModelU()); }


}
