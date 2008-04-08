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

import java.io.DataInput;
import java.io.IOException;

/**
 * The DeserializationContext allows Objects and object graphs 
 * that were serialized by a SerializationContext to be read from a ByteBuffer.
 * 
 * <p>The function of the context is to keep track of objects and 
 * class data that have already been read, allowing the data to contain simple
 * references to other objects.
 * This allows, for instance, the serialization of objects 
 * graphs that contains cycles.
 * 
 * <p>Only objects that implement <code>java.io.Serializable</code> can be read
 * by a DeserializationContext. Currently, custom deserialization (through 
 * <code>java.io.Externalizable</code> or <code>readObject</code> method)
 * as well as other advanced serialization features are not supported.
 * 
 * <p>The data in the ByteBuffer is not self-contained, because it may refer to objects 
 * or classes that have been previously read. Because of that, data written with 
 * a single SerializationContext should be read by a single DeserializationContext.
 * 
 * @author Leonardo "Bubble" Mesquita
 * @see java.nio.ByteBuffer
 * @see java.io.Serializable
 * @see com.bubble.serializer.SerializationContext  
 */
public class DeserializationContext {
	/**
	 * Maps class-assigned id's to the class appointed Deserializer.
	 */
	private Map idToDeserializer;
	
	/**
	 * Maps object-assigend id's to their respective Objects
	 */
	private Map idToObject;
	
	/**
	 * Id of the next class to be read.
	 */
	private int nextClassId;

	/**
	 * Id of the next Object to be read.
	 */
	private int nextObjectId;
	
	/**
	 * Generator of custom deserialization code.
	 */
	private Generator generator;

	SaverFilter sfilter;
	
	/**
	 * Constructs a new DeserializationContext.
	 *
	 */
	public DeserializationContext(ClassLoader classloader) {
		this(classloader, new ConstSaverFilter(true));
	}
	public DeserializationContext(ClassLoader classloader, SaverFilter sfilter) {
		this.nextClassId = SerializationConstants.FIRST_CLASS_ID;
		this.idToDeserializer = new HashMap();
		this.nextObjectId = SerializationConstants.FIRST_OBJECT_ID;
		this.idToObject = new HashMap();
		this.generator = new Generator(classloader);
		this.sfilter = sfilter;
	}
	
	/**
	 * Reads an object from a ByteBuffer. The object must have been transformed
	 * to serial form by a SerializationContext. The data can be a reference to
	 * an object that was previosly read by this DeserializationContext.
	 * 
	 * @param buffer The buffer that contains the object data.
	 * @return The reconstructed object.
	 */
	public Object deserialize(DataInput buffer) throws IOException {		
		while(true) {
			byte code = buffer.readByte();
			switch(code) {
				case SerializationConstants.CLASSDEF_BLOCK: {
					String className = buffer.readUTF(); //StringUtils.deserialize(buffer);
					try {
						Deserializer ds = generator.getDeserializer(className);
						idToDeserializer.put(new Integer(nextClassId++), ds);
					} catch (ClassNotFoundException e) {
						throw new BufferCorruptedException("Invalid block", e);
					}
					continue;
				}
					
				case SerializationConstants.OBJECT_BLOCK: {
					int classId = buffer.readInt();
					Deserializer ds = (Deserializer)idToDeserializer.get(new Integer(classId));
					
					Object result = ds.instantiate(buffer, this);					
					sfilter.pushObj(result);
					if (sfilter.saveObj(result)) 
						idToObject.put(new Integer(nextObjectId++), result);					
//System.out.println(result.getClass() + " " + ds.getClass());
					ds.deserialize(result, buffer, this);										
					sfilter.popObj(result);
					return result;
				}
				
				case SerializationConstants.NULL_BLOCK:
					return null;
					
				case SerializationConstants.OBJECT_REF_BLOCK: {
					int objectId = buffer.readInt();
					Object result = idToObject.get(new Integer(objectId));
					if(result == null) {
						throw new BufferCorruptedException("Invalid reference id: "+objectId);
					}
					return result;
				}
				
				case SerializationConstants.OBJECT_UPDATE_BLOCK: {
					int objectId = buffer.readInt();
					Object result = idToObject.get(new Integer(objectId));
					if(result == null) {
						throw new BufferCorruptedException("Invalid reference id: "+objectId);
					}
					Deserializer ds = generator.getDeserializer(result.getClass());
					ds.deserialize(result, buffer, this);
					
					return result;
				}
					
				default:
					throw new BufferCorruptedException("Invalid block code: "+code);
			}
		}
	}
}
