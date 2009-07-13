
import citibob.gui.AppLauncher;
import citibob.hokserver.ConfigApp;
import citibob.hokserver.admin.ConfigAdmin;
import citibob.hokserver.admin.ConsolePBEAuth;

/**
 *
 * @author citibob
 */
public class Main {
public static void main(String[] args) throws Exception {

	AppLauncher.launch("hokserver", new Class[] {
		ConsolePBEAuth.class,
		ConfigApp.class,
		ConfigAdmin.class
	});
}
}
