/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public interface ConnFactory {
	public Connection create() throws SQLException;
	public void close(Connection dbb) throws SQLException;
}
