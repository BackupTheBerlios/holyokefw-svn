/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.RssRunnable;
import citibob.sql.SqlRunner;
import citibob.sql.pgsql.SqlString;
import java.sql.ResultSet;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Set of resources used by the current application.
 * @author citibob
 */
public abstract class ResSet
{

protected ClassLoader jarClassLoader;	// Used to read resources from JAR file.
protected String jarPrefix;
// "offstage/resources/"

/** Constructor of app-specific res set will add to this. */
protected TreeMap<String,Resource> resources = new TreeMap();

protected int sysVersion;

public ResSet(int sysVersion, ClassLoader jarClassLoader, String jarPrefix) {
	this.jarClassLoader = jarClassLoader;
	this.jarPrefix = jarPrefix;
	this.sysVersion = sysVersion;
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
	return res.load(str, uversionid, sysVersion);
}




/* List of resource-uversionid pairs required by this app at this time. */
//public abstract List<ResKey> getRequired();
/** Set of resource-uversionid pairs relevant to the app at this time.  By default,
 base it on the Resources registered with this class. */
public SortedSet<ResKey> getRelevant()
{
	SortedSet<ResKey> ret = new TreeSet();

	for (Resource res : resources.values()) {
		ret.add(new ResKey(res));
	}
	return ret;
}

public void createAllResourceIDs(SqlRunner str)
{
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
