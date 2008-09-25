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
import java.io.InputStream;

public class HelperInputStream extends InputStream {

	private HelperOutputStream src;
	private int position = 0;
	
	public HelperInputStream(HelperOutputStream src) {
		this.src = src;
	}
	
	public int read() throws IOException {
		if(src.getSize() <= position) {
			return -1;
		}
		return src.getInternalData()[position++];
	}
	
	public int read(byte[] b, int off, int len) throws IOException {
		if(src.getSize() <= position) {
			return -1;
		}
		if(position+len > src.getSize()) {
			len = src.getSize()-position;
		}
		if (len <= 0) {
			return 0;
		}
		
		System.arraycopy(src.getInternalData(), position, b, off, len);
		position += len;
		return len;
	}
	
	public long skip(long n) throws IOException {
		if (position+n > src.getSize()) {
			n = src.getSize()-position;
		}
		if(n < 0) {
			return 0;
		}
		position += n;
		return n;		
	}
	
	public int available() throws IOException {		
		return src.getSize()-position;
	}

}
