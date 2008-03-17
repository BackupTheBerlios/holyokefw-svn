
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
public static void main(String[] args) throws Exception {
//	
//	
//	
//	
//	
//	
//	
//	
//                bsh.Interpreter bsh = new bsh.Interpreter();
//
//                // Evaluate statements and expressions
//                bsh.eval("foo=Math.sin(0.5)");
//                bsh.eval("bar=foo*5; bar=Math.cos(bar);");
//                bsh.eval("for(i=0; i<10; i++) { print(\"hello\"); }");
//                // same as above using java syntax and apis only
//                bsh.eval("for(int i=0; i<10; i++) { System.out.println(\"hello\"); }");
//System.exit(0);

	AppLauncher.launch("holyokefw", new Class[] {
		IndexSet.class,
		MakeNbm.class,
//		de.jppietsch.prefedit.PrefEdit.class,
		Licensor.class
	});
}
}
