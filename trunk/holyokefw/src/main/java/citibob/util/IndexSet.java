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
package citibob.util;
/*
 * IndexSet.java
 *
 * Created on November 17, 2005, 6:11 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

import java.util.*;

/**
 * A set in which each item is indexed with an integer 0..size()-1.  The
 * index changes when the set changes.
 * @author fiscrob
 */
public class IndexSet<TT> implements java.io.Serializable
{

TT[] sym;
HashMap<TT,Integer> tsym;		// NOT TreeMap

protected IndexSet() { }
public IndexSet(TT... sym)
{
	init(sym);
}
public IndexSet(List<TT> asym)
{
	TT[] sym = (TT[])new Object[asym.size()];
	asym.toArray(sym);
	init(sym);
}

//public static TT[] subtract(TT[] a, TT[] b)
//{
//	TreeSet set = new TreeSet();
//	for (int i=0; i<a.length; ++i) set.add(a[i]);
//	for (int i=0; i<b.length; ++i) set.remove(b[i]);
//	TT[] c = new TT[set.size()];
//	int i=0;
//	for (Iterator ii=set.iterator(); ii.hasNext();) {
//		c[i++] = (TT)ii.next();
//	}
//	return c;
//}
//public static TT[] add(TT[] a, TT[] b)
//{
//	TreeSet set = new TreeSet();
//	for (int i=0; i<a.length; ++i) set.add(a[i]);
//	for (int i=0; i<b.length; ++i) set.add(b[i]);
//	TT[] c = new TT[set.size()];
//	int i=0;
//	for (Iterator ii=set.iterator(); ii.hasNext();) {
//		c[i++] = (TT)ii.next();
//	}
//	return c;
//}

public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append("IndexSet(");
	for (int i=0; i<sym.length; ++i) {
		buf.append(sym[i]);
		buf.append(' ');
	}
	buf.append(')');
	return buf.toString();
}

protected void init(TT[] sym)
{
	this.sym = sym;
	tsym = new HashMap();
	for (int i=0; i<sym.length; ++i)
		tsym.put(sym[i], new Integer(i));
}

public TT get(int ix)
{
	if (ix<0 || ix>=sym.length) return null;
	return sym[ix];
}

	public TT[] getSym() {
		return sym;
	}

public TT[] get(int... ixx)
{
	TT[] out = (TT[])new Object[ixx.length];
	for (int i=0; i<out.length; ++i) out[i] = get(ixx[i]);
	return out;
}

public TT[] getSymbols() { return sym; }

public int toIx(TT s)
{
if (tsym == null) {
	System.out.println("tsym is null");
}
	Integer ii = tsym.get(s);
	return (ii == null ? -1 : ii.intValue());
//	s = s.toUpperCase();
//	int ix=-1;
//	for (int t=0; t<sym.length; t++)
//	{
//		if (sym[t].equalsIgnoreCase(s))
//		{
//			ix=t;
//			break;
//		}
//	}
//	return ix;
}

/** Makes an index array of a sub-universe. */
public int[] toIx(TT... sym)
{
	int[] ret = new int[sym.length];
	for (int i=0; i<sym.length; ++i)
	{
		ret[i] = toIx(sym[i]);
	}
	return ret;
}
	
public int size()
{
	return sym.length;
}

// =========================================================
public static void main(String[] args)
{
	IndexSet<String> set = new IndexSet("A", "B", "C");
	System.out.println(set.toIx("B"));
	System.out.println(set.get(2));

}
}
