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
/*
 * SqlTypeFactory.java
 *
 * Created on January 28, 2007, 9:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.sql.ansi;

import java.util.*;
import static java.sql.Types.*;
import java.sql.*;
import citibob.sql.*;

/**
 * TODO: Could reduce a lot of short-lifed memory allocation here...
 * @author citibob
 */
public class AnsiTypeSet extends BaseSqlTypeSet
{
	
TimeZone tz;

public AnsiTypeSet(boolean msDates, TimeZone tz)
{
	super(msDates);
	this.tz = tz;
}
public AnsiTypeSet(boolean msDates)
{
	this(msDates, TimeZone.getDefault());
}


/** @param col the first column is 1, the second is 2, ...
 @returns an SqlType, given one of the basic types in java.sql.Types.  If N/A,
 or not yet implemented as an SqlType, returns null. */
public SqlType getSqlType(int type, int precision, int scale, boolean nullable)
{
	switch (type) {
		case ARRAY : return null;
		case BIGINT : return null;
		case BINARY : return null;
		case BIT : return new SqlBool(nullable);
		case BLOB : return null;
		case BOOLEAN : return new SqlBool(nullable);
//		case CHAR : return new SqlChar(nullable);
		case CHAR : return new SqlString(nullable);
		case CLOB : return new SqlString(nullable);
		case DATALINK : return null;
		case DATE : {
			SqlDateType sdt = new SqlDate(tz, nullable);
			if (msDates) sdt = new SqlMSDateWrapper(sdt);
			return sdt;
		}
		case DECIMAL : return new SqlNumeric(precision, scale, nullable);
		case DISTINCT : return null;
		case DOUBLE : return new SqlDouble(nullable);
		case FLOAT : return null;
		case INTEGER : return new SqlInteger(nullable) ;
		case JAVA_OBJECT : return null;
		case LONGVARBINARY : return null;
		case LONGVARCHAR : return new SqlString(nullable);
		case NULL : return null;
		case NUMERIC : return new SqlNumeric(precision, scale, nullable);
		case OTHER : return null;
		case REAL : return null;
		case REF : return null;
		case SMALLINT : return new SqlInteger(nullable);
		case STRUCT : return null;
		case TIME : {
			SqlDateType sdt = new SqlTime(nullable);
			if (msDates) sdt = new SqlMSDateWrapper(sdt);
			return sdt;
		}
		case TIMESTAMP : {
			SqlDateType sdt = new SqlTimestamp(tz, nullable);
			if (msDates) sdt = new SqlMSDateWrapper(sdt);
			return sdt;
		}
		case TINYINT : return null;
		case VARBINARY : return null;
		case VARCHAR : return new SqlString(nullable);
	}
	return null;
}

	
}