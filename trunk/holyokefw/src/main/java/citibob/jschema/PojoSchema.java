/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

import citibob.types.JavaJType;
import java.lang.reflect.Field;

/**
 *
 * @author fiscrob
 */
public class PojoSchema extends ConstSqlSchema
{
	
Class klass;		// Class from which this Schema was made

public Class getKlass() { return klass; }

public PojoSchema(Class klass)
{
	this.klass = klass;
	super.table = klass.getName();
	Field[] fields = klass.getFields();
	super.cols = new Column[fields.length];
	for (int i=0; i<fields.length; ++i) {
		Field f = fields[i];
		cols[i] = new Column(new JavaJType(f.getType()), f.getName());
	}
}

//public Object newInstance() throws InstantiationException, IllegalAccessException
//	{ return klass.newInstance(); }
}
