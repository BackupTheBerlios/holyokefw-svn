/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bubble.serializer;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 * @author fiscrob
 */
public class SerMap
{
Map<Object,Integer> main = new IdentityHashMap();
Map<Object,Integer> strings = new HashMap();

public void put(Object obj, Integer ii)
{
	if (obj instanceof String) strings.put(obj, ii);
	else main.put(obj, ii);
}
public Integer get(Object obj)
{
	if (obj instanceof String) return strings.get(obj);
	return main.get(obj);
}

}
