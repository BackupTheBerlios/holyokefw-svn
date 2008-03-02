/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRunner;
import citibob.sql.UpdRunnable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public abstract class BaseUpgrader implements Upgrader
{

protected int version0, version1;
protected Resource resource;
protected boolean backCompatible;

public BaseUpgrader(Resource resource, int version0, int version1, boolean backCompatible)
{
	this.resource = resource;
	this.version0 = version0;
	this.version1 = version1;
	this.backCompatible = backCompatible;
}

/** Can the effect of this upgrader be reversed?  Or does it overwrite old versions? */
public boolean isBackCompatible() { return true; }


//public String getName() { return resource; }
public Resource getResource() { return resource; }
public int version0() { return version0; }

public int version1() { return version1; }


public String toString()
{
	return getClass().getSimpleName() + "(" + version0 + " -> " + version1 + ")";
}
}
