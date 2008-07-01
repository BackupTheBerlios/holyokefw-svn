/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.Swinger.RenderEdit;
import citibob.swing.typed.SwingerMap;
import citibob.text.SFormatMap;
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

public DelegateStyledTM(JTypeTableModel modelU)
	{ super(modelU); }
//public DelegateStyledTM(JTypeTableModel modelU, JTypeTableModel model)
//	{ super(modelU, model); }
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
/** @param fmtSpecs.  Array of blocks of four:
<nl>
<li>Underlying Column Name (String)</li>
<li>Visible Column Name (String).  If null: use Underlying name.</li>
<li>Editable (Boolean).  If null: use editable from modelU </li>
<li>Format Spec: Swinger, SFormat, JType, Class, etc.  If null: get from SwingerMap.</li>
 </nl>
 * 
 */
public void setColumns(SwingerMap smap, Object... fmtSpecs)
{
	int n = fmtSpecs.length / 4;
	
	// Set up the ColPermuteTableModel
	String[] colMap = new String[n];
	String[] colNames = new String[n];
	for (int i=0; i<n; ++i) {
		String nameU = (String)fmtSpecs[i*4];
		String name = (String)fmtSpecs[i*4+1];
		if (name == null) name = nameU;
		colMap[i] = nameU;
		colNames[i] = name;
	}
	model = new ColPermuteTableModel(modelU, colNames, colMap);
	
	// Set up editable
	DataCols<Boolean> editable = new DataCols(Boolean.class, n);
	for (int i=0; i<n; ++i) {
		Boolean ed = (Boolean)fmtSpecs[i*4+2];
		// Override editable only if we've given a non-null
		if (ed == null) ed = super.isEditable(0, i);
		editable.data[i] = ed;
	}
	this.setEditableModel(editable);
	
	// Set of swingers / types / etc.
	RenderEditDataCols re = new RenderEditDataCols(this, smap);
	for (int i=0; i<n; ++i) {
		Object spec = fmtSpecs[i*4+3];
		// Override result from constructor only if we've specified something.
		if (spec != null) re.setFormat(i, spec);
	}
	this.setRenderEditModel(re);
}

/** @param fmtSpecs.  Array of blocks of four:
<nl>
<li>Underlying column in modelU that provides tooltip (String)</li>
<li>Format Spec: Swinger, SFormat, JType, Class, etc.  If null: get from SwingerMap.</li>
 </nl>
 * fmtSpecs must specifiy the same number of columns as is in model.
 * 
 */
public void setToolTips(SFormatMap smap, Object... fmtSpecs)
{
	int n = fmtSpecs.length / 2;
	String[] colMap = new String[n];
	for (int i=0; i<n; ++i) {
		colMap[i] = (String)fmtSpecs[i*2];
	}
	StringTableModel tt = new StringTableModel(
		modelU, colMap, null, false, smap);
	
	// Set up non-default formatting
	for (int i=0; i<n; ++i) {
		Object spec = fmtSpecs[i*2+1];
		// Override result from constructor only if we've specified something.
		if (spec != null) tt.setFormat(i, spec);
	}
	this.setTooltipModel(tt);
}

}
