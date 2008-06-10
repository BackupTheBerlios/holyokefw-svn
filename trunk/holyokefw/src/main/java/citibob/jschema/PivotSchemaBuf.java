/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.swing.table.PivotTableModel;

/**
 *
 * @author citibob
 */
public class PivotSchemaBuf extends PivotTableModel
{
	public PivotSchemaBuf()
	{
		
	}
	@Override
	protected int newCell(int rowM, int colM) {
		SchemaBuf sbDataU = (SchemaBuf)dataU;
		int rowD = sbDataU.insertRow(-1);
		
		// Set the keys
		for (int i=0; i<keyColsM.length; ++i) {
			sbDataU.setValueAt(
				mainU.getValueAt(rowM, keyColsM[i]),
				rowD, keyColsD[i]);
		}
		return rowD;
	}

}
