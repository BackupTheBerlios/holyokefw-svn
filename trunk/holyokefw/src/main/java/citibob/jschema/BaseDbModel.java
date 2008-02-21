/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

/**
 *
 * @author citibob
 */
public abstract class BaseDbModel implements DbModel
{
	
protected Object[] keys;
	
/** Sets all key fields at once */
public void setKeys(Object... xkeys)
{
	for (int i=0; i<keys.length; ++i) setKey(i, xkeys[i]);
}

/** Sets just the first key field (most common case) */
public void setKey(Object key)
	{ setKey(0,key); }

public void setKey(int ix, Object key)
	{ keys[ix] = key; }

//public Object[] getKeys() { return keys; }
public Object getKey(int ix) { return keys[ix]; }
public Object getKey() { return getKey(0); }

}
