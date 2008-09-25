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
package com.bubble.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class HelperOutputStream extends OutputStream {

	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private byte[] data;
	private ByteBuffer buffer;
	private int mark;
	
	public HelperOutputStream() {
		data = new byte[DEFAULT_BUFFER_SIZE];
		buffer = ByteBuffer.wrap(data);
		mark = 0;
	}
	
	public void write(int b) throws IOException {
		relocate(buffer.position()+1);
		buffer.put((byte)b);		
	}
	public void write(byte[] b, int off, int len) throws IOException {
		relocate(buffer.position()+len);
		buffer.put(b, off, len);
	}
	
	private void relocate(int size) {
		if(buffer.capacity() < size) {
			int newsize = Math.max(size, buffer.capacity()<<1);
			byte[] newdata = new byte[newsize];
			ByteBuffer  newbuf = ByteBuffer.wrap(newdata);
			buffer.flip();
			newbuf.put(buffer);
			data = newdata;
			buffer = newbuf;
		}
	}
	
	public byte[] getData() {
		int len = buffer.position()-mark;
		byte[] result = new byte[len];
		System.arraycopy(data, mark, result, 0, len);
		mark += len;
		return result;
	}
	
	public byte[] getStream() {
		buffer.flip();
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		buffer.limit(buffer.capacity());
		return result;
	}
	
	public void reset() {
		buffer.clear();
	}
	
	byte[] getInternalData() {
		return data;
	}
	int getSize() {
		return buffer.position();
	}

}
