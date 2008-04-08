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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryUtils {
	
	public static void dump(byte[] array) {
		dump(array, 0, array.length);
	}
	
	public static void dump(byte[] array, int index, int length) {
		if(array == null) {
			System.out.println("(null byte array)");
			return;
		}
		String line = "";
		String side = "";
		int column = 0;
			
		for (int i = index, count = length; count > 0; i++,count--) {
			byte b = array[i];
			if(b >= 0 && b < 32) {
				line += ".";
			} else {
				line += (char)(b);
			}
			side += toHex(b)+" ";
			column++;
			if(column >= 16) {
				System.out.println(line+" | "+side);
				line = "";
				side = "";
				column = 0;
			}
		}
		if(column > 0) {
			for(;column < 16;column++) {
				line += " ";
				side += "   ";
			}
			System.out.println(line+" | "+side);
		}
	}
	
	public static String toHex(byte value) {
		int d1 = (value>>4)&0xF;
		int d2 = value & 0xF;
		return Character.forDigit(d1, 16)+""+Character.forDigit(d2, 16);
	}
	
	public static void dumpFile(String filename) throws IOException {
		File file = new File(filename);
		FileInputStream input = new FileInputStream(file);
		byte []data = new byte[(int)file.length()];
		input.read(data);
		System.out.println(filename+":");
		dump(data);
	}
}
