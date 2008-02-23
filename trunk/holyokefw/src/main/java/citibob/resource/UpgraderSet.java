/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author citibob
 */
public class UpgraderSet {

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

/** Gets a (sorted) list of all the versions we've seen. */
TreeSet<Integer> getVersions() { return versions; }
//{
//	int[] ret = new int[versions.size()];
//	int i = 0;
//	for (Integer ver : versions) ret[i++] = ver;
//	return ret;
//}

/** Plain and simple.
 Question: should this always prefer paths staring with a higher version0
 over paths with a lower version0, even if the one with higher version0 had
 a greater length?  Probably...
 Or maybe it should just return ALL the paths that it could find, the shortest
 one from each version0*/
Upgrader[] getPath(int version0, int version1)
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

//static final List<Integer> creatorVersions0;
//static {
//	creatorVersions0 = new ArrayList(1);
//	creatorVersions0.add(-1);
//}
public Upgrader[] getCreatorPath(int version1)
{
	return getPath(-1, version1);
}

}
