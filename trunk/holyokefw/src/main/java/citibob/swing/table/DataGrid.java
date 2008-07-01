/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

/**
 *
 * @author fiscrob
 */
public interface DataGrid<TT>
{
	public int getColumnCount();
	public int getRowCount();
	public TT getValueAt(int row, int col);


}
