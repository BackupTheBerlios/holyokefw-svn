/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.gui.BareBonesOpen;
import citibob.sql.ConnPool;
import citibob.sql.RsRunnable;
import citibob.sql.SqlRunner;
import citibob.sql.UpdRunnable;
import citibob.sql.pgsql.*;
import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author citibob
 */
public class ResUtil
{

ConnPool pool;

public static String delResourceSql(String name, int uversionid, int version)
{
	String sql =
		" delete from resources" +
		" using resourceids rid" +
		" where resources.resourceid = rid.resourceid" +
		" and rid.name = " + SqlString.sql(name) +
		" and uversionid = " + uversionid +
		" and version = " + version;
	return sql;
//	str.execSql(sql);
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

public static void uploadResource(ConnPool pool,
final RtResKey curResKey, final int version, File inFile)
throws SQLException, IOException
{
	// Read resource back from temporary file
	byte[] bytes = new byte[(int)inFile.length()];
	InputStream in = new FileInputStream(inFile);
	in.read(bytes);
	in.close();

	// Store back in database
	Connection dbb = pool.checkout();
	try {
		ResUtil.setResource(dbb,
			curResKey.res.getName(), curResKey.uversionid, version,
			bytes);
	} finally {
		pool.checkin(dbb);
	}
}

public static void saveResource(SqlRunner str,
final RtResKey curResKey, final int version, final File outFile)
{
	// Fetch the resource from the database
	final ResResult rr = ResUtil.getResource(str,
		curResKey.res.getName(), curResKey.uversionid, version);
	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		// Copy resource to the temporary file.
		OutputStream out = new FileOutputStream(outFile);
		out.write(rr.bytes);
		out.close();
	}});	
}
	
//	final RtVers ver = (RtVers)tAvailVersions.getValue();
//	int version = ver.version;
//	ResourcePanel.this

public static void editResource(SqlRunner str, final ConnPool pool,
final Component parentComponent,
final RtResKey curResKey, final int version)
throws IOException
{
	// Get a temporary file
	String name = curResKey.res.getName();
	int dot = name.lastIndexOf('.');
	String suffix = (dot < 0 ? "" : name.substring(dot));
	String prefix = (dot < 0 ? name : name.substring(0,dot));
	final File tmpFile = File.createTempFile(prefix, suffix);
	tmpFile.deleteOnExit();
	saveResource(str, curResKey, version, tmpFile);

	str.execUpdate(new UpdRunnable() {
	public void run(SqlRunner str) throws Exception {
		// Open the resource
		BareBonesOpen.open(tmpFile);

		// Wait for user to edit and save
		int option = JOptionPane.showConfirmDialog(parentComponent,
			"Please press OK after you finish" +
			"editing and saving the resource.",
			"Edit Resource", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			uploadResource(pool, curResKey, version, tmpFile);
		}
	}});
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
