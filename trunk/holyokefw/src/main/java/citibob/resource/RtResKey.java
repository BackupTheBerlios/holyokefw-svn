/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Key fields for a resource.  Used to identify which resources we need
 * current version of.
 * @author citibob
 */
public class RtResKey implements Comparable<RtResKey> {
	public Resource res;
	public int uversionid;
	public String uversionName;		// For display: name corresponding to uversionid.  Or null.
//	static final int[] noVersions = new int[0];
//	public int[] availVersions = noVersions;	// (from DB): available versions of this resource.

	public ArrayList<RtVers> availVersions = new ArrayList();
	
	
	public RtResKey(Resource res, int uversionid, String uversionName) {
		this.res = res;
		this.uversionid = uversionid;
		this.uversionName = uversionName;
	}
	public RtResKey(Resource res) { this(res, 0, "<Regular>"); }
	public int compareTo(RtResKey rk) {
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
