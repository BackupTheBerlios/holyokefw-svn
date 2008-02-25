/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

/**
 *
 * @author citibob
 */
public class UpgradePlan {
	Upgrader[] path;
	private int uversionid0, uversionid1;
	private String uversionName0, uversionName1;
	
	public UpgradePlan(Upgrader[] path)
		{ this.path = path; }
	public Upgrader[] getPath() { return path; }
	public int version0() { return path[0].version0(); }
	public int version1() { return path[path.length - 1].version1(); }
	
	public int uversionid0() { return uversionid0; }
	public int uversionid1() { return uversionid1; }
	public String uversionName0() { return uversionName0; }
	public String uversionName1() { return uversionName1; }
	public void setUversionid0(int v, String name) {
		uversionid0 = v;
		uversionName0 = name;
	}
	public void setUversionid1(int v, String name) {
		uversionid1 = v;
		uversionName1 = name;
	}
	public String toString() {
		StringBuffer sbuf = new StringBuffer("UpgradePlan(");
		if (path == null || path.length < 1) sbuf.append(")");
		else {
			sbuf.append(path[0].version0());
			for (int i=1; i<path.length; ++i) {
				sbuf.append(" -> " + path[i].version1());
			}
			sbuf.append(")");
		}
		return sbuf.toString();
	}
}
