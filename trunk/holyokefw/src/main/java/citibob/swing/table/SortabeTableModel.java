/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.table;

import java.util.Comparator;

/**
 *
 * @author fiscrob
 */
public interface SortabeTableModel extends JTypeTableModel
{
	/** Set a new SortSpec (and resort). */
	public void setSortSpec(SortSpec spec);
	
	/** @returns Current SortSpec.  The caller may change this as
	 * it wishes, followed by a resort() call. */
	public SortSpec getSortSpec();
	
	/** Re-sort according to the latest SortSpec.  Throws events, etc. */
	public boolean resort();
	
	/** Shows how to sort a particular column */
	public void setComparator(int col, Comparator comp);
	
	/** Convenience function */
	public void setComparator(String scol, Comparator comp);
}
