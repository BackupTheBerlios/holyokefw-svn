
import citibob.gui.AppLauncher;
import citibob.hokserver.ConfigApp;
import citibob.hokserver.admin.ConfigAdmin;

/**
 *
 * @author citibob
 */
public class Main {
public static void main(String[] args) throws Exception {

	AppLauncher.launch("hokserver", new Class[] {
		ConfigApp.class,
		ConfigAdmin.class
	});
}
}
