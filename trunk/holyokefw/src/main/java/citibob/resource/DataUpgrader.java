/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.pgsql.*;
import java.sql.Connection;

/**
 *
 * @author citibob
 */
public abstract class DataUpgrader extends BaseUpgrader
{

public DataUpgrader(Resource resource, int version0, int version1)
	{ super(resource, version0, version1); }

/** Does the semantic work of the actual upgrade! */
public abstract byte[] upgrade(byte[] val) throws Exception;


public void upgrade(Connection dbb, ResResult rr) throws Exception
{
	byte[] newBytes = upgrade(rr.bytes);
	ResUtil.setResource(dbb, resource.getName(), rr.uversionid, version1, newBytes);
}


}
