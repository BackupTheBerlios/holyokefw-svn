///*
// * MVCGenTask.java
// *
// * Created on January 9, 2006, 4:42 PM
// *
// * To change this template, choose Tools | Options and locate the template under
// * the Source Creation and Management node. Right-click the template and choose
// * Open. You can then make changes to the template in the Source Editor.
// */
//
//package citibob.mvcgen;
//
//
//import java.io.*;
//import org.apache.tools.ant.*;
//
///**
// * See: http://ant.apache.org/manual/develop.html
// * @author fiscrob
// */
//public class MVCGenTask extends org.apache.tools.ant.Task
//{
//	String fName;
//	
//	public void setFile(String f)
//		{ fName = f; }
//	public void execute() throws org.apache.tools.ant.BuildException
//	{
//		int c = fName.lastIndexOf(".mvc");
//		String oName = (c >= 0 ? fName.substring(0,c) : fName) + ".java";
//
//		try {
//			Reader in = new FileReader(fName);
//			MVCGen g = new MVCGen(in);
//			in.close();
//
//			Writer out = new FileWriter(oName);
//			g.doWrite(out);
//			out.close();
//		} catch(IOException e) {
//			throw new BuildException(e);
//		}
//	}
//	
//	
//}
