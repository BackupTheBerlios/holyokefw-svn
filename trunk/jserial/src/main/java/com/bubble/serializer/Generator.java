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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.compiler.Javac.CtFieldWithInit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javassist.ClassPath;
/**
 * Generates customized code for serialization.
 * This is where the magic happens.
 * 
 * <p>The generator inspects classes with reflection to generate
 * code that performs serialization of fields in the most direct way possible.
 * For non-private fields, the generated code will access fields directly, not
 * through reflection.
 * 
 * @author Leonardo "Bubble" Mesquita
 *
 */
public class Generator {
	private static final Map serializers = new HashMap();
	private static final Map deserializers = new HashMap();
	
	private static final String OBJECT_PARAM = "obj";
	private static final String BUFFER_VAR = "buffer";
	private static final String OBJECT_VAR = "object";
	private static final String CONTEXT_VAR = "context";
	
	private static final String FIELD_SUFFIX = "_field";

	private static final String OBJECT_PARAM_DECL = Object.class.getName()+" "+OBJECT_PARAM;
//private static final String BUFFER_VAR_DECL = ByteBuffer.class.getName()+" "+BUFFER_VAR;
	private static final String DATAOUTPUT_VAR_DECL = DataOutput.class.getName()+" "+BUFFER_VAR;
	private static final String DATAINPUT_VAR_DECL = DataInput.class.getName()+" "+BUFFER_VAR;
	private static final String SERIALIZATION_CONTEXT_VAR_DECL = SerializationContext.class.getName()+" "+CONTEXT_VAR;
	private static final String DESERIALIZATION_CONTEXT_VAR_DECL = DeserializationContext.class.getName()+" "+CONTEXT_VAR;

	private static final String THROWS_IOEXCEPTION = "throws " + IOException.class.getName();
	
	ClassLoader classloader;
	
	public Generator(ClassLoader classloader)
	{
		this.classloader = classloader;
	}
	
	public Serializer getSerializer(Class clazz) {
		Serializer result = (Serializer)serializers.get(clazz);
		if(result == null) {
			result = generateSerializer(clazz);
			serializers.put(clazz, result);
		}
		return result;		
	}
	
	private static class FieldComparator implements Comparator {
		public int compare(Object obj1, Object obj2) {
			Field f1 = (Field)obj1;
			Field f2 = (Field)obj2;			
			return f1.getName().compareTo(f2.getName());
		}
	}
	
	private static final FieldComparator fieldComparator = new FieldComparator();
	
ClassPool newClassPool()
{
	ClassPool cpp = new ClassPool(ClassPool.getDefault()) {
		protected CtClass createCtClass(String classname, boolean useCache)
		{
			CtClass cl = super.createCtClass(classname, useCache);
			System.out.println("createCtClass: " + classname + " -> " + cl);
			return cl;
		}
	};
	ClassPath cp = new ClassPath() {
		public void close() { }
		public java.net.URL find(String classname) {
//			URL url = super.find(classname);
//			if (url != null) return url;
	
			//System.out.println("ClassPath classname = " + classname);
			String slashName = classname.replace('.', '/') + ".class";
			URL url = classloader.getResource(slashName);
System.out.println("ClassPath.find(" + classname + ") = " + url);
			return url;
		}
		public InputStream openClassfile(String classname) {
//System.out.println("ClassPool.openClassfile: " + classname);
			String slashName = classname.replace('.', '/') + ".class";
			return classloader.getResourceAsStream(slashName);			
		}
	};
	cpp.appendClassPath(cp);
	return cpp;
}
	private CtClass createSerializationClass(String className, boolean deserializer) {
//		ClassPool cp = (deserializer ? newClassPool() : ClassPool.getDefault());
//		ClassPool cp = newClassPool();
		ClassPool cp = ClassPool.getDefault();

		CtClass cl = cp.makeClass(className+"$__"+(deserializer?"DE":"")+"SERIALIZER__");
		
		String interfaceName = (deserializer?Deserializer.class.getName():Serializer.class.getName());
		try {
			cl.addInterface(cp.get(interfaceName));
		} catch (NotFoundException e) {
			throw new NoClassDefFoundError("Could not find Serializer interface");
		}
		
		return cl;
	}
	
