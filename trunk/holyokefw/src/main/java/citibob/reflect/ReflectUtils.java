/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
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
