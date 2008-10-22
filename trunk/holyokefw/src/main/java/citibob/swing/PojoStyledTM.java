/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing;

import citibob.swing.table.LabelRowStyledTM;
import citibob.swing.table.PojoTM;
import citibob.swing.typed.SwingerMap;

/**
 *
 * @author fiscrob
 */
public class PojoStyledTM extends LabelRowStyledTM
{

public PojoStyledTM(SwingerMap smap, Object... fmtSpecs)
{
	super.init(smap, fmtSpecs);
	super.SetModelU(new PojoTM());
}

public void setPojo(Object pojo)
{
	((PojoTM)getModelU()).setPojo(pojo);
	super.refreshModel();
}

}
