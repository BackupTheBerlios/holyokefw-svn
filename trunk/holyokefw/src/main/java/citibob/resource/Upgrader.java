/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import citibob.sql.ConnPool;
import citibob.sql.SqlRunner;

/**
 * Encapsulates a procedure to upgrade one resource to the next version.
 * Could involve just throwing it out, or could involve patching it.
 * @author citibob
 */
public interface Upgrader {

public Resource getResource(); //String getName();

public int version0();

public int version1();

/** Does the actual upgrade! */
public void upgrade(SqlRunner str, final ConnPool pool, int uversionid0, int uversionid1);

}