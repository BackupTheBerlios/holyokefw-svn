/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package citibob.swing.calendar;
import java.util.*;
import java.text.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;
import citibob.swing.typed.*;

/**
 *
 * @author  citibob
 */
public class JDayTFChooser extends javax.swing.JPanel implements CalModel.Listener
{
	
boolean inPropertyChange = false;
JFormattedTextField tfDay;

/** Creates new form JDayChooser */
public JDayTFChooser() {
	initComponents();

	// Add the year text field...
	Format format = new java.text.DecimalFormat("####");
	tfDay = new JFormattedTextField(format);
	tfDay.setValue(new Integer(0));
	add(tfDay, java.awt.BorderLayout.CENTER);	

	
//	spinner.getUp().addActionListener(new SpinnerListener(-1));
//	spinner.getDown().addActionListener(new SpinnerListener(1));
	spinner.getUp().addMouseListener(new SpinnerListener(-1));
	spinner.getDown().addMouseListener(new SpinnerListener(1));
	
	
//	JFormattedTextField.AbstractFormatterFactory factory = new javax.swing.text.DefaultFormatterFactory(format);
//	tfDay.setFormatterFactory(tfDay.getDefaultFormatterFactory(format));
	
	tfDay.addPropertyChangeListener(new PropertyChangeListener() {
	public  void  propertyChange(PropertyChangeEvent evt) {
		if ("value".equals(evt.getPropertyName())) {
			inPropertyChange = true;
			Object o = tfDay.getValue();
			//System.out.println(o.getClass());
			Number Day = (Number)o;
			int day = Day.intValue();
	//		if (day == getDay()) return;
//	System.out.println("TF day set to " + day + " (vs " + getDay() + " in model)");
			setDay(day);
		}
	}});
}
int getDay()
	{ return model.getCal().get(Calendar.DAY_OF_MONTH); }
void setDay(int m)
{
	int y = getDay();
	if (y == m) return;		// Break infinite recursion.
	model.set(Calendar.DAY_OF_MONTH, m); 

//spinner.getUp().setFocusable(true);
//spinner.getUp().requestFocus();
//System.out.println("Focusable = " + spinner.getUp().isFocusable());

}
// =====================================================
// Standard for all the JxxxChooser calendar sub-components
CalModel model;
public void setModel(CalModel m) {
	if (model != null) model.removeListener(this);
	model = m;
	model.addListener(this);
	calChanged();
}
public CalModel getModel() { return model; }
public void nullChanged() { citibob.swing.WidgetTree.setEnabled(this, !model.isNull()); }
// =====================================================

// ===================================================================
//class SpinnerListener implements ActionListener {
//	int incr;
//	public SpinnerListener(int a) { incr=a; }
//	public void  actionPerformed(ActionEvent e) {
//System.out.println("ActionPerformed on SpinnerListener: " + incr + ", " + e.getActionCommand());
//		int month = getDay();
//		setDay(month + incr);
//	}
//}

// ==================================================================
class SpinnerListener extends MouseAdapter
{
int incr;
public SpinnerListener(int a) { incr=a; }
public void  mousePressed(MouseEvent e) 
{
System.out.println("Mouse clicked!");
		int month = getDay();
		setDay(month + incr);
}}

// ===================================================================
// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void calChanged()
{
	int year = getDay();
	Number Y = (Number)tfDay.getValue();
	if (Y.intValue() == year) return;		// No change; break recursion
	tfDay.setValue(new Integer(year));
}
public void dayButtonSelected() {}
// ===================================================================
// These are called by the Integer TextField.
class DayObjModel implements ObjModel
{
	public void setValue(Object o) {
		int year = ((Integer)o).intValue();
		setDay(year);
	}
	public Object getValue() {
		return new Integer(getDay());
	}
}
// ===================================================================


/**  The "final" value has been changed. */
public void finalChanged() {}	// don't listen to it.
// ===================================================================
	
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        spinner = new citibob.swing.calendar.JSpinnerButtons();

        setLayout(new java.awt.BorderLayout());

        add(spinner, java.awt.BorderLayout.EAST);

    }
    // </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private citibob.swing.calendar.JSpinnerButtons spinner;
    // End of variables declaration//GEN-END:variables
	
}
