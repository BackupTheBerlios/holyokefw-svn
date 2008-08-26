/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author citibob
 */
public class JDBCConnFactory implements ConnFactory
{

String url;
Properties props;
	
public JDBCConnFactory(String url, Properties props)
{
	this.url = url;
	this.props = props;
}



/** Create an actual connection --- used by pool implementations, should not
 * be called by user. */
public Connection create() throws SQLException
{
	Connection conn = DriverManager.getConnection(url, props);
	return conn;
}

/** Closes an actual connection --- used by pool implementations, should not
 * be called by user. */
public void close(Connection dbb) throws SQLException
{
	dbb.close();
}		

	
}
