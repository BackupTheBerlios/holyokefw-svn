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

import java.util.HashMap;
import java.util.Map;

import java.io.DataOutput;
import java.io.IOException;

/**
 * The SerializationContext allows Objects and object graphs 
 * to be written to a DataOutput in serialized form. 
 * 
 * <p>The function of the context is to keep track of objects and 
 * class data that have already been serialized, replacing previously seen objects
 * with simple references. This allows, for instance, the serialization of objects 
 * graphs that contains cycles.
 * 
 * <p>Only objects that implement <code>java.io.Serializable</code> can be serialized
 * through a SerializationContext. Currently, custom serialization (through 
 * <code>java.io.Externalizable</code> or <code>writeObject</code> method)
 * as well as other advanced serialization features are not supported.
 * 
 * <p>Objects written to a DataOutput by a SerializationContext can be retrieved 
 * with a DeserializationContext. The data written to the DataOutput is not 
 * self-contained, because it may refer to objects or classes that have been 
 * previously written. Because of that, data written with a single SerializationContext 
 * should be read by a single DeserializationContext.
 *   
 * @author Leonardo "Bubble" Mesquita
 * @see java.nio.DataOutput
 * @see java.io.Serializable
 * @see com.bubble.serializer.DeserializationContext
 *
 */
public class SerializationContext {
	/**
	 * Maps classes to their assigned Id's
	 */
	private Map classToId;
	
	/**
	 * Next id available for assignment to a class.
	 */
	private int nextClassId;

	/**
	 * Next object id available for assignment to an object.
	 */
	private int nextObjectId;
	
	/**
	 * Generator of custom serialization code.
	 */
	private Generator generator;
	
	/**
	 * Maps objects to their assigned Id's
	 */
	private SerMap objectToId;

	SaverFilter sfilter;
	
	/**
	 * Constructs a new SerializationContext.
	 */
	public SerializationContext() {
		this(new ConstSaverFilter(true));
	}
	public SerializationContext(SaverFilter sfilter) {
		this.nextClassId = SerializationConstants.FIRST_CLASS_ID;
		this.nextObjectId = SerializationConstants.FIRST_OBJECT_ID;
		this.classToId = new HashMap();
		this.generator = new Generator(null);
		this.objectToId = new SerMap();
		this.sfilter = sfilter;
	}
	
	/**
	 * Serializes an object to a DataOutput.
	 * <p>The object must implement <code>java.io.Serializable</code>.
	 * @param obj The object being serialized.
	 * @param buffer The buffer to which the object will be serialized.
	 */
	public void serialize(Object obj, DataOutput buffer) throws IOException
	{
//System.out.println("Serializing: " + obj);
		if(obj == null) {
			buffer.writeByte(SerializationConstants.NULL_BLOCK);
			return;
		}
		sfilter.pushObj(obj);
		Integer objectId = (Integer)objectToId.get(obj);
		if(objectId == null) {
			serializeNewObject(obj, buffer);
		} else {
			buffer.writeByte(SerializationConstants.OBJECT_REF_BLOCK);
			buffer.writeInt(objectId.intValue());
		}
		sfilter.popObj(obj);
	}	
	
	private void serializeNewObject(Object obj, DataOutput buffer) throws IOException {
		Class clazz = obj.getClass();
		Serializer serializer = generator.getSerializer(clazz);
		Integer classId = (Integer)classToId.get(clazz);
		if(classId == null) {
			classId = new Integer(nextClassId++);
			classToId.put(clazz, classId);
			buffer.writeByte(SerializationConstants.CLASSDEF_BLOCK);
			buffer.writeUTF(clazz.getName());	//StringUtils.serialize(clazz.getName(), buffer);
		}
	
		if (sfilter.saveObj(obj)) {
//System.out.println("Storing ID: " + obj);
			Integer objectId = new Integer(nextObjectId++);
			objectToId.put(obj, objectId);
		}
		buffer.writeByte(SerializationConstants.OBJECT_BLOCK);
		buffer.writeInt(classId.intValue());
		serializer.serializeInstanceInfo(obj, buffer, this);
		serializer.serialize(obj, buffer, this);
	}
	
	/**
	 * Serializes a new state for an object. When read through a 
	 * DeserializationContext, it will update the state of the 
	 * Object that was created when this Object was first read.
	 * 
	 * <p>The object must implement <code>java.io.Serializable</code>.
	 * 
	 * @param obj The object being updated.
	 * @param buffer The buffer to which the object state will be serialized.
	 */
	public void update(Object obj, DataOutput buffer) throws IOException {
		if(obj == null) {
			buffer.writeByte(SerializationConstants.NULL_BLOCK);
			return;
		}
		Integer objectId = (Integer)objectToId.get(obj);
		if(objectId == null) {
			serializeNewObject(obj, buffer);
		} else {
			// If this object has already gone, its class MUST have been serialized
			Class clazz = obj.getClass();
			Serializer serializer = generator.getSerializer(clazz);
			buffer.writeByte(SerializationConstants.OBJECT_UPDATE_BLOCK);
			buffer.writeInt(objectId.intValue());
			serializer.serialize(obj, buffer, this);
		}
	}
}
