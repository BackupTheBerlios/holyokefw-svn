/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.app.App;
import citibob.config.dialog.ConfigDialog;
import citibob.swingers.JavaSwingerMap;
import java.util.TimeZone;
import java.util.prefs.Preferences;

/**
 *
 * @author citibob
 */
public class DialogConfigMaker implements ConfigMaker
{

String demoResourceRoot;

	public DialogConfigMaker(String demoResourceRoot) {
		this.demoResourceRoot = demoResourceRoot;
	}

	public Config newConfig(App app) {
		ConfigDialog dialog = new ConfigDialog(app, demoResourceRoot);
		dialog.setVisible(true);
		if (!dialog.isOK()) return null;
		return dialog.getConfig();
	}

}
