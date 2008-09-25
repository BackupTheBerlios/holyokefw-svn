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

import java.util.Arrays;
import java.util.IdentityHashMap;

public class CuckooHashMap {
	private int size;
	private int threshold;
	private int[] table;
	private Object[] objects;
	private IdentityHashMap lost;
	
	private static final int INITIAL_SIZE = 256;
	private static final float LOAD_FACTOR = 0.3f;
	private static final float MAX_LOOP = 10;
	public CuckooHashMap() {
		this.size = 0;
		this.table = new int[INITIAL_SIZE];
		this.threshold = (int)(INITIAL_SIZE*LOAD_FACTOR);
		this.objects = new Object[INITIAL_SIZE];
		this.lost = new IdentityHashMap();
		Arrays.fill(table, -1);
	}
	
	private int hash1(Object obj) {
		return System.identityHashCode(obj) & 0x7FFFFFFF;		
	}
	private int hash2(Object obj) {
		return ((System.identityHashCode(obj)>>16) & 0xFFFF)|((System.identityHashCode(obj)<<16) & 0x7FFF0000);		
	}
	private void growObjects() {
		int newLen = (objects.length<<1)+1;
		Object[] newObj = new Object[newLen];
		System.arraycopy(objects, 0, newObj, 0, size);
		objects = newObj;
	}
	
	private void growTable() {
		table = new int[(table.length<<1)+1];
		Arrays.fill(table, -1);
		lost.clear();
		for (int i = 0; i < size; i++) {
			insert(objects[i], i);
		}
		threshold = (int)(table.length*LOAD_FACTOR);
		
	}
	
	public int assign(Object obj) {
		if(size >= threshold) {
			growTable();
		}
		if(size >= objects.length) {
			growObjects();
		}
		insert(obj, size);
		return size++;
	}
	
	private void insert(Object obj, int handle) {
		objects[handle] = obj;
		int n1 = handle;
		int n2 = -1;
		for(int loop = 0; loop < MAX_LOOP; loop++) {
			int h1=hash1(objects[n1])%table.length;
			n2 = table[h1];
			table[h1] = n1;
			if(n2 == -1) {
				break;
			}
			int h2 = hash2(objects[n2])%table.length;
			n1 = table[h2];
			table[h2] = n2;
			if(n1 == -1) {
				break;
			}
		}
		if((n1 != -1) && (n2 != -1)) {
			lost.put(objects[n1], new Integer(n1));
		}
		
	}
	
	
	public int lookup(Object obj) {
		int h=hash1(obj)%table.length;
		if(table[h] != -1 && objects[table[h]]==obj) {
			return table[h];
		}
		h=hash2(obj)%table.length;
		if(table[h] != -1&& objects[table[h]]==obj) {
			return table[h];
		}
		Integer val = (Integer)lost.get(obj);
		if(val != null) {
			return val.intValue();
		}
		return -1;
	}
	
	public int lost() {
		return lost.size();
	}
}
