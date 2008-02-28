/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.pgsql.*;

/**
 * For testing!
 * @author citibob
 */
public class CopyUpgrader extends DataUpgrader
{

public CopyUpgrader(Resource resource, int version0, int version1)
	{ super(resource, version0, version1); }

/** Does the semantic work of the actual upgrade! */
public byte[] upgrade(byte[] val) {
//	System.out.println("Nop Upgrading " + getResource() + " from " + version0 + " -> " + version1);
	return val;
}

public String getDescription()
{
	return "Copy version " + version0() + " to version " + version1(); 
}

}
