///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package citibob.config;
//
//import citibob.io.IOUtils;
//import citibob.template.Template;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileFilter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//
///**
// * Utility class used to copy configuration trees.
// * @author citibob
// */
//public class DirConfigCopier {
//
//FileFilter includeDirs;
//FileFilter includeFiles;
//FileFilter templateFiles;
////File root;
//
//public DirConfigCopier(
//FileFilter includeDirs,
//FileFilter includeFiles,
//FileFilter templateFiles)
//throws IOException
//{
//	if (includeDirs == null) includeDirs = new FileFilter() {
//	public boolean accept(File dir) {
//		if (dir.getName().startsWith(".")) return false;
//		return true;
//	}};
//	this.includeDirs = includeDirs;
//	
//	if (includeFiles == null) {
//		includeFiles = new FileFilter() {
//		public boolean accept(File file) {
//			if (file.getName().endsWith("~")) return false;
//			if (file.getName().endsWith(".bak")) return false;
//			return true;
//		}};
//	}
//	this.includeFiles = includeFiles;
//	
//	if (templateFiles == null) {
//		templateFiles = new FileFilter() {
//		public boolean accept(File dir) {
//			if (dir.getName().endsWith(".template")) return true;
//			return false;
//		}};
//	}
//	this.templateFiles = templateFiles;
//}
//
//
///**
// * 
// * @param baseDir
// * @param inDir
// * @param outDir
// * @param map Use these values on any templates you find in the directory.
// * @param includeDirs
// * @param includeFiles
// * @param templateFiles
// * @throws java.io.IOException
// */
//public void copyConfig(File baseDir,
//File inDir, File outDir,
//Map<String,Object> map)
//throws IOException
//{
//	// Do files in this directory
//	File[] files = inDir.listFiles();
//	for (File f : files) {
//		File inFile = f;
//		File outFile = new File(outDir, f.getName());
//		if (f.isDirectory()) {
//			if (includeDirs.accept(f)) {
//				copyConfig(baseDir, inFile, outFile, map);
//			}
//		} else {
//			if (!includeFiles.accept(f)) continue;
//
//			outDir.mkdirs();
//			if (templateFiles.accept(f)) {
//				String name = f.getName();
//				int dot = name.lastIndexOf('.');
//				name = name.substring(0,dot);
//				
//				outFile = new File(outDir, name);
//				
//				Template template = new Template(inFile.toURL());
//				for (Entry<String,Object> en : map.entrySet()) {
//					template.put(en.getKey(), en.getValue());
//				}
//				Writer out = new BufferedWriter(new FileWriter(outFile));
//				template.write(out);
//				out.close();
//			} else {
//				// Copy this file to the output...
//				IOUtils.copy(inFile, outFile);
//			}
//		}
//	}
//}
//
//public void copyConfig(File inDir, File outDir,
//Map<String,Object> map)
//throws IOException
//{
//	copyConfig(inDir, inDir, outDir, map);	
//}
//
//
//// =============================================================
//public static void main(String[] args) throws Exception
//{
//	File configRoot = new File("/export/home/citibob/mvn/hokserver/configs");
//	File inDir = new File(configRoot, "custs/_proto");
//	File outDir = new File(configRoot, "custs/ballettheatre");
//
//	Map<String,Object> map = new TreeMap();
//	map.put("custname", "ballettheatre");
//	map.put("db.password", "xyzxyz");
//	
//	DirConfigCopier ct = new DirConfigCopier(null, null, null);
//	ct.copyConfig(inDir, outDir, map);
//}
//
//
//}
