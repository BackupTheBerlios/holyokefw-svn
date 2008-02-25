/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRunner;
import citibob.sql.UpdRunnable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public abstract class BaseUpgrader implements Upgrader
{

protected int version0, version1;
protected Resource resource;

public BaseUpgrader(Resource resource, int version0, int version1)
{
	this.resource = resource;
	this.version0 = version0;
	this.version1 = version1;
}
//public String getName() { return resource; }
public Resource getResource() { return resource; }
public int version0() { return version0; }

public int version1() { return version1; }

public abstract void upgrade(Connection dbb, ResResult rr, int uversionid1) throws Exception;

public void upgrade(SqlRunner str, final ConnPool pool, int uversionid0, final int uversionid1)
{
	final ResResult rr = resource.load(str, uversionid0, version0);
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		Exception e = pool.exec(new citibob.task.DbRunnable() {
		public void run(java.sql.Connection dbb) throws Exception {
			upgrade(dbb, rr, uversionid1);
		}});
		if (e != null) throw e;
	}});
}

public String toString()
{
	return getClass().getSimpleName() + "(" + version0 + " -> " + version1 + ")";
}
}
