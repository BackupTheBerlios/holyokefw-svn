/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

/**
 *
 * @author citibob
 */
public class DbbCreator extends BaseDbbUpgrader
{

final static String[] isuffixes = {"schema", "data"};

public DbbCreator(Resource resource, int version1)
{
	super(resource, -1, version1, false, isuffixes);
}

public String getDescription() {
	return "Create new database version" + version1;
}


public String toString()
{
	return getClass().getSimpleName() + "(" + version1 + ")";
}
}
