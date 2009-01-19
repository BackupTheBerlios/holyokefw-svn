/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.app.App;
import java.io.File;

/**
 *
 * @author citibob
 */
public class MultiConfigMaker implements ConfigMaker {

Object[] configs;

public MultiConfigMaker(Object... configs)
{
	this.configs = configs;
}

public Config newConfig(App app)
{
	MultiConfig ret = new MultiConfig();
	for (Object obj : configs) {
		if (obj instanceof Config) {
			ret.add((Config)obj);
		} else if (obj instanceof File) {
			ret.add(new DirConfig((File)obj));
		} else if (obj instanceof String) {
			ret.add(new ResourceConfig((String)obj));
		} else if (obj instanceof ConfigMaker) {
			ConfigMaker maker = (ConfigMaker)obj;
			ret.add(maker.newConfig(app));
		} else {
			throw new IllegalArgumentException("class " + obj.getClass());
		}
	}
	if (ret.size() == 1) return ret.get(0);
	return ret;
}


}
