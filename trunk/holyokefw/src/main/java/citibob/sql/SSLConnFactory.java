/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.sql;

import citibob.io.sslrelay.SSLRelayClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public abstract class SSLConnFactory extends RealConnPool
{

public static class Params {
	String url;		// JDBC "URL" to connect to
	Properties props;	// Extra stuff for url
	
}
	
//protected int localPort;		// Used by subclasses in getURL().
protected abstract SSLRelayClient.Params getSSLRelayClientParams();
	
/** One of these per connection we create */
static class FwdRec {
	SSLRelayClient sslClient;
}
Map<Connection,FwdRec> fwdMap = new HashMap();

	
/** Create an actual connection --- used by pool implementations, should not
 * be called by user. */
protected Connection create() throws SQLException
{
//	Properties props = (Properties)super.props.clone();
//	int clientPort;		// Get a free port
//	int serverPort = Integer.parseInt(props.getProperty("db.port"));
//	props.setProperty("db.port", ""+clientPort);
	
	// Start forwarder on the clientPort
	FwdRec frec = new FwdRec();
	frec.sslClient = new SSLRelayClient(getSSLRelayClientParams());

	Connection conn = DriverManager.getConnection(getURL(), getProps());
	fwdMap.put(conn, frec);
	return conn;
}

/** Closes an actual connection --- used by pool implementations, should not
 * be called by user. */
protected void close(Connection dbb) throws SQLException
{
	dbb.close();
	FwdRec frec = fwdMap.get(conn);
	frec.sslClient.closeAll();
}		

	
}
