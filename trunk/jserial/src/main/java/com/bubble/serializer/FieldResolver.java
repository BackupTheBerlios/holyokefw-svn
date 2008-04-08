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

import java.lang.reflect.Field;

public class FieldResolver {
	public static Field getField(Class clazz, String name) {		
		try {
			Field result = clazz.getDeclaredField(name);
			result.setAccessible(true);		
			return result;
		} catch (SecurityException e) {
			throw new FieldResolutionException("Security exception resolving field '"
					+name+"' for class "+clazz.getName(), e);			
		} catch (NoSuchFieldException e) {
			throw new FieldResolutionException("Field '"
					+name+"' not found for class "+clazz.getName(), e);			
		}
	}
	
	public static Object get(Field field, Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void set(Field field, Object obj, Object value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	
	
	
	
	public static boolean getBoolean(Field field, Object obj) {
		try {
			return field.getBoolean(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static byte getByte(Field field, Object obj) {
		try {
			return field.getByte(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static short getShort(Field field, Object obj) {
		try {
			return field.getShort(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static int getInt(Field field, Object obj) {
		try {
			return field.getInt(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static long getLong(Field field, Object obj) {
		try {
			return field.getLong(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static float getFloat(Field field, Object obj) {
		try {
			return field.getFloat(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static double getDouble(Field field, Object obj) {
		try {
			return field.getDouble(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

	public static char getChar(Field field, Object obj) {
		try {
			return field.getChar(obj);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setBoolean(Field field, Object obj, boolean value) {
		try {
			field.setBoolean(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setByte(Field field, Object obj, byte value) {
		try {
			field.setByte(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setShort(Field field, Object obj, short value) {
		try {
			field.setShort(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setInt(Field field, Object obj, int value) {
		try {
			field.setInt(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setLong(Field field, Object obj, long value) {
		try {
			field.setLong(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setFloat(Field field, Object obj, float value) {
		try {
			field.setFloat(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setDouble(Field field, Object obj, double value) {
		try {
			field.setDouble(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}
	
	public static void setChar(Field field, Object obj, char value) {
		try {
			field.setChar(obj, value);
		} catch (IllegalArgumentException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		} catch (IllegalAccessException e) {
			throw new FieldResolutionException("Problems accessing field '"
					+field.getName()+"' for class "+field.getDeclaringClass().getName(), e);			
		}
	}

}
