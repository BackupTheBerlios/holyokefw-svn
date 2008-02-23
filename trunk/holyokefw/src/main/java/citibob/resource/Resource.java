/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRunner;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author citibob
 */
public abstract class Resource
{

String name;
protected boolean essential = false;
protected ResSet rset;

public ResSet getResSet() { return rset; }


public Resource(ResSet rset, String name)
{ this(rset, name, false); }

public Resource(ResSet rset, String name, boolean essential)
{
	this.rset = rset;
	this.name = name;
	this.essential = essential;
}


public String getName() { return name; }



TreeSet<Integer> versions = new TreeSet();	// All available versions (i.e. those referenced by an Upgrader)
Map<Integer,Node> nodes;		// Each node has a set of upgraders with same version0

// =============================================================
static class Version1Comparator implements Comparator<Upgrader> {
public int compare(Upgrader a, Upgrader b) {
	return a.version1() - b.version1();
}}
static Comparator<Upgrader> version1Comparator = new Version1Comparator();
static class Node extends TreeSet<Upgrader>
{
	public Node() { super(version1Comparator); }
}
// =============================================================
Node makeNode(int version0)
{
	Node node = nodes.get(version0);
	if (node == null) {
		node = new Node();
		nodes.put(version0, node);
	}
	return node;
}
public void add(Upgrader up)
{
	versions.add(up.version0());
	versions.add(up.version1());
	Node node = makeNode(up.version0());
	node.add(up);
}
// -------------------------------------------------

///** Gets a (sorted) list of all the versions we've seen. */
//TreeSet<Integer> getVersions() { return versions; }

/** Plain and simple.
 Question: should this always prefer paths staring with a higher version0
 over paths with a lower version0, even if the one with higher version0 had
 a greater length?  Probably...
 Or maybe it should just return ALL the paths that it could find, the shortest
 one from each version0*/
private Upgrader[] getPath(int version0, int version1)
{
	return null;
}

/** @return Finds a set of upgraders to carry out a desired upgrade ---
 * or null if no such path exists.  Uses Dijkstra's algorithm.  Prefers steps that
 * make large version leaps over small version leaps (i.e. find the shortest path
 * in terms of number of upgraders required).
 
 * @param versions0 Set of versions we wish to start from.  Includes -1 if we want
 * to consider just creating the resource.  versions0=-1 should be done in its own
 * separate getPath(), and only if no upgrader was found...
 * @param version1
 * @param allowCreation if true, we will consider creator paths if we cannot find any
 * @return
 */
public Upgrader[] getUpgradePath(int version0, int version1, boolean allowCreation)
{
	Upgrader[] path = getPath(version0, version1);
	if (path != null) return path;
	return getPath(-1, version1);
}

public Upgrader[] getCreatorPath(int version1)
{
	return getPath(-1, version1);
}


/** Auto-choose the preferred path from a bunch of shortest paths with
 * different starting points.  In GUI, this is not needed, user can
 * manually choose. */
public Upgrader[] getPreferredPath(List<Upgrader[]> paths)
{
	return null;
}


/** Figures out all our options for upgrade based on what is available in the
 * database and what Upgraders we have on file.
 * @param uversionid
 * @param availVersions
 * @return
 */
public List<Upgrader[]> getAvailablePaths(int uversionid, Set<Integer> availVersions)
{
	return null;
}

public void applyPath(SqlRunner str, ConnPool pool, int uversionid, Upgrader[] path)
{
	for (Upgrader up : path) {
		up.upgrade(str, pool, uversionid);
	}
}
// =============================================

//public String getName(int uversionid);



///** @returns The version of the currently stored default, which will be used
// * if no appropriate resource can be found. */
//public int storedVersion();

///** Creates this resource from scratch, at the current version.
// @overwrite If true, overwrite even a pre-existing resource. */
//public void create(SqlRunner str, int uversionid, boolean overwrite);

///** Upgrades existing resource to a new version
// @param uversionid if <0, then upgrade ALL resources of name */
//public void upgrade(SqlRunner str, int uversionid, int iversion0, int iversion1)
//{
//	// Get available versions...
//	
//	// Run shortest path...
//}

// ================================================================
// Runtime


/** Tells which version of this resource is required to support the given
 * system version.  Based on info of which versions we can have of this
 * resource (gleaned from the Upgraders).
 * @param systemVersion Version of current overall system
 @returns needed resource version. */
public int getRequiredVersion(int sysVersion)
{
	return versions.headSet(sysVersion+1).last();
	// If NullPointerException here, that means that we don't have
	// any record of this resource, or systemVersion is just too low.
}

///** Converts from raw bytes to the actual resource */
//public abstract Object toVal(byte[] bytes);
//
///** Converts from raw bytes to the actual resource */
//public abstract byte[] toBytes(Object val);


/** Loads the resource from the Classpath (JAR File) */
public ResResult loadJar(String name, int version) throws IOException
{
	// Create JAR resource name (with version #)
	String rname;
	int dot = name.lastIndexOf('.');
	if (dot < 0) rname = name + "-" + version;
	else rname = name.substring(0,dot) + "-" + version + name.substring(dot);
	
	// File doesn't exist; read from inside JAR file instead.
	String resourceName = getResSet().getJarPrefix() + rname;
System.out.println("Loading template as resource: " + resourceName);
	InputStream in = rset.getJarClassLoader().getResourceAsStream(resourceName);
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buf = new byte[8192];
	int len;
	while ((len = in.read(buf)) > 0) baos.write(buf,0,len);
	in.close();
	baos.close();

	ResResult ret = new ResResult();
		ret.bytes = baos.toByteArray();
		ret.name = name;
		ret.uversionid = 0;
		ret.version = version;
	return ret;
//	return OffstageReports.class.getClassLoader().getResourceAsStream(resourceName);	
}



/** Loads resource with largest value < sysVersion */
public ResResult load(SqlRunner str, int uversionid, int sysVersion)
{
	return ResUtil.getResource(str, getName(), uversionid, sysVersion);
//	// Get the bytes
//	final ResResult ret = new ResResult();
//	String sql =
//		" select rid.name,r.* from retources r, retourceids rid" +
//		" where rid.retourceid = r.retourceid" +
//		" and rid.name = " + SqlString.sql(getName()) +
//		" and uversionid = " + uversionid +
//		" and version = " +
//			" (select max(version) from retources r, retourceids rid" +
//			" where rid.retourceid = r.retourceid" +
//			" and name = " + SqlString.sql(getName()) +
//			" and uversionid = " + uversionid + ")";
//	str.execSql(sql, new RsRunnable() {
//	public void run(SqlRunner str, ResultSet rs) throws Exception {
//		ret.name = rs.getString("name");
//		ret.version = rs.getInt("version");
//		ret.uversionid = rs.getInt("uversionid");
//		ret.bytes = rs.getBytes("val");
//		// ret.bytes = toVal(bytes);
//	}});
//	return ret;
}

/** Should we refuse to run the application if this resource
 is not up to date?  Or should we run anyway, fialing gracefully when we
 cannot load the required resource? */
public boolean isEssential() { return essential; }

}
