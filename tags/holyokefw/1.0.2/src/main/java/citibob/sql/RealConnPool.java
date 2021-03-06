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

import java.sql.*;
import java.util.*;

public class RealConnPool extends SimpleConnPool
{

static class DbbDate
{
	Connection dbb;
	long lastUsedMS;
	
	public DbbDate(Connection dbb) {
		this.dbb = dbb;
		this.lastUsedMS = System.currentTimeMillis();
	}
}
	
LinkedList<DbbDate> reserves = new LinkedList();	// Our reserve connections, not being used for now


public RealConnPool(String url, Properties props)
{ super(url, props); }

//ExceptionHandler ehandler;
//
//public SimpleConnectionPool(ExceptionHandler eh)
//{
//	this.ehandler = eh;
//}

/** Get a connection from the pool. */
public synchronized Connection checkout() throws SQLException
{
//return create();
	long ms = System.currentTimeMillis();
	while (reserves.size() > 0) {
		DbbDate dd = reserves.removeFirst();
		if (ms - dd.lastUsedMS < 60 * 1000L) {
			// Good connection, less than 1 minute stale
//System.out.println("Re-using good connection: " + dd.dbb);
			return dd.dbb;
		}
		// Throw out connections > 1 minute stale
//System.out.println("Throwing out connection: " + dd.dbb);
		try { dd.dbb.close(); } catch(SQLException e) {}
	}
	
	// No reserves left; create a new connection
System.out.println("RealConnPool: Creating new database connection");
	return create();
//TODO: Keep track of lastused date --- throw out connections after 10 minutes
//Also.... in Exception handler, an SQLException should cause that connection to be closed and NOT returned.
}

/** Return a connection */
public synchronized void checkin(Connection c) throws SQLException
{
//	c.close();
	if (reserves.size() >= 5) c.close();
	else reserves.addLast(new DbbDate(c));
}
public void dispose() {}

//public void doRun(StRunnable r)
//	{ DbRawRunner.run(r, this); }
//public void doRun(DbRunnable r)
//	{ DbRawRunner.run(r, this); }

}
