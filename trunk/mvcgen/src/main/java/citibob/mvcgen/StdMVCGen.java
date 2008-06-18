/*
 * MVCGen.java
 *
 * Created on January 9, 2006, 8:50 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.mvcgen;

import java.io.*;
import java.util.*;

/**
 *
 * @author fiscrob
 */
public class StdMVCGen implements MVCGen
{

public String getSuffix() { return ".mvc"; }
	
	
static final int PREAMBLE = 0;
static final int HEADER1 = 1;
static final int HEADER2 = 2;
static final int PREMETHOD = 3;
static final int POSTMETHOD = 4;
static final int PARAMTYPE = 5;
static final int PARAMNAME = 6;
static final int PARAMNEXT = 7;
static final int DONE=8;

StringBuffer preamble = new StringBuffer();
String lName;
StringBuffer extend = new StringBuffer();
ArrayList methods = new ArrayList();

Method meth;	// current method
Param pp;		// current param
// =================================================
static class Method {
	StringBuffer comments = new StringBuffer();
	String name;
	ArrayList params = new ArrayList();
	ArrayList xthrows = new ArrayList();
}

static class Param {
	String type;
	String name;
}
// =================================================

/** Creates a new instance of MVCGen */
public void doRead(Reader in) throws IOException
{
	CPStreamTokenizer st = new CPStreamTokenizer(in);
	st.slashSlashComments(true);
	st.slashStarComments(true);
	st.ordinaryChar('/');

	int state = PREAMBLE;
	int tok;
	while ((tok = st.nextToken()) != CPStreamTokenizer.TT_EOF)
	switch(state) {
		case PREAMBLE: {
			if ("listeners".equals(st.sval)) state = HEADER1;
			else if (tok > 0) preamble.append((char)tok);
			else preamble.append(st.sval);
		} break;
		case HEADER1 : {
			if (tok != CPStreamTokenizer.TT_WHITESPACE) {
				lName = st.sval;
				state = HEADER2;
			}
		} break;
		case HEADER2 : {
			if (tok == '{') {
				meth = new Method();
				state = PREMETHOD;
			} else extend.append(st.sval);
		} break;
		case PREMETHOD : {
			if (tok == CPStreamTokenizer.TT_WHITESPACE || tok == CPStreamTokenizer.TT_COMMENT) 
				meth.comments.append(st.sval);
			else if (tok == CPStreamTokenizer.TT_WORD) {
				if (tok == ';') break;
				if (tok == '}') {
					state = DONE;
					break;
				}
				meth.name = st.sval;
System.out.println("name = " + meth.name + "(" + meth + ")");
				state = PARAMTYPE;
				pp = new Param();
			}
		} break;
		case POSTMETHOD : {
			if (tok == ';') {
System.out.println("POSTMETHOD: ;");
				state = PREMETHOD;
				methods.add(meth);
System.out.println("Adding meth: " + meth.name + "(" + meth + ")");
				meth = new Method();
			} else if (tok == ',') {
				meth.xthrows.add(",");
			} else if (tok == CPStreamTokenizer.TT_WORD) {
System.out.println("XXXTHROWS: " + st.sval + "(meth = " + meth);
				meth.xthrows.add(st.sval);
			}
		} break;
		case PARAMTYPE : {
			if (tok == ')') {
				state = POSTMETHOD;
			}
			if (tok == CPStreamTokenizer.TT_WORD) {
				pp.type = st.sval;
				state = PARAMNAME;
			}
		} break;
		case PARAMNAME : {
			if (tok == CPStreamTokenizer.TT_WORD) {
				pp.name = st.sval;
				meth.params.add(pp);
				state = PARAMNEXT;
			}
		} break;
		case PARAMNEXT : {
			if (tok == ',') {
				state = PARAMTYPE;
				pp = new Param();
			} else if (tok == ')') {
System.out.println("PARAMNEXT: postmethod");
				state = POSTMETHOD;
//				methods.add(meth);
//				meth = new Method();
			} 
		} break;
		default : ;
	}
}

void writeParamList(PrintWriter pout, Method m)
{
	for (Iterator pi=m.params.iterator(); pi.hasNext(); ) {
		Param p = (Param)pi.next();
		pout.print(p.type);
		pout.print(' ');
		pout.print(p.name);
		if (pi.hasNext()) pout.print(", ");
	}	
}
void writeArgList(PrintWriter pout, Method m)
{
	for (Iterator pi=m.params.iterator(); pi.hasNext(); ) {
		Param p = (Param)pi.next();
		pout.print(p.name);
		if (pi.hasNext()) pout.print(", ");
	}	
}
void writeThrows(PrintWriter pout, Method m)
{
	for (Iterator pi=m.xthrows.iterator(); pi.hasNext(); ) {
		String p = (String)pi.next();
		pout.print(p);
		if (pi.hasNext()) pout.print(" ");
	}	
}

public void doWrite(Writer out)
throws IOException
{
	PrintWriter pout = new PrintWriter(out);
	pout.print(preamble.toString());
	pout.print("public abstract class ");
	pout.print(lName);
	pout.print(extend.toString());
	pout.println("{");
	pout.flush();
	
	// Write listeners
	pout.print("public static interface Listener {");
	for (Iterator ii=methods.iterator(); ii.hasNext();) {
		Method m = (Method)ii.next();
		pout.print(m.comments.toString());
		pout.print("public void " + m.name + "(");
		writeParamList(pout, m);
		pout.print(") ");
		writeThrows(pout, m);
		pout.println(";");
	}
	pout.println("}");
	
	pout.println("// ======================================================");
	
	// Write adapter class
	pout.print("public static class Adapter implements " + lName + ".Listener {");
	for (Iterator ii=methods.iterator(); ii.hasNext();) {
		Method m = (Method)ii.next();
		pout.print(m.comments.toString());
		pout.print("public void " + m.name + "(");
		writeParamList(pout, m);
		pout.print(") ");
		writeThrows(pout, m);
		pout.println("{}");
	}
	pout.println("}");
	
	// Write addListener and removeListener code
	pout.println("// ======================================================");
	pout.println("java.util.LinkedList listeners = new java.util.LinkedList();");
	pout.println("public void addListener(" + lName + ".Listener l)");
	pout.println("\t{ listeners.add(l); }");
	pout.println("public void removeListener(" + lName + ".Listener l)");
	pout.println("\t{ listeners.remove(l); }");
	pout.println("");
	
	pout.println("// ======================================================");
	for (Iterator ii=methods.iterator(); ii.hasNext();) {
		Method m = (Method)ii.next();
		String capName = Character.toUpperCase(m.name.charAt(0)) + m.name.substring(1);
		pout.print("public void fire" + capName + "(");
		writeParamList(pout, m);
		pout.print(") ");
		writeThrows(pout, m);
		pout.println("\n{");
		pout.println("\tfor (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {");
		pout.println("\t\t" + lName + ".Listener l = (" + lName + ".Listener)ii.next();");
		pout.print("\t\tl." + m.name + "(");
		writeArgList(pout, m);
		pout.println(");");
		pout.println("\t}");
		pout.println("}");
	}
	
	// Finish the class
	pout.println("}");
}

public void open(Reader in) throws IOException
{
	CPStreamTokenizer st = new CPStreamTokenizer(in);
//	st.eolIsSignificant(true);
//	st.lowerCaseMode(false);
	st.slashSlashComments(true);
	st.slashStarComments(true);
	//st.lowerCaseMode(true);
	st.ordinaryChar('/');

	doRead(in);
//	int tok;
//	while ((tok = st.nextToken()) != StreamTokenizer.TT_EOF) {
//		System.out.println(st);
//	}
}

//public static void main(String[] args) throws Exception
//{
//	for (int i=0; i<args.length; ++i) {
//		String className = args[i];
//		String sname = className.replace('.', File.separatorChar);
//		
//		File src = new File("./src/main/java");
//		File inFile = new File(src, sname + ".mvc");
//		File outFile = new File(src, sname + ".java");
//
//		FileReader in = new FileReader(inFile);
//		StdMVCGen g = new StdMVCGen(in);
//		Writer out = new FileWriter(outFile);
//		g.doWrite(out);
//		in.close();
//		out.close();
//	}
//}

}
