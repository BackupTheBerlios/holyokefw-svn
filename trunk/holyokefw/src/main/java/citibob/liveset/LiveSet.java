package citibob.liveset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public abstract class LiveSet<TT extends LiveItem>
extends LiveSetMVC<TT>
{

protected Map<TT,TT> set = new TreeMap();
boolean newItemCreated;


/** Creates a new row, based on a lookupKey.  Optional.  Only
 needed if you're going to use getCreate(). */
protected TT newItem(TT lookupKey) { return null; }


/** Fires event... */
public void add(TT item)
{
	set.put(item,item);
	fireItemAdded(item);
}
/** Fires event... */
public TT remove(TT item)
{
	TT ret = set.remove(item);
	fireItemRemoved(item);
	return ret;
}
public TT get(TT key)
{
	return set.get(key);
}

/** Fires event... */
public void clear()
{
	set.clear();
	fireItemsChanged();
}

/** Returns all items in this LiveSet. */
public Collection<TT> copyAll()
{
	List<TT> list = new ArrayList(set.size());
	for (TT tt : set.keySet()) list.add(tt);
	return list;
}

/** Call only AFTER you've set lookupKey! */
public TT getCreateItem(TT lookupKey) //TT lookupKey)
{
	TT row = set.get(lookupKey);
	if (row == null) {
		newItemCreated = true;
		row = newItem(lookupKey);
		add(row);
	} else {
		newItemCreated = false;
	}
	return row;
}
/** Call after getCreateRow(), tells if a new row was created or not. */
public boolean newItemCreated() {
	return newItemCreated;
}
}
