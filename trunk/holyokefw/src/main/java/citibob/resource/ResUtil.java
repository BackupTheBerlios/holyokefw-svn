/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.RsRunnable;
import citibob.sql.SqlDateType;
import citibob.sql.SqlRunner;
import citibob.sql.SqlTypeSet;
import citibob.sql.pgsql.*;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author citibob
 */
public class ResUtil
{

ConnPool pool;

public static void delResource(SqlRunner str, String name, int uversionid, int version)
{
	String sql =
		" delete from resources" +
		" using resourceids rid" +
		" where resources.resourceid = rid.resourceid" +
		" and rid.name = " + SqlString.sql(name) +
		" and uversionid = " + uversionid +
		" and version = " + version;
	str.execSql(sql);
}
/** Loads resource with largest value < version */
public static ResResult getResource(SqlRunner str, String name, int uversionid, int version)
{
	final ResResult res = new ResResult();
	res.name = name;
	res.version = -1;
	res.uversionid = uversionid;
	res.bytes = null;
	if (version < 0) return res;
	
	String sql =
		" select rid.name,r.* from resources r, resourceids rid" +
		" where rid.resourceid = r.resourceid" +
		" and rid.name = " + SqlString.sql(name) +
		" and uversionid = " + uversionid +
		" and version = " + version;
//			" (select max(version) from resources r, resourceids rid" +
//			" where rid.resourceid = r.resourceid" +
//			" and name = " + SqlString.sql(name) +
//			" and uversionid = " + uversionid +
//			" and version <= " + version + )";
	str.execSql(sql, new RsRunnable() {
	public void run(SqlRunner str, ResultSet rs) throws Exception {
		if (rs.next()) {
			res.name = rs.getString("name");
			res.version = rs.getInt("version");
			res.uversionid = rs.getInt("uversionid");
			res.bytes = rs.getBytes("val");
		}
	}});
	return res;
}


public static void setResource(Connection dbb, String name, int uversionid, int version, byte[] val)
throws SQLException
{
	PreparedStatement st = dbb.prepareStatement(
		" select w_resource_create(?, ?, ?);" +
		" update resources set lastmodified=now(), val = ?" +
		" where resourceid = (select resourceid from resourceids where name = ?)" +
		" and uversionid = ?" +
		" and version = ?;");
	try {
		st.setString(1, name);
		st.setInt(2, uversionid);
		st.setInt(3, version);
		st.setString(5, name);
		st.setInt(6, uversionid);
		st.setInt(7, version);

		st.setBinaryStream(4, new ByteArrayInputStream(val), val.length);

		st.execute();
		dbb.commit();
	} finally {
		try {
			st.close();
		} catch(Exception e) {}
	}

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
 *  3. fetchAvailableVersions() for each resource-uversionid pair.
 *  4. Set up a GUI allowing the user (for each resource-uversionid, even those
 *     that exist in up-to-date form):
 *      * Upgrade from an older version (choose your upgrade path)
 *      * Re-create the resource from scratch.
 */


}
