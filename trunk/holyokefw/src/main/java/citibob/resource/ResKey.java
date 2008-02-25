/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.SortedSet;

/**
 * Key fields for a resource.  Used to identify which resources we need
 * current version of.
 * @author citibob
 */
public class ResKey implements Comparable<ResKey> {
	public Resource res;
	public int uversionid;
	public String uversionName;		// For display: name corresponding to uversionid.  Or null.
	public int[] availVersions;	// (from DB): available versions of this resource.
	public ResKey(Resource res, int uversionid, String uversionName) {
		this.res = res;
		this.uversionid = uversionid;
		this.uversionName = uversionName;
	}
	public ResKey(Resource res) { this(res, 0, "<Base>"); }
	public int compareTo(ResKey rk) {
		int cmp = res.getName().compareTo(rk.res.getName());
//		int cmp = res.resourceid = rk.res.resourceid; //getName().compareTo(rk.res.getName());
		if (cmp != 0) return cmp;
		return uversionid - rk.uversionid;
	}
	
	public UpgradePlan getCreatorPlan(int version1)
	{
		UpgradePlan uplan = res.getCreatorPlan(version1);
		if (uplan == null) return null;
		uplan.setUversionid0(uversionid, uversionName);
		uplan.setUversionid1(uversionid, uversionName);
		return uplan;
	}
//	public UpgradePlan getUpgradePlan(int version0, int version1)
//	{
//		return getUpgradePlan(version0, version1, false);
//	}
	public UpgradePlan getUpgradePlan(int version0, int version1)
	{
		UpgradePlan uplan = res.getUpgradePlan(version0, version1);
		if (uplan == null) return null;
		uplan.setUversionid0(uversionid, uversionName);
		uplan.setUversionid1(uversionid, uversionName);
		return uplan;
	}

	public String toString()
	{
		return "ResKey(" + res.getName() + ":" + res.getResourceID() + ", " + uversionName + ":" + uversionid + ")";
	}
}
