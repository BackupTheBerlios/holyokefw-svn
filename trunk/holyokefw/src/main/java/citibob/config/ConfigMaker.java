/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.app.App;

/**
 * Factory for providing a configuration to an application
 * @author citibob
 */
public interface ConfigMaker {
	
public Config newConfig(App app);

}
