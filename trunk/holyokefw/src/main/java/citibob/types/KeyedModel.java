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
/*
 * KeyedModel.java
 *
 * Created on March 20, 2005, 5:51 PM
 * TODO: Should this be in citibob.jschema package?
 * TODO: This does not use the SqlTypeSet structure.  Thus, it may do incorrect
 * data conversions on some types (such as dates, wrong timezone).  This should
 * not be much of a problem, since KeyedModel is rarely if ever used for dates
 * or times selected from the DB.
 */

package citibob.types;

import java.sql.*;
import java.util.*;
import citibob.sql.*;

//import
/**
 *
 * @author citibob
 */
public class KeyedModel extends KeyedModelMVC
{

Map itemMap = new HashMap();	// HashMap instead of TreeMap doesn't require comparision method
Map invMap = new HashMap();
Vector keyList = new Vector();
//String nullString = "<none>";
int nextSerial = 0;

public int size() { return itemMap.size(); }

/** An item to be added to the combo box in JIntComboBox. */
public class Item {
	public Object key;
	public Object obj;
	public Integer serial;	// Order of this item in the KeyedModel list
	public String toString() {
		if (obj == null) return null;
		return obj.toString();
	}
	public Item(Object key, Object obj) {
		this.key = key;
		this.obj = obj;
	}
	/** Pointer equality requires model object be used */
	public boolean equals(Object o)
	{
		if (o instanceof Item) {
			Item it = (Item)o;
			return (it == this);
		} else return false;
	}
}

//public void setNullString(String s)
//	{ this.nullString = s; }
//public String getNullString()
//	{ return nullString; }
public Vector getKeyList()
	{ return keyList; }
public Map getItemMap()
	{ return itemMap; }

public boolean containsKey(Object key)
{
	return itemMap.get(key) != null;
}
public KeyedModel.Item get(Object key)
{
	return (KeyedModel.Item)itemMap.get(key);
}
public KeyedModel.Item getInv(Object val)
{
	return (KeyedModel.Item)invMap.get(val);
}
/** Given val, returns the corresponding key. */
public Object getKey(Object val)
{
	return getInv(val).key;
}
/** Convenience method */
public Integer getIntKey(Object val)
{
	return (Integer)getKey(val);
}
protected void clear()
{
	itemMap.clear();
	invMap.clear();
	keyList.clear();
}
private void addItem(KeyedModel.Item oi)
{
	oi.serial = new Integer(nextSerial++);
	itemMap.put(oi.key, oi);
	invMap.put(oi.obj, oi);
	keyList.add(oi.key);
}

/** Adds another item to the dropdown list. */
public void addItem(Object key, Object item)
{
	KeyedModel.Item oi = new KeyedModel.Item(key, item);
	addItem(oi);
}

//public void addAllItems(SqlRunner str, String sql, int keyCol, int itemCol) throws SQLException
//{
////System.out.println("addAllItems: this = " + this);
//	while (rs.next()) addItem(rs.getObject(keyCol), rs.getObject(itemCol));
//}

public void addAllItems(SqlRun str, String sql, final int keyCol, final int itemCol)
{
	str.execSql(sql, new RsTasklet() {
	public void run(ResultSet rs) throws SQLException {
		clear();
		while (rs.next()) {
			addItem(rs.getObject(keyCol),
				rs.getObject(itemCol));
		}
	}});
}

public void addAllItems(SqlRun str, String sql, final String keyCol, final String itemCol)
{
	str.execSql(sql, new RsTasklet() {
	public void run(ResultSet rs) throws SQLException {
		clear();
		while (rs.next()) {
			addItem(rs.getObject(keyCol),
				rs.getObject(itemCol));
		}
	}});
}

/** Converts key value to a string, if the key exists in the model;
 *otherwise return null. */
public String toString(Object key)
{
	//if (key == null) return null;
	KeyedModel.Item oi = (Item)itemMap.get(key);
	if (oi == null) return null;
	return oi.toString();
}
public Integer getSerial(Object key)
{
	//if (key == null) return null;
	KeyedModel.Item oi = (Item)itemMap.get(key);
	if (oi == null) return null;
	return oi.serial;
}
// -------------------------------------------------------------------
public void KeyedModel(SqlRun str, String sql, int keyCol, int itemCol)
{
	addAllItems(str, sql, keyCol, itemCol);
}
public void KeyedModel(SqlRun str, String sql, String keyCol, String itemCol)
{
	addAllItems(str, sql, keyCol, itemCol);
}
//public KeyedModel(Object[] objs)
/** Creates a KeyedModel in which key == value. */
public static KeyedModel sameKeys(Object[] objs)
{
	KeyedModel km = new KeyedModel();
	for (int i=0; i<objs.length; ++i) {
		km.addItem(objs[i], objs[i]);
	}
	return km;
}
/** items is the displayed values in the listbox. */
public KeyedModel(Object[] keys, Object[] items)
{
	for (int i=0; i<keys.length; ++i) {
		addItem(keys[i], items[i]);
	}
	
}
public KeyedModel() {}
public static KeyedModel intKeys(Object... items)
{
	KeyedModel km = new KeyedModel();
	for (int i=0; i<items.length; ++i) {
		km.addItem(new Integer(i), items[i]);
	}
	return km;
}
// -------------------------------------------------------------------

}
