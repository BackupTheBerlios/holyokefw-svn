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
System.out.println("getAllFields: klass = " + klass);
		for (Class k = klass; k != Object.class; k = k.getSuperclass()) {
System.out.println("    k = " + k);
			Field[] fields = k.getDeclaredFields();
			for (Field f : fields) {
				// Earlier items take precedence over later items.
				if (map.containsKey(f.getName())) continue;
				// Get the field
	//			f.setAccessible(true);
				map.put(f.getName(), f);
			}
		}
	}
	return map;
}

}
