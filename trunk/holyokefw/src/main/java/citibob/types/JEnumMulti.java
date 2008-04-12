/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.types;

import java.util.Collection;

/**
 *
 * @author citibob
 */
public class JEnumMulti implements JType {

	protected KeyedModel kmodel;
	protected Object segment;
	protected JType baseJType = JavaJType.jtInteger;

	/** nullText = string to use for null value, or else <null> if this is not nullable. */
	public JEnumMulti(JType baseJType, KeyedModel kmodel, Object segment) {
		this.baseJType = baseJType;
		this.kmodel = kmodel;
		this.segment = segment;
	}
	public KeyedModel getKeyedModel() { return kmodel; }
	public Object getSegment() { return segment; }

	/** Returns the type of the elemnts of the enumeration; usually JavaJType.jtInteger. */
	public JType getBaseJType() { return baseJType; }
	
	/** Java class used to represent this type */
	public Class getObjClass()
		{ return Collection.class; } //baseJType.getObjClass();; }

	public boolean isInstance(Object o)
	{
		return (kmodel.containsKey(o)); //get(o) != null);
	}

	
}
