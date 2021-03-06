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
package citibob.jschema;

import java.util.*;

public class ConstSqlSchema
extends ConstSchema
implements SqlSchema
{

protected String table;

//public List getPrototypes()
//{
//	ArrayList ls = new ArrayList(cols.length);
//	for (int i = 0; i < cols.length; ++i) {
//		ls.add(cols[i].getType().getPrototype());
//	}
//	return ls;
//}

public ConstSqlSchema() {
	// Default table name same as class
	table = getClass().getSimpleName();
}
/** @Deprecated */
public ConstSqlSchema(Column[] cols, String table)
{
	this(table, cols);
}
public ConstSqlSchema(String table, Column... cols)
{
	this.table = table;
	this.cols = cols;
}

public String getDefaultTable()
	{ return table; }

}
