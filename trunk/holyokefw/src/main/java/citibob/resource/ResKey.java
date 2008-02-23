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
public class ResKey {
	public Resource res;
	public int uversionid;
	public ResKey(Resource res, int uversionid) {
		this.res = res;
		this.uversionid = uversionid;
	}
}