	private void setSuperclass(CtClass cl, Class superClass, boolean deserializer) {
//		ClassPool cp = (deserializer ? newClassPool() : ClassPool.getDefault());
//		ClassPool cp = newClassPool();
		ClassPool cp = ClassPool.getDefault();		
System.out.println("setSuperclass(" + superClass + ")");
		Class sp = (deserializer?(Object)getDeserializer(superClass):(Object)getSerializer(superClass)).getClass();
		try {
			cl.setSuperclass(cp.get(sp.getName()));
		} catch (NotFoundException e) {
			Error e2 = new NoClassDefFoundError("Could not find Serializer interface");
			e2.initCause(e);
			throw e2;
		} catch (CannotCompileException e) {
			throw new GeneratorRuntimeException("Could not generate serializer for subclass of "+superClass.getName(), e);
		}
	}
	
	private void addMethod(String body, CtClass cl, String className) {
		try {
System.out.println("body = " + body);
			cl.addMethod(CtNewMethod.make(body, cl));
		} catch (CannotCompileException e) {
			throw new GeneratorRuntimeException("Could not generate serializer for class "+className, e);
		}
	}
	
	private Object createInstance(ClassLoader classloader, CtClass cl, String className) {
		try {			
			Object result = cl.toClass(classloader, null).newInstance();
			return result;
		} catch (InstantiationException e) {
			throw new GeneratorRuntimeException("Could not generate serializer for class "+className, e);
		} catch (IllegalAccessException e) {
			throw new GeneratorRuntimeException("Could not generate serializer for class "+className, e);
		} catch (CannotCompileException e) {
			throw new GeneratorRuntimeException("Could not generate serializer for class "+className, e);
		}
	}
	
	
	private String generateSerializeInstanceInfoMethodHeader(String className) {
		String body = "public void serializeInstanceInfo("
			+ OBJECT_PARAM_DECL+", "
			+ DATAOUTPUT_VAR_DECL+", " 
			+ SERIALIZATION_CONTEXT_VAR_DECL+") " + THROWS_IOEXCEPTION + " {\n";	
		return body;
	}
	
	private String generateObjectCast(String className) {
		return "\t"+className+ " "+OBJECT_VAR +" = ("+className+") "+OBJECT_PARAM+";\n";
	}

	
	private String generateSerializeMethodHeader(String className) {
		String body = "public void serialize("
			+ OBJECT_PARAM_DECL+", "
			+ DATAOUTPUT_VAR_DECL+", " 
//			+ SERIALIZATION_CONTEXT_VAR_DECL+") {\n";
			+ SERIALIZATION_CONTEXT_VAR_DECL+") " + THROWS_IOEXCEPTION + " {\n";	
		
		return body;
	}
	
	private String generateInstantiateMethodHeader(String className) {
		String body = "public "+Object.class.getName()+" instantiate("
			+ DATAINPUT_VAR_DECL+", " 
			+ DESERIALIZATION_CONTEXT_VAR_DECL+") {\n";
		return body;
	}

	private String generateDeserializeMethodHeader(String className) {
		String body = "public void deserialize("
			+ OBJECT_PARAM_DECL+", "
			+ DATAINPUT_VAR_DECL +", " 
			+ DESERIALIZATION_CONTEXT_VAR_DECL+") " + THROWS_IOEXCEPTION + " {\n";	
		
		return body;
	}

	
	private Serializer generateSerializer(Class clazz) {
		if(clazz.isArray()) {
			return generateArraySerializer(clazz);
		}
		
		if (String.class.equals(clazz)) {
			return StringSerializer.getInstance();
		}
		
		if(!Serializable.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("Class "+clazz.getName()+ " is not Serializable");
		}
		
		Class superClass = clazz.getSuperclass();
//		boolean hasSerialSuper = (
//			Serializable.class.isAssignableFrom(superClass)
//			&& !superClass.isInterface()); 
		boolean hasSerialSuper = Serializable.class.isAssignableFrom(superClass);
System.out.println("hasSerialSuper(superclass " + superClass.getName() + ") = " + hasSerialSuper);
		String className = clazz.getName();
		
		CtClass cl = createSerializationClass(className, false);
		
		if(hasSerialSuper) {
			setSuperclass(cl, clazz.getSuperclass(), false);
		}
		
		Field[] fields = clazz.getDeclaredFields();
		// Sorts fields alphabetically by name
		Arrays.sort(fields, fieldComparator); 

		generatePrivateFieldAccessors(cl, clazz, fields);
		
		// Creates an empty "serializeInstanceInfo" method
		String body = generateSerializeInstanceInfoMethodHeader(className);
		body += "}\n";
		
		addMethod(body, cl, className);
		
		body = generateSerializeMethodHeader(className);
		
		if(hasSerialSuper) {
			body += "\tsuper.serialize("+OBJECT_PARAM+", "+BUFFER_VAR+ ", "+CONTEXT_VAR+");\n";
		}
		
		body += generateObjectCast(className);	
		
		
		for (int i = 0; i < fields.length; i++) {			
			Field field = fields[i];
			body += generateFieldSerialization(field);				
		}			
		
		body += "}\n";
		
		addMethod(body, cl, className);
		
		return (Serializer)createInstance(clazz.getClassLoader(), cl, className);
	}
	
