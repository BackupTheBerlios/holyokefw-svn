
import citibob.reflect.ClassPathUtils;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author citibob
 */
public class RunWsdl {

//import org.apache.
	
   // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }
	
public static void main(String[] args)
{
	File root = ClassPathUtils.getMavenProjectRoot();
	File java = new File(root, "src/main/java");
	File com = new File(java, "com");
	
	deleteDir(com);
			
	org.apache.axis.wsdl.WSDL2Java.main(new String[] {
		"-o", java.toString(),
		"https://api.jangomail.com/api.asmx?WSDL"
	});
	
	System.out.println("Done!");
}
//	java -cp ../target/executable-netbeans.dir/offstagearts-*.jar org.apache.axis.wsdl.WSDL2Java 
//-o out https://api.jangomail.com/api.asmx?WSDL
	
}
