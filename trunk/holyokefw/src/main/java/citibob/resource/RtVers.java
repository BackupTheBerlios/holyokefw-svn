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
	public Long size;				// # bytes in the resource stored in dbb
	public Date lastmodified;
	
	public RtVers(int version, Long size, Date lastmodified)
	{
		this.version = version;
		this.size = size;
		this.lastmodified = lastmodified;
	}
}
