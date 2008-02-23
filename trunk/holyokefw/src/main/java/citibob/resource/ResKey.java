/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

/**
 * Key fields for a resource.  Used to identify which resources we need
 * current version of.
 * @author citibob
 */
public class ResKey implements Comparable<ResKey> {
	public Resource res;
	public int uversionid;
	public String uversionName;		// For display: name corresponding to uversionid.  Or null.
	public ResKey(Resource res, int uversionid, String uversionName) {
		this.res = res;
		this.uversionid = uversionid;
		this.uversionName = uversionName;
	}
	public int compareTo(ResKey rk) {
		int cmp = res.getName().compareTo(rk.res.getName());
		if (cmp != 0) return cmp;
		return uversionid - rk.uversionid;
	}
}
