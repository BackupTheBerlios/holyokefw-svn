/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger;
import citibob.swing.typed.Swinger.RenderEdit;
import citibob.text.SFormat;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author fiscrob
 */
public class DelegateStyledTM extends StyledTableModel
{

DataGrid<String> tooltipModel;
//DataGrid<SFormat> sFormatModel;
//DataGrid<Swinger> swingerModel;
DataGrid<RenderEdit> renderEditModel;
DataGrid<Color> bgColorModel;
DataGrid<Color> fgColorModel;
DataGrid<Font> fontModel;
DataGrid<Boolean> editableModel;

public DelegateStyledTM(JTypeTableModel model)
	{ super(model); }
public DelegateStyledTM(JTypeTableModel modelU, JTypeTableModel model)
	{ super(modelU, model); }
// ==========================================================
@Override
public Color getBgColor(int row, int col) {
	if (bgColorModel == null) return super.getBgColor(row, col);
	return bgColorModel.getValueAt(row, col);
}

@Override
public Color getFgColor(int row, int col) {
	if (fgColorModel == null) return super.getFgColor(row, col);
	return fgColorModel.getValueAt(row, col);
}

@Override
public Font getFont(int row, int col) {
	if (fontModel == null) return super.getFont(row, col);
	return fontModel.getValueAt(row, col);
}

@Override
public RenderEdit getRenderEdit(int row, int col) {
	if (renderEditModel == null) return super.getRenderEdit(row, col);
	return renderEditModel.getValueAt(row, col);
}

@Override
public String getTooltip(int row, int col) {
	if (tooltipModel == null) return super.getTooltip(row, col);
	return tooltipModel.getValueAt(row, col);
}

@Override
public boolean isEditable(int row, int col) {
	if (editableModel == null) return super.isEditable(row, col);
	return editableModel.getValueAt(row, col).booleanValue();
}
// ==========================================================


	public void setBgColorModel(DataGrid<Color> bgColorModel) {
		this.bgColorModel = bgColorModel;
	}

	public void setEditableModel(DataGrid<Boolean> editableModel) {
		this.editableModel = editableModel;
	}

	public void setFgColorModel(DataGrid<Color> fgColorModel) {
		this.fgColorModel = fgColorModel;
	}

	public void setFontModel(DataGrid<Font> fontModel) {
		this.fontModel = fontModel;
	}

	public void setRenderEditModel(DataGrid<RenderEdit> renderEditModel) {
		this.renderEditModel = renderEditModel;
	}

	public void setTooltipModel(DataGrid<String> tooltipModel) {
		this.tooltipModel = tooltipModel;
	}

// ==========================================================
public void 


}
