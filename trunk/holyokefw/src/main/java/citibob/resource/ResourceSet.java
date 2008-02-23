/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.util.HashMap;
import java.util.List;

/**
 * Set of resources used by the current application.
 * @author citibob
 */
public abstract class ResourceSet extends
{

protected int sysVersion;

public ResourceSet(int sysVersion) {
	
}

/** Constructor of app-specific res set will add to this. */
HashMap<String,Resource> resources;

Resource get(String name)
	{ return resources.get(name); }


Object load(SqlRunner str, String name, int uversionid)
		
public Object load(SqlRunner str, int uversionid, int sysVersion)


/* List of resource-uversionid pairs required by this app at this time. */
public abstract List<ResKey> getRequired();

}
