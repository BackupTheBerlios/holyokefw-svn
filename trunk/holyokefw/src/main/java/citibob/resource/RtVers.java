/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.Date;

/**
 * Info on one version number
 * @author citibob
 */
public class RtVers {
	public int version;
	public Date lastmodified;
	
	public RtVers(int version, Date lastmodified)
	{
		this.version = version;
		this.lastmodified = lastmodified;
	}
}
