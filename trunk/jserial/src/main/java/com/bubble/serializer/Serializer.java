/*
 * Copyright (c) 2006 Leonardo "Bubble" Mesquita
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bubble.serializer;

import java.io.DataOutput;
import java.io.IOException;


/**
 * Interface of the custom serializers for each class.
 * @author Leonardo "Bubble" Mesquita
 *
 */
public interface Serializer {
	/**
	 * Serializes information necessary to instantiate an object.
	 * Currently, this is used only to serialize the size of arrays.
	 * In the future, we intend to use this to serialize the outer
	 * object instance of an inner class, in order to instantiate it
	 * without reflection.
	 * 
	 * @param obj The object being serialized
	 * @param buffer The buffer to which the object is being serialized
	 * @param context The SerializationContext that is serializing this object. 
	 * 	Objects that are members of the serialized object can be replaced
	 *  by references by the context. 
	 * 
	 */
	public void serializeInstanceInfo(Object obj, DataOutput buffer, SerializationContext context)
	throws IOException;
	
	/**
	 * Serializes the object's fields. Only non-final fields are serialized here,
	 * to reduce the amount of data that is sent through a <code>SerializationContext.update(Object, DataOutput)</code>
	 * 
	 * @param obj The object being serialized
	 * @param buffer The buffer to which the object is being serialized
	 * @param context The SerializationContext that is serializing this object. 
	 * 	Objects that are members of the serialized object can be replaced
	 *  by references by the context. 
	 */
	public void serialize(Object obj, DataOutput buffer, SerializationContext context)
	throws IOException;
}
