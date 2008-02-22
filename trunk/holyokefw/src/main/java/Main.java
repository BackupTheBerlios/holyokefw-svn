
import citibob.gui.AppLauncher;
import citibob.licensor.Licensor;
import citibob.licensor.MakeNbm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import citibob.util.IndexSet;

/**
 *
 * @author citibob
 */
public class Main {
public static void main(String[] args) {
	AppLauncher.launch("holyokefw", new Class[] {
		IndexSet.class,
		MakeNbm.class,
//		de.jppietsch.prefedit.PrefEdit.class,
		Licensor.class
	});
}
}
