/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.sql.Connection;
import citibob.sql.pgsql.*;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public abstract class DataUpgrader extends BaseUpgrader
{

public DataUpgrader(String name, int fromVersion, int toVersion)
	{ super(name, fromVersion, toVersion); }

/** Does the semantic work of the actual upgrade! */
public abstract byte[] upgrade(byte[] val);

public void upgrade(Connection dbb, int uversionid, byte[] oldVal)
throws SQLException
{
	ResUtil.setResource(dbb, name, uversionid, toVersion,
		upgrade(oldVal));
}

}
