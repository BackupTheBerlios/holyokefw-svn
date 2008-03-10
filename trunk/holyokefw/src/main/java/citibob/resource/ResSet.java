/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.RsRunnable;
import citibob.sql.RssRunnable;
import citibob.sql.SqlRunner;
import citibob.sql.pgsql.SqlString;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Set of resources used by the current application.
 * @author citibob
 */
public abstract class ResSet
{
protected boolean dbbExists = false;		// true if the database has been verified to exist.
public boolean dbbExists() { return dbbExists; }

protected ClassLoader jarClassLoader;	// Used to read resources from JAR file.
protected String jarPrefix;
// "offstage/resources/"

/** Constructor of app-specific res set will add to this. */
protected TreeMap<String,Resource> resources = new TreeMap();

protected int sysVersion;

public ResSet(SqlRunner str, int sysVersion, ClassLoader jarClassLoader, String jarPrefix)
throws SQLException
{
	this.jarClassLoader = jarClassLoader;
	this.jarPrefix = jarPrefix;
	this.sysVersion = sysVersion;

	// Make sure the database exists at all!
	// We could be starting from a blank database.
	String sql =
		" select count(*) from  information_schema.tables" +
		" where table_name in ('resources', 'resourceids');";
	str.execSql(sql, new RsRunnable() {
	public void run(SqlRunner str, ResultSet rs) throws Exception {
		rs.next();
		int count = rs.getInt(1);
		switch(count) {
			case 2 : dbbExists = true;
				break;
			case 1 : // Error: one table exists, not the other!
				throw new SQLException("Corrupt database.  Only one of resources and resourceids exists.");
			case 0 : // New database; fake our available versions!
				dbbExists = false;
			break;
		}
	}});

}

public ClassLoader getJarClassLoader() { return jarClassLoader; }
public String getJarPrefix() { return jarPrefix; }

public void add(Resource res)
	{resources.put(res.getName(), res); }
Resource get(String name)
	{ return resources.get(name); }


public ResResult load(SqlRunner str, String name, int uversionid)
{
	Resource res = get(name);
	int reqVersion = res.getRequiredVersion(sysVersion);
	return res.load(str, uversionid, reqVersion);
}

public void saveResource(SqlRunner str, String name, int uversionid,
final File outFile)
{
	Resource res = get(name);
	int reqVersion = res.getRequiredVersion(sysVersion);
	ResUtil.saveResource(str, res.getName(), uversionid, reqVersion, outFile);
}


/* List of resource-uversionid pairs required by this app at this time. */
//public abstract List<RtResKey> getRequired();
/** Set of resource-uversionid pairs relevant to the app at this time.  By default,
 base it on the Resources registered with this class.
 @param useDbb if false, DO NOT try to read the database (it doesn't exist)*/
public SortedSet<RtResKey> newRelevant()
{
	SortedSet<RtResKey> ret = new TreeSet();

	for (Resource res : resources.values()) {
		ret.add(new RtResKey(res));
	}
	return ret;
}



public void createAllResourceIDs(SqlRunner str)
{
	if (!dbbExists) return;

	StringBuffer sql = new StringBuffer();
	for (Resource res : resources.values()) {
		sql.append("select w_resourceid_create(" +
			SqlString.sql(res.getName()) + ");\n");
	}
	str.execSql(sql.toString(), new RssRunnable() {
	public void run(citibob.sql.SqlRunner str, java.sql.ResultSet[] rss) throws Exception {
		int i = 0;
		for (Resource res : resources.values()) {
			ResultSet rs = rss[i++];
			rs.next();
			res.resourceid = rs.getInt(1);
		}
		
	}});
}

}
