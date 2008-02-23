/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.SqlRunner;

/**
 *
 * @author citibob
 */
public abstract class BaseUpgrader implements Upgrader
{

int version0, version1;
Resource res;

public BaseUpgrader(Resource resource, int version0, int version1)
{
	this.res = resource;
	this.version0 = version0;
	this.version1 = version1;
}
//public String getName() { return resource; }
public Resource getResource() { return res; }
public int version0() { return version0; }

public int version1() { return version1; }

}
