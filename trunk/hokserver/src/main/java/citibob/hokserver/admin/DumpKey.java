package citibob.hokserver.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;

/* DumpKey.java
 * Copyright (c) 2007 by Dr. Herong Yang, http://www.herongyang.com/
 */
public class DumpKey {

public static void dumpKey(File jksFile, char[] jksPass,
String keyName, char[] keyPass, File outFile)
throws Exception
{
         KeyStore jks = KeyStore.getInstance("jks");
         jks.load(new FileInputStream(jksFile), jksPass);
         Key key = jks.getKey(keyName, keyPass);
         System.out.println("Key algorithm: "+key.getAlgorithm());
         System.out.println("Key format: "+key.getFormat());
         System.out.println("Writing key in binary form to "
            +outFile);

         FileOutputStream out = new FileOutputStream(outFile);
         out.write(key.getEncoded());
         out.close();
}
   static public void main(String[] a) {
      if (a.length<5) {
         System.out.println("Usage:");
         System.out.println(
            "java DumpKey jks storepass alias keypass out");
         return;
      }
      File jksFile = new File(a[0]);
      char[] jksPass = a[1].toCharArray();
      String keyName = a[2];
      char[] keyPass = a[3].toCharArray();
      File outFile = new File(a[4]);

      try {
		  dumpKey(jksFile, jksPass, keyName, keyPass, outFile);
//         KeyStore jks = KeyStore.getInstance("jks");
//         jks.load(new FileInputStream(jksFile), jksPass);
//         Key key = jks.getKey(keyName, keyPass);
//         System.out.println("Key algorithm: "+key.getAlgorithm());
//         System.out.println("Key format: "+key.getFormat());
//         System.out.println("Writing key in binary form to "
//            +outFile);
//
//         FileOutputStream out = new FileOutputStream(outFile);
//         out.write(key.getEncoded());
//         out.close();
      } catch (Exception e) {
         e.printStackTrace();
         return;
      }
   }
}