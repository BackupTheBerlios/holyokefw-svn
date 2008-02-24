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

public DataResource(ResSet rset, String name) { super(rset, name); }

public void applyPath(SqlRunner str, final ConnPool pool, int uversionid, final Upgrader[] path)
{
	int version0 = path[0].version0();
	final ResResult rr = load(str, uversionid, version0);
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		byte[] bytes = rr.bytes;
		for (Upgrader up : path) {
			DataUpgrader dup = (DataUpgrader)up;
			bytes = dup.upgrade(bytes);
		}
		final byte[] xbytes = bytes;
		Exception e = pool.exec(new citibob.task.DbRunnable() {
		public void run(java.sql.Connection dbb) throws Exception {
			int version1 = path[path.length-1].version1();
			ResUtil.setResource(dbb, getName(), rr.uversionid, version1, xbytes);
		}});
		if (e != null) throw e;
	}});
}
// ========================================================

}
