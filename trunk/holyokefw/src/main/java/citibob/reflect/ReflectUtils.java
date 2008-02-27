/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fiscrob
 */
public class ReflectUtils {

public static Map<String,Field> getAllFields(Class... klasses)
{
	HashMap<String,Field> map = new HashMap();
	for (Class klass : klasses) {
//System.out.println("getAllFields: klass = " + klass);
		for (Class k = klass; k != Object.class; k = k.getSuperclass()) {
//System.out.println("    k = " + k);
			Field[] fields = k.getDeclaredFields();
			for (Field f : fields) {
				// Earlier items take precedence over later items.
				// Meaning: subclass instance variables override
				// superclass instance variables.
				if (map.containsKey(f.getName())) continue;
				
				// Don't do static fields
				if ((f.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0) continue;
		
				// Get the field
	//			f.setAccessible(true);
				map.put(f.getName(), f);
			}
		}
	}
	return map;
}

public static List<Field> getAllFieldsList(Class klass)
{
	LinkedList<Field> list = new LinkedList();
	for (Class k = klass; k != Object.class; k = k.getSuperclass()) {
		Field[] fields = k.getDeclaredFields();
		for (int i=fields.length-1; i>= 0; --i) {
			Field f = fields[i];
			
			// Don't do static fields
			if ((f.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0) continue;

			// Get the field
//			f.setAccessible(true);
			list.addFirst(f);
		}
	}
	return list;
}

/** Gets one named field, public or otherwise. */
public static Field getField(Class klass, String name)
{
	for (Class k = klass; k != Object.class; k = k.getSuperclass()) {
//System.out.println("    k = " + k);
		Field[] fields = k.getDeclaredFields();
		for (Field f : fields) {
			// Don't do static fields
			if ((f.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0) continue;

			// Get the field
			if (name.equals(f.getName())) return f;
		}
	}
	return null;
}


}