	private String getCastName(Class clazz) {
		if(!clazz.isArray()) {
			return clazz.getName();
		}
		Class baseType = clazz.getComponentType();
		int depth = 1;
		while(baseType.isArray()) {
			baseType = baseType.getComponentType();
			depth++;
		}

		String className = baseType.getName();		
		for (int i = depth; i > 0; i--) {
			className += "[]";
		}
		return className;
	}
	
	private Serializer generateArraySerializer(Class clazz) {
		String arrayName = clazz.getName();

		String className = getCastName(clazz);		
		
		String clname = arrayName.replaceAll("\\[", "_ARR\\$").replaceAll(";", "_");

		CtClass cl = createSerializationClass(clname, false); 
		
		// Creates a "serializeInstanceInfo" method
		String body = generateSerializeInstanceInfoMethodHeader(className);
		body += generateObjectCast(className);
		body += "\t"+BUFFER_VAR+".writeInt("+OBJECT_VAR+".length);\n";
		body += "}\n";
		
		addMethod(body, cl, className);
		
		body = generateSerializeMethodHeader(className);
		body += generateObjectCast(className);
		
		body += "\tfor(int i = 0; i < "+OBJECT_VAR+".length; i++) {\n";
		body += generateElementSerialization(OBJECT_VAR+"[i]", clazz.getComponentType(), "\t\t");
		body += "\t}\n";
		body += "}\n";
		
		addMethod(body, cl, className);
		return (Serializer)createInstance(clazz.getClassLoader(), cl, className);				
	}
	
	private static final int bannedMods = Modifier.STATIC | Modifier.TRANSIENT | Modifier.FINAL;
	private String generateFieldSerialization(Field field) {		
		int modifiers = field.getModifiers();
		if((modifiers & bannedMods) != 0) {
			return "";
		}
		if(Modifier.isPrivate(modifiers)) {
			return generatePrivateElementSerialization(field.getName(), field.getType(), "\t");
		} else {
			return generateElementSerialization(OBJECT_VAR+"."+field.getName(), field.getType(), "\t");
		}
	}
	
