/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRunner;
import citibob.sql.UpdRunnable;

/**
 *
 * @author citibob
 */
public class DataResource extends Resource
{

public DataResource(ResSet rset, String uversionType, String name) {
	super(rset, uversionType, name);
	editable = true;
}

public void applyPlan(SqlRunner str, final ConnPool pool, final UpgradePlan uplan)
{
	final ResResult rr = load(str, uplan.uversionid0(), uplan.version0());
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		byte[] bytes = rr.bytes;
		for (Upgrader up : uplan.getPath()) {
			DataUpgrader dup = (DataUpgrader)up;
			bytes = dup.upgrade(bytes);
		}
		final byte[] xbytes = bytes;
		Exception e = pool.exec(new citibob.task.DbRunnable() {
		public void run(java.sql.Connection dbb) throws Exception {
			Upgrader[] path = uplan.getPath();
			ResUtil.setResource(dbb, getName(), uplan.uversionid1(), uplan.version1(), xbytes);
		}});
		if (e != null) throw e;
	}});
}
// ========================================================

}
