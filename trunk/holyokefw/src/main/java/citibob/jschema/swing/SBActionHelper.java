/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema.swing;

import citibob.app.App;
import citibob.jschema.SchemaBufDbModel;
import citibob.task.ETask;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author citibob
 */
public class SBActionHelper {

App fapp;
SchemaBufDbModel dm;
Component component;		// Enclosing AWT/Swing Component (for mouse changing)
JTable table;				// Table being used to show the model

/** Adds a blank row by default.  Override to get more complex behavior. */
public void addRow()
{
	// Default: blank row.  
	dm.getSchemaBuf().insertRow(-1);
}
// =============================================================
public void addAction()
{
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		addRow();
	}});
}

public void delAction()
{
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().deleteRow(table.getSelectedRow());
		table.requestFocus();
	}});
}                                          
public void undelAction()
{                                                 
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().undeleteRow(table.getSelectedRow());
		table.requestFocus();		// Enable easy cursor key movement
	}});
}                                            

public void delAllAction()
{                                                  
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().deleteAllRows();
	}});
}                                             

public void undelAllAction()
{                                                     
	fapp.guiRun().run(component, new ETask() {
	public void run() throws Exception {
		dm.getSchemaBuf().undeleteAllRows();
	}});
}                                                




	
}
