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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class StringUtils {
	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetEncoder encoder = charset.newEncoder();
	private static CharsetDecoder decoder = charset.newDecoder();
	public static void serialize(String str, ByteBuffer buffer) {
		if(str == null) {
			buffer.putShort((short)-1);
			return;
		}
		int pos = buffer.position();
		buffer.position(pos+2);
		CoderResult result = encoder.encode(CharBuffer.wrap(str), buffer, true);
		if (result.isError()) {
			try {
				result.throwException();
			} catch(CharacterCodingException e) {
				throw new RuntimeException("Error encoding string", e);
			}
		}
		
		short len = (short)(buffer.position()-pos-2);
		buffer.putShort(pos, len);
	}
	
	public static String deserialize(ByteBuffer buffer) {
		short len = buffer.getShort();
		if(len < 0) {
			return null;
		}
		int limit = buffer.limit();
		buffer.limit(buffer.position()+len);
		String result;
		try {
			result = decoder.decode(buffer).toString();
		} catch(CharacterCodingException e) {
			throw new RuntimeException("Error decoding string", e);
		}
		buffer.limit(limit);
		return result;		
	}
}
