/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.RemoveSqlCommentsReader;
import citibob.sql.SqlRunner;
import citibob.sql.pgsql.SqlString;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

/**
 *
 * @author citibob
 */
public class BaseDbbUpgrader extends BaseUpgrader
{

String[] suffixes;		// Applies a file with each suffix in turn.

public BaseDbbUpgrader(Resource resource, int version0, int version1,
boolean backCompatible, String[] suffixes)
{
	super(resource, version0, version1, backCompatible);
	this.suffixes = suffixes;
}

public String getDescription() {
	return "Upgrade " + resource.getName() + " from version " +version0 + " to " + version1;
	
}

public void upgrade(SqlRunner str, final ConnPool pool, int uversionid0, final int uversionid1)
throws Exception
{
	ResSet rset = resource.getResSet();
	String name = resource.getName();

	StringBuffer sql = new StringBuffer("BEGIN;\n");
	for (String suff : suffixes) {
		// Create JAR resource name (with version #)
		String rname;
		String svers = (version0 == -1 ? "" : "-" + version0) + "-" + version1;
		int dot = name.lastIndexOf('.');
		if (dot < 0) rname = name + "-" + suff + svers;
		else rname = name.substring(0,dot) + "-" + suff + svers + name.substring(dot);

		// File doesn't exist; read from inside JAR file instead.
		String resourceName = rset.getJarPrefix() + rname;
	System.out.println("Loading resource of name: " + resourceName);
		InputStream iin = new BufferedInputStream(rset.getJarClassLoader().getResourceAsStream(resourceName));

		// Read the SQL
		Reader in = new RemoveSqlCommentsReader(new InputStreamReader(iin));
		char[] buf = new char[8192];
		int n;
		StringWriter out = new StringWriter();
		while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
		in.close();

		// Upgrade the main tables
		sql.append(out.toString() + ";\n");
	}
	// send it off...
	sql.append(
		// Record the upgrade
		" select w_resourceid_create(" + SqlString.sql(resource.getName()) + ");\n" +
		" select w_resource_create(" + SqlString.sql(resource.getName()) + ", " +
			uversionid1 + ", " + version1 + ");\n" +
		" update resources set lastmodified=now()" +
		" where resourceid = (select resourceid from resourceids where name = " +
			SqlString.sql(resource.getName()) + ")" +
		" and uversionid = " + uversionid1 +
		" and version = " + version1 + ";\n");
	if (!backCompatible && version0 != -1) {
		// Delete all previous version if this upgrade is not backwards compatible.
		sql.append(//ResUtil.delResourceSql(resource.getName(), uversionid1, version0));
			" delete from resources" +
			" using resourceids rid" +
			" where resources.resourceid = rid.resourceid" +
			" and rid.name = " + SqlString.sql(name) +
			" and uversionid = " + uversionid1 +
			" and version < " + version1);

	}
	sql.append("COMMIT;\n");
	str.execSql(sql.toString());

	// Do each upgrade step separately.
	str.flush();
}

public String toString()
{
	return getClass().getSimpleName() + "(" + version0 + " -> " + version1 + ")";
}
}
