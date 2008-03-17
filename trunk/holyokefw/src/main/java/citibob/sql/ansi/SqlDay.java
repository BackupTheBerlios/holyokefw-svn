/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

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
package citibob.sql.ansi;

import citibob.sql.SqlType;
import java.text.*;
import java.util.*;
import java.sql.*;
import citibob.sql.pgsql.*;
import citibob.text.DaySFormat;
import citibob.util.Day;


public class SqlDay implements SqlType
{

protected boolean nullable = true;
protected DaySFormat dsfmt;

	protected SqlDay(String sfmt, boolean nullable)
	{
		this.nullable = nullable;
		dsfmt = new DaySFormat(sfmt, "");		
	}
	public SqlDay(boolean nullable) {
		this("yyyy-MM-dd", nullable);
	}
	public SqlDay() { this(true); }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Day.class; }

	/** Convert an element of this type to an Sql string for use in a query */
	public String toSql(Object o)
	{
		return o == null ? "null" :
			("DATE '" + dsfmt.valueToString(o) + '\'');
	}
	
	/** Returns the SQL string that encodes this data type. */
	public String sqlType()
		{ return "date"; }

	public boolean isInstance(Object o)
		{ return (o instanceof Day || (nullable && o == null)); }
		
//		return rs.getObject(col); }
	public Object get(java.sql.ResultSet rs, String col) throws SQLException
		{ return get(rs, rs.findColumn(col)); }
	public Object get(java.sql.ResultSet rs, int col) throws SQLException
	{
		try {
			String s = rs.getString(col);
			if (s == null) return null;
			return dsfmt.stringToValue(s);
		} catch(java.text.ParseException e) {
			throw new SQLException(e.getMessage());
		}
	}
}

	

