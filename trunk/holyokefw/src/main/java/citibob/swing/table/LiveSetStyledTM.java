/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import citibob.swing.typed.SwingerMap;
import citibob.task.ExpHandler;
import citibob.types.JType;
import citibob.util.LiveItem;
import citibob.util.LiveSet;

public abstract class LiveSetStyledTM<RowType extends LiveItem>
extends DelegateStyledTM
implements LiveSet.Listener
{
protected LiveSetTableModel liveTM;
public LiveSetTableModel getLiveTM() { return ((LiveSetTableModel)modelU); }

protected LiveSetStyledTM() {}
public LiveSetStyledTM(ExpHandler expHandler, SwingerMap smap, Object... colSpecs)
	{ init(expHandler, smap, colSpecs); }

protected void init(ExpHandler expHandler, SwingerMap smap, Object... colSpecs)
{
	// Set up basic modelU and model
	int n = colSpecs.length / 3;
	String[] colNames = new String[n];
	JType[] jTypes = new JType[n];
	for (int i=0; i<n; ++i) {
		colNames[i] = (String)colSpecs[i*3];
		jTypes[i] = (JType)colSpecs[i*3+1];
	}
	liveTM = new LiveSetTableModel<RowType>(expHandler, colNames, jTypes) {
	public final Object getValueAt(RowType row, int col) {
		return LiveSetStyledTM.this.getValueAt(row, col);
	}};
	modelU = liveTM;
	
	model = new ColPermuteTableModel(modelU);
	
	// Set up swingers, including custom formats
	RenderEditCols re = new RenderEditCols(this, smap);
	for (int i=0; i<n; ++i) {
		Object fmtSpec = colSpecs[i*3+2];
		if (fmtSpec != null) re.setFormat(i, fmtSpec);
	}
	setRenderEditModel(re);
}
public abstract Object getValueAt(RowType row, int col);
}
