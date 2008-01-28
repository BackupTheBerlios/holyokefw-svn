package citibob.licensor;

import beans2nbm.gen.*;
import java.io.File;

public class MakeNbm {
    
    /** Creates a new instance of Main */
    public MakeNbm() {
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
	{
		char sep = File.separatorChar;
		Beans2Nbm.Params prm = new Beans2Nbm.Params();
		
		prm.version = "1.0";		// Must be numeric for NetBeans
		prm.projectHome = "/export/home/citibob/mvn/holyokefw";
		prm.jarFolder = "target";		// For Maven
		prm.jarName = "holyokefw-1.0-SNAPSHOT";
		
		prm.description = "Typed Widgets and other advanced Swing components" +
			" for database applications";
		prm.homepage = "http://citibob.net";
		prm.codeName = "org.holyokefw";
		prm.author = "Robert Fischer";
		prm.docsJar = null;
		prm.sourceJar = null;
		prm.displayName = "Holyoke Framework";
		prm.license =
			Licensor.readLicenseFile(MakeNbm.class, "license-long.txt");
		prm.minJDK = "1.5";
		prm.beanNames = new String[] {
			"citibob.swing.typed.JBoolCheckbox",
			"citibob.swing.typed.JKeyedComboBox",
			"citibob.swing.typed.JTypedDateChooser",
			"citibob.swing.typed.JTypedEditableLabel",
			"citibob.swing.typed.JTypedEditableLabelDB",
			"citibob.swing.typed.JTypedFileName",
			"citibob.swing.typed.JTypedLabel",
			"citibob.swing.typed.JTypedPanel",
			"citibob.swing.typed.JTypedScrollPane",
			"citibob.swing.typed.JTypedSelectTable",
			"citibob.swing.typed.JTypedTextField",
			"citibob.swing.typed.KeyedButtonGroup",
			"citibob.swing.CitibobJTable",
			"citibob.swing.ColorsJTypeTable",
			"citibob.swing.ColPermuteTable",
			"citibob.swing.JConsole",
			"citibob.swing.JDialogWiz",
			"citibob.swing.JTextAreaWriter",
			"citibob.swing.JTypeColTable",
			"citibob.swing.JTypeTable",
			"citibob.swing.LogPanel",
			"citibob.gui.ConsoleFrame",
			"citibob.gui.DBPrefsDialog",
			"citibob.gui.ThreadConsoleGui"
		};
		Beans2Nbm.makeNbm(prm);
	}
}
