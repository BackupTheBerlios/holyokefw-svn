/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
//public byte[] upgrade(byte[] val);
public void upgrade(Connection dbb, byte[] oldVal) throws SQLException, IOException;

}