	private void generatePrivateFieldAccessors(CtClass cl, Class clazz, Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if(!Modifier.isPrivate(field.getModifiers())) {
				continue;
			}
			String decl = "private "+Field.class.getName()+" "+field.getName()+FIELD_SUFFIX+" = "
				+FieldResolver.class.getName()+".getField("+clazz.getName()+".class, \""+field.getName()+"\");";
			CtField cf;
			try {
				cf = CtFieldWithInit.make(decl, cl);			
				cl.addField(cf);
			} catch (CannotCompileException e) {
				throw new GeneratorRuntimeException("Could not generate private field accessor for "+field.toString(), e);				
			}
		}
	}
	
	private String generatePrivateElementSerialization(String fieldName, Class type, String tabs) {
		String accessParam = "("+fieldName+FIELD_SUFFIX+", "+OBJECT_VAR+")";
		if(boolean.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getBoolean"
				+accessParam;
			return tabs+BUFFER_VAR+".write(("+access+")?(byte)1:(byte)0);\n";
		}
		
		if (byte.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getByte"
				+accessParam;
			return tabs+BUFFER_VAR+".write("+access+");\n";
		}
		
		if (short.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getShort"
				+accessParam;
			return tabs+BUFFER_VAR+".writeShort("+access+");\n";
		}
		
		if (int.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getInt"
				+accessParam;
			return tabs+BUFFER_VAR+".writeInt("+access+");\n";
		}		

		if (long.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getLong"
				+accessParam;
			return tabs+BUFFER_VAR+".writeLong("+access+");\n";
		}
		
		if (float.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getFloat"
				+accessParam;
			return tabs+BUFFER_VAR+".writeFloat("+access+");\n";
		}
		
		if (double.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getDouble"
				+accessParam;
			return tabs+BUFFER_VAR+".writeDouble("+access+");\n";
		}
		
		if (char.class.equals(type)) {
			String access = FieldResolver.class.getName()+".getChar"
				+accessParam;
			return tabs+BUFFER_VAR+".writeChar("+access+");\n";
		}
		String access = FieldResolver.class.getName()+".get"
			+accessParam;
		return tabs+CONTEXT_VAR+".serialize("+access+", "+BUFFER_VAR+");\n";
	}
	
	private String generateElementSerialization(String name, Class type, String tabs) {
				
		if(boolean.class.equals(type)) {
			return tabs+BUFFER_VAR+".write(("+name+")?(byte)1:(byte)0);\n";
		}
		
		if (byte.class.equals(type)) {
			return tabs+BUFFER_VAR+".write("+name+");\n";
		}
		
		if (short.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeShort("+name+");\n";
		}
		
		if (int.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeInt("+name+");\n";
		}		

		if (long.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeLong("+name+");\n";
		}
		
		if (float.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeFloat("+name+");\n";
		}
		
		if (double.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeDouble("+name+");\n";
		}
		
		if (char.class.equals(type)) {
			return tabs+BUFFER_VAR+".writeChar("+name+");\n";
		}
		return tabs+CONTEXT_VAR+".serialize("+name+", "+BUFFER_VAR+");\n";
	}
	
	public Deserializer getDeserializer(String className) throws ClassNotFoundException {
		Class klass = Class.forName(className, true, classloader);
		return getDeserializer(klass);
//		return getDeserializer(classloader.l.Class.forName(className));
	}
	
	public Deserializer getDeserializer(Class clazz) {
		Deserializer result = (Deserializer)deserializers.get(clazz);
		if(result == null) {
			result = generateDeserializer(clazz);
			deserializers.put(clazz, result);
		}
		return result;	
	}
		
	private Deserializer generateDeserializer(Class clazz) {
		
		if(clazz.isArray()) {
			return generateArrayDeserializer(clazz);
		}
		
		if(String.class.equals(clazz)) {
			return StringDeserializer.getInstance();
		}
		
		if(!Serializable.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("Class "+clazz.getName()+ " is not Serializable");
		}
		
//		boolean hasSerialSuper = Serializable.class.isAssignableFrom(clazz.getSuperclass()); 
		Class superClass = clazz.getSuperclass();
		boolean hasSerialSuper = Serializable.class.isAssignableFrom(superClass);
//		boolean hasSerialSuper = (
//			Serializable.class.isAssignableFrom(superClass)
//			&& !superClass.isInterface()); 
		
		String className = clazz.getName();		
		
		CtClass cl = createSerializationClass(className, true);
		if(hasSerialSuper) {
			setSuperclass(cl, clazz.getSuperclass(), true);
		}
		
		Field[] fields = clazz.getDeclaredFields();
		// Sorts fields alphabetically by name
		Arrays.sort(fields, fieldComparator); 
		
		generatePrivateFieldAccessors(cl, clazz, fields);
		
		String body = generateInstantiateMethodHeader(className);
		body += "\treturn new "+className+"();\n";
		body += "}\n";
		
		addMethod(body, cl, className);
		
		body = generateDeserializeMethodHeader(className);
		
		if(hasSerialSuper) {
			body += "\tsuper.deserialize("+OBJECT_PARAM+", "+BUFFER_VAR+", "+CONTEXT_VAR+");\n";
		}
		
		body += generateObjectCast(className);
		
		for (int i = 0; i < fields.length; i++) {			
			Field field = fields[i];
			body += generateFieldDeserialization(field);
				
		}			

		body += "}\n";
//System.out.println(body);
		addMethod(body, cl, className);
		
		return (Deserializer)createInstance(clazz.getClassLoader(), cl, className);

	}
	
	private String generateFieldDeserialization(Field field) {		
		int modifiers = field.getModifiers();
		if((modifiers & bannedMods) != 0) {
			return "";
		}
		if(Modifier.isPrivate(modifiers)) {
			return generatePrivateElementDeserialization(field.getName(), field.getType(), "\t");
			
		} else {
			return generateElementDeserialization(OBJECT_VAR+"."+field.getName(), field.getType(), "\t");
		}
		
	}
	
	private String generatePrivateElementDeserialization(String fieldName, Class type, String tabs) {
		String prefix = FieldResolver.class.getName()+".";
		String method;
		String accessParam = "("+fieldName+FIELD_SUFFIX+", "+OBJECT_VAR+", ";
		String value;
		String suffix = ");\n";
		if(boolean.class.equals(type)) {
			value = "("+BUFFER_VAR+".get() != 0)";
			method = "setBoolean";			
		}		
		else if (byte.class.equals(type)) {
			value = BUFFER_VAR+".get()";
			method = "setByte";
		}
		
		else if (short.class.equals(type)) {
			value = BUFFER_VAR+".getShort()";
			method = "setShort";
		}
		
		else if (int.class.equals(type)) {
			value = BUFFER_VAR+".getInt()";
			method = "setInt";
		}		

		else if (long.class.equals(type)) {
			value = BUFFER_VAR+".getLong()";
			method = "setLong";
		}
		
		else if (float.class.equals(type)) {
			value = BUFFER_VAR+".getFloat()";
			method = "setFloat";
		}
		
		else if (double.class.equals(type)) {
			value = BUFFER_VAR+".getDouble()";
			method = "setDouble";
		}
		
		else if (char.class.equals(type)) {
			value = BUFFER_VAR+".getChar()";
			method = "setChar";
		}
		else {
			value = CONTEXT_VAR+".deserialize("+BUFFER_VAR+")";
			method = "set";			
		}
		return tabs+prefix+method+accessParam+value+suffix;
	}
	
	private String generateElementDeserialization(String name, Class type, String tabs) {
		
		if(boolean.class.equals(type)) {
			return tabs+name+" = ("+BUFFER_VAR+".readByte() != 0);\n";
		}
		
		if (byte.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readByte();\n";
		}
		
		if (short.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readShort();\n";
		}
		
		if (int.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readInt();\n";
		}		

		if (long.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readLong();\n";
		}
		
		if (float.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readFloat();\n";
		}
		
		if (double.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readDouble();\n";
		}
		
		if (char.class.equals(type)) {
			return tabs+name+" = "+BUFFER_VAR+".readChar();\n";
		}
		return tabs+name+" = ("+getCastName(type)+") "+CONTEXT_VAR+".deserialize("+BUFFER_VAR+");\n";
	}
	
	private Deserializer generateArrayDeserializer(Class clazz) {
		String arrayName = clazz.getName();

		Class baseType = clazz.getComponentType();
		int depth = 0;
		while(baseType.isArray()) {
			baseType = baseType.getComponentType();
			depth++;
		}

		String className = baseType.getName()+"[]";
		String newOperation = baseType.getName()+"[len]";
		for (int i = depth; i > 0; i--) {
			className += "[]";
			newOperation += "[]";
		}				
		
		String clname = arrayName.replaceAll("\\[", "_ARR\\$").replaceAll(";", "_");;

		CtClass cl = createSerializationClass(clname, true); 
		
		String body = generateInstantiateMethodHeader(className);
		body += "\tint len = "+BUFFER_VAR+".readInt();\n";
		body += "\treturn new "+newOperation+";\n"; 
		body += "}\n";
		
		addMethod(body, cl, className);
		
		body = generateDeserializeMethodHeader(className);
		body += generateObjectCast(className);
		
		body += "\tfor(int i = 0; i < "+OBJECT_VAR+".length; i++) {\n";
		body += generateElementDeserialization(OBJECT_VAR+"[i]", clazz.getComponentType(), "\t\t");
		body += "\t}\n";
		body += "}\n";
		
		addMethod(body, cl, className);
		return (Deserializer)createInstance(clazz.getClassLoader(), cl, className);				
	}

	private static class StringSerializer implements Serializer {
		private StringSerializer() {			
		}
		
		public static StringSerializer getInstance() {
			return new StringSerializer();
		}
		public void serializeInstanceInfo(Object obj, DataOutput buffer, SerializationContext context)
		throws IOException
		{
			String str = (String)obj;			
			buffer.writeUTF(str); // StringUtils.serialize(str, buffer);
			
		}
		public void serialize(Object obj, DataOutput buffer, SerializationContext context) {
			// Strings have no state data, since they're immutable			
		}
	}
	
	private static class StringDeserializer implements Deserializer {
		private StringDeserializer() {			
		}
		
		public static StringDeserializer getInstance() {
			return new StringDeserializer();
		}		
		
		public Object instantiate(DataInput buffer, DeserializationContext context)
		throws IOException
		{
			return buffer.readUTF();
//			return StringUtils.deserialize(buffer);
		}
		
		public void deserialize(Object obj, DataInput buffer, DeserializationContext context)
		throws IOException {
			// Strings have no state data, since they're immutable			
		}
	}
}
