/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.wizard2;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author citibob
 */
public class SwingWizard extends Wizard
{

Frame owner;	// The frame that called this (modal) Wizard

public SwingWizard(Frame owner)
{
	super();
	this.owner = owner;
}

 /* Creates a new instance of HtmlDialog 
public HtmlDialog(Frame owner, String title, boolean modal)
{
	super(owner, title, modal);
//	super(title);
	html = new ObjHtmlPanel();
	getContentPane().add(html);
//	this.setSize(600, 400);
	html.addListener(this);
	
    // Add a listener for the close event
    addWindowListener(new java.awt.event.WindowAdapter() {
	public void windowClosing(WindowEvent evt) {
		submitName = "cancel";
		submitButton = null;
		setVisible(false);
    }});
}
*/

@Override
protected void showScreen(WizScreen screen, final Map<String,Object> values) throws Exception
{
	JPanel panel = (JPanel)screen;
	final JDialog dialog = new JDialog();
	dialog.getContentPane().add(panel);
	dialog.setSize(panel.getPreferredSize());
	dialog.setVisible(true);

    // Add a listener for the window close event
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
	public void windowClosing(WindowEvent evt) {
		values.put("submit", "cancel");
		dialog.setVisible(false);
    }});
	
	// Add a listener for the WizScreen deciding it's closing
	panel.addPropertyChangeListener("submit", new PropertyChangeListener() {
	public void propertyChange(PropertyChangeEvent evt) {
		values.put("submit", evt.getNewValue());
		dialog.setVisible(false);
	}});

}





}
