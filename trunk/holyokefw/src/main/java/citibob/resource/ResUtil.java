/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.RsRunnable;
import citibob.sql.SqlRunner;
import citibob.sql.pgsql.*;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author citibob
 */
public class ResUtil
{

ConnPool pool;

/** Loads resource with largest value < iversion */
public static ResResult getResources(SqlRunner str, String name, int uversionid, int iversion)
{
	final ResResult res = new ResResult();
	String sql =
		" select * from resources" +
		" where name = " + SqlString.sql(name) +
		(uversionid <= 0 ? "" : " and uversionid = " + uversionid) +
		" and iversionid = " +
			" (select max(iversionid) from resources where name = " + SqlString.sql(name) +
			(uversionid <= 0 ? "" : " and uversionid = " + uversionid) + ")";
	str.execSql(sql, new RsRunnable() {
	public void run(SqlRunner str, ResultSet rs) throws Exception {
		res.name = rs.getString("name");
		res.iversion = rs.getInt("iversion");
		res.uversionid = rs.getInt("uversionid");
		res.val = rs.getBytes("val");
	}});
	return res;
}


public static void setResource(Connection dbb, String name, int uversionid, int version, byte[] val)
throws SQLException
{
	PreparedStatement st = dbb.prepareStatement(
		" select w_resource_create(?, ?, ?);" +
		" update resources set val = ?" +
		" where name = ? and uversionid = ? and version = ?;");
	try {
		st.setString(1, name);
		st.setInt(2, uversionid);
		st.setInt(3, version);
		st.setString(5, name);
		st.setInt(6, uversionid);
		st.setInt(7, version);

		st.setBinaryStream(4, new ByteArrayInputStream(val), val.length);

		st.execute();
	} finally {
		try {
			st.close();
		} catch(Exception e) {}
	}

}

/** Looks in database for available versions of each requested resource. */
public static List<Set<Integer>> getAvailableVersions(SqlRunner str, List<ResKey> keys)
{
	
}




/* Startup procedure (check all required resources are there):
 *  1. Get list of all resources we need (look up the ResourceInfo for each resourceid).
 *  2. getRequiredVersion() on each resource-uversionid pair.
 *  3. Make sure that required-version exists in the resource table for all resourceids.
 *     If not, throw an exception, must run setup...
 * 
 * Runtime procedure:
 *  1. getRequiredVersion() on the resource-uversionid in question
 *  2. Load it from the DB.  If up-to-date version where uversionid<>0 doesn't
 *     exist, try an up-to-date version with uversion=0.
 *  3. Cache for later use.
 * 
 * Setup procedure:
 *  1. Get list of all resources we need (look up the ResourceInfo for each resourceid).
 *  2. getRequiredVersion() on each resource-uversionid pair.
 *  3. getAvailableVersions() for each resource-uversionid pair.
 *  4. Set up a GUI allowing the user (for each resource-uversionid, even those
 *     that exist in up-to-date form):
 *      * Upgrade from an older version (choose your upgrade path)
 *      * Re-create the resource from scratch.
 */


}
