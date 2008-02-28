/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.resource;

public class JarInstaller extends DataUpgrader
{
	public JarInstaller(Resource resource, int version)
		{ super(resource, -1, version); }

	public byte[] upgrade(byte[] val) throws Exception
	{
		ResResult rr = getResource().loadJar(version1);
		return rr.bytes;
	}
public String getDescription()
{
	return "Start with version " + version1() + " from JAR file.";
}
	
}
