package citibob.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class LiveSet<TT extends LiveItem>
extends LiveSetMVC<TT>
{

protected SortedMap<TT,TT> items = new TreeMap();
boolean newItemCreated;


/** Creates a new row, based on a lookupKey.  Optional.  Only
 needed if you're going to use getCreate(). */
protected TT newItem(TT lookupKey) { return null; }


/** Fires event... */
public void add(TT item)
{
	items.put(item,item);
	fireItemAdded(item);
}
/** Fires event... */
public TT remove(TT item)
{
	TT ret = items.remove(item);
	fireItemRemoved(item);
	return ret;
}
public TT get(TT key)
{
	return items.get(key);
}

/** Fires event... */
public void clear()
{
	items.clear();
	fireItemsChanged();
}

/** Returns all items in this LiveSet. */
public Collection<TT> copyAll()
{
	List<TT> list = new ArrayList(items.size());
	for (TT tt : items.keySet()) list.add(tt);
	return list;
}

public TT getCreateItem(TT lookupKey) //TT lookupKey)
{
	TT row = items.get(lookupKey);
	if (row == null) {
		newItemCreated = true;
		row = newItem(lookupKey);
		if (row == null) throw new NullPointerException("LiveSet.newItem() returned null; has it been properly overridden yet?");
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
