/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author citibob
 */
public class WrapConnFactory implements ConnFactory
{

ConnFactory sub;

public WrapConnFactory(ConnFactory sub)
{
	this.sub = sub;
}

	public void close(Connection dbb) throws SQLException {
		sub.close(dbb);
	}

	public Connection create() throws SQLException {
		return sub.create();
	}


	
}
