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

public class ArrayUtils {
	public static String toString(boolean[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}
	
	public static String toString(byte[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(short[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(int[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(long[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(float[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(double[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}

	public static String toString(char[] array) {
		if(array == null) {
			return String.valueOf(null);
		}
		if(array.length == 0) {
			return "[]";
		}
		String result = "["+array[0];
		for (int i = 1; i < array.length; i++) {
			result += ", "+array[i];
		}
		result += "]";
		return result;
	}


}
