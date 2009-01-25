///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.config;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// *
// * @author citibob
// */
//public class DirConfigWriter extends ConfigWriter
//{
//
//File dir;
//
//public DirConfigWriter(File dir)
//{
//	super(null);
//	this.dir = dir;
//}
//public DirConfigWriter(File dir, char[] password)
//{
//	super(password);
//	this.dir = dir;
//}
//	
//@Override
//void writeSimpleEntry(String name, byte[] bytes) throws IOException
//{
//	File file = new File(dir, name);
//	file.getParentFile().mkdirs();
//	
//	OutputStream fout = new FileOutputStream(file);
//	fout.write(bytes);
//	fout.close();
//}
//
//}
