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
public class NopUpgrader extends DataUpgrader
{

public NopUpgrader(Resource resource, int version0, int version1)
	{ super(resource, version0, version1); }

/** Does the semantic work of the actual upgrade! */
public byte[] upgrade(byte[] val) {
	System.out.println("Nop Upgrading " + getResource() + " from " + version0 + " -> " + version1);
	return val;
}

}
