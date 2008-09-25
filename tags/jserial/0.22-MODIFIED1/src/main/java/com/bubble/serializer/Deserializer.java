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

import java.io.DataInput;
import java.io.IOException;
/**
 * Interface of the custom deserializers for each class.
 * @author Leonardo "Bubble" Mesquita
 *
 */
public interface Deserializer {
	/**
	 * Returns a new object, possibly reading instantiation information from the buffer.
	 * Currently, only arrays have instantiation information, namely the size of the array,
	 * serialized separately. This allows arrays to be created without resorting to reflection.
	 * @param buffer The buffer that may contain instantiation information.
	 * @param context The DeserializationContext that is reading the buffer. The context can
	 *   interpret references to previously read objects that might be members of the object being read. 
	 * 	
	 * @return A new object read from the buffer, but not complete.
	 */
	public Object instantiate(DataInput buffer, DeserializationContext context)
	throws IOException;
	
	/**
	 * Reads the state of the object from the buffer. 
	 * 
	 * @param obj The object for which the state is being read.
	 * @param buffer The buffer that contains the state of the object.
	 * @param context The DeserializationContext that is reading the buffer. The context can
	 *   interpret references to previously read objects that might be members of the object being read.
	 */
	public void deserialize(Object obj, DataInput buffer, DeserializationContext context)
	throws IOException;
}
