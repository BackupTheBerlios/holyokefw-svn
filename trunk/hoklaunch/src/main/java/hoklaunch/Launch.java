package hoklaunch;

import hoklaunch.citibob.io.IOUtils;
import hoklaunch.citibob.io.RobustOpen;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class Launch 
{
	
static class Deleter {
	File file;
	public Deleter(File file) { this.file = file; }
	public void finalize()
	{
		System.out.println("Deleting " + file);
		file.delete();
	}
}
	
public static void main( String[] args ) throws Exception
{
	ClassLoader cl = Launch.class.getClassLoader();

	// Load the config properties from our resources (as opposed
	// to from the zips file)
	Properties props = new Properties();
	InputStream propsIn = cl.getResourceAsStream("config.properties");
	props.load(propsIn);
	propsIn.close();
	
	// Get the relevant properties
	String appName = props.getProperty("app.name");
	String appURL = props.getProperty("app.url");
	String configName = props.getProperty("config.name");
	System.out.println("app.name = " + appName);
	System.out.println("app.url = " + appURL);
	System.out.println("config.name = " + configName);
	
	// Copy config.properties
	String userHome = System.getProperty("user.home");
	final File propsFile = new File(userHome, appName + "_config.properties");
	IOUtils.copy(cl.getResourceAsStream("config.properties"), propsFile);
	
	// Copy configuration to appropriate place
	final File zipsFile = new File(userHome, appName + "_config.zips");
	IOUtils.copy(cl.getResourceAsStream("config.zips"), zipsFile);
	final long fileMS = zipsFile.lastModified();

	// Delete our file when we're done
    Runtime.getRuntime().addShutdownHook(new Thread() {
	public void run() {
		if (!zipsFile.exists()) return;
		if (zipsFile.lastModified() != fileMS) return;
		System.out.println("Deleting " + zipsFile);
		zipsFile.delete();
		propsFile.delete();
	}});

	
	// Launch it!
	RobustOpen.open(new URL(appURL));

	// Hang around, waiting to delete file if the launch doesn't work
	long maxMS = System.currentTimeMillis() + 5*60*1000L;
	
	
	for (;;) {
		long curMS = System.currentTimeMillis();
		System.out.println("Waiting to exit @" + new Date(curMS));
		if (!zipsFile.exists()) break;
		if (zipsFile.lastModified() != fileMS) break;
		if (curMS >= maxMS) {
			zipsFile.delete();
			propsFile.delete();
			break;
		}	
		Thread.currentThread().sleep(5000L);
	}
	
	System.out.println("Exiting Launch!");
}

}

