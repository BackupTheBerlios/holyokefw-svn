/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.wizard2;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author citibob
 */
public class WizContext {
	Map<String,Object> values;
	Map<String,Object> localValues;

public void addValues(Map<String,Object> values) {
	for (Map.Entry<String,Object> ee : values.entrySet()) {
		values.put(ee.getKey(), ee.getValue());
	}
}

public Object get(String key) { return values.get(key); }
public void put(String key, Object val) { values.put(key, val); }
public Object getLocal(String key) { return localValues.get(key); }

}
