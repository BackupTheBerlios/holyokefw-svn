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
public class JDateFieldChooser extends javax.swing.JPanel implements CalModel.Listener
{
	
boolean inPropertyChange = false;
JFormattedTextField tfYear;
int dateField;			// eg: Calendar.YEAR, Calendar.HOUR, etc.

/** Creates new form JYearChooser */
public JDateFieldChooser()
{
	initComponents();
}
public void initRuntime(int dateField, String sformat)
{
	this.dateField = dateField;

	
	// Add the year text field...
	Format format = new java.text.DecimalFormat(sformat);
	tfYear = new JFormattedTextField(format);
	tfYear.setValue(new Integer(0));
	add(tfYear, java.awt.BorderLayout.CENTER);	

	
	spinner.getUp().addMouseListener(new SpinnerListener(-1));
	spinner.getDown().addMouseListener(new SpinnerListener(1));
	
	
//	JFormattedTextField.AbstractFormatterFactory factory = new javax.swing.text.DefaultFormatterFactory(format);
//	tfYear.setFormatterFactory(tfYear.getDefaultFormatterFactory(format));
	
	tfYear.addPropertyChangeListener(new PropertyChangeListener() {
	public  void  propertyChange(PropertyChangeEvent evt) {
		if ("value".equals(evt.getPropertyName())) {
			inPropertyChange = true;
			Object o = tfYear.getValue();
			System.out.println(o.getClass());
			Number Y = (Number)o;
			setYear(Y.intValue());
		}
	}});
}
int getYear()
	{ return model.getCal().get(dateField); }
void setYear(int m)
{
	int y = getYear();
	if (y == m) return;		// Break infinite recursion.
	model.set(dateField, m); 

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
// =====================================================

// ===================================================================
class SpinnerListener extends MouseAdapter
{
int incr;
public SpinnerListener(int a) { incr=a; }
public void  mousePressed(MouseEvent e) 
{
		int month = getYear();
		setYear(month + incr);
}}
// ===================================================================
// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void calChanged()
{
	int year = getYear();
	Number Y = (Number)tfYear.getValue();
	if (Y.intValue() == year) return;		// No change; break recursion
	tfYear.setValue(new Integer(year));
}
public void dayButtonSelected() {}
public void nullChanged() { citibob.swing.WidgetTree.setEnabled(this, !model.isNull()); }
// ===================================================================
// These are called by the Integer TextField.
class YearObjModel implements ObjModel
{
	public void setValue(Object o) {
		int year = ((Integer)o).intValue();
		setYear(year);
	}
	public Object getValue() {
		return new Integer(getYear());
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
