/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRun;
import citibob.sql.UpdTasklet;
import citibob.sql.pgsql.*;
import java.sql.Connection;

/**
 *
 * @author citibob
 */
public abstract class DataUpgrader extends BaseUpgrader
{

public DataUpgrader(Resource resource, int version0, int version1)
	{ super(resource, version0, version1, true); }

/** Does the semantic work of the actual upgrade! */
public abstract byte[] upgrade(byte[] val) throws Exception;

//public abstract void upgrade(Connection dbb, ResResult rr, int uversionid1) throws Exception;


public void upgrade(Connection dbb, ResResult rr, int uversionid1) throws Exception
{
	byte[] newBytes = upgrade(rr.bytes);
	ResUtil.setResource(dbb, resource.getName(), uversionid1, version1, newBytes);
}

public void upgrade(SqlRun str, final ConnPool pool, int uversionid0, final int uversionid1)
throws Exception
{
	final ResResult rr = resource.load(str, uversionid0, version0);
	str.execUpdate(new UpdTasklet() {
	public void run() throws Exception {
		Exception e = pool.exec(new citibob.task.DbTask() {
		public void run(java.sql.Connection dbb) throws Exception {
			upgrade(dbb, rr, uversionid1);
		}});
		if (e != null) throw e;
	}});
}


}
