/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.mvcgen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author fiscrob
 */
public class MVCTree {

public static void processMVC(MVCGen g, File inFile, File outFile)
throws IOException
{
	Reader in = new FileReader(inFile);
//	EMVCGen g = new EMVCGen(in);
	g.open(in);
	Writer out = new FileWriter(outFile);
	g.doWrite(out);
	out.close();
}

/** @param suffix with dot. */
public static void processMVC(MVCGen mvc, File inFile)
throws IOException
{
	String path = inFile.getPath();
	if (!path.endsWith(mvc.getSuffix())) return;
//	if (!path.endsWith(".emvc")) return;
	System.out.println("Processing " + inFile);
	File outFile = new File(path.substring(0,path.length()-3) + "java");
	processMVC(mvc, inFile, outFile);
}
public static void processMVCTree(MVCGen mvc, File root)
throws IOException
{
//System.out.println(new File(".").getAbsolutePath());
System.out.println("root = " + root);
	if (root.getName().startsWith(".")) return;
	File[] files = root.listFiles();
	for (int i=0; i<files.length; ++i) {
		if (files[i].isDirectory()) processMVCTree(mvc, files[i]);
		else processMVC(mvc, files[i]);
	}
	System.out.println("Done processing all files!");
}

	
}
