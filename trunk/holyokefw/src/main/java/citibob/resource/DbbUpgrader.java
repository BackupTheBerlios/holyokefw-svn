/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

/**
 *
 * @author citibob
 */
public class DbbUpgrader extends BaseDbbUpgrader
{

final static String[] isuffixes = {"upgrade"};

public DbbUpgrader(Resource resource, int version0, int version1,
boolean backCompatible)
{
	super(resource, version0, version1, backCompatible, isuffixes);
}
}
