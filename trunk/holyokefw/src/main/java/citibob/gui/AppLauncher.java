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
package citibob.gui;


import citibob.text.ClassSFormat;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;

/*
 * AppLauncher.java
 *
 * Created on January 21, 2008, 8:05 PM
 */
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 
 @author  fiscrob
 */
public class AppLauncher extends javax.swing.JFrame {

Preferences prefs;

	/** Creates new form AppLauncher */
	public AppLauncher(String projectName, Class[] classes) {
		initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DefaultListModel mod = new DefaultListModel();
		for (int i=0; i<classes.length; ++i) mod.addElement(classes[i]);
		lclasses.setModel(mod);
		
		lclasses.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) return;
			setVisible(false);
			Class klass = (Class)lclasses.getModel().getElementAt(event.getFirstIndex());
			launchClass(klass);
		}});

		prefs = Preferences.userRoot().node("AppLauncher").node(projectName);
//			userNodeForPackage(getClass()).node("AppLauncher");
		this.setSize(new Dimension(400, 600));
		new citibob.swing.prefs.SwingPrefs().setPrefs(this, "", prefs);
		
		// Populate our default class from last time...
		lDefaultClass.setJType(Class.class, new ClassSFormat());
		try {
			lDefaultClass.setValue(Class.forName(prefs.get("defaultClass", "")));
		} catch(Exception e) {}
		
		bLaunchDefault.requestFocus();
	}

	void launchClass(Class klass)
	{
		prefs.put("defaultClass", klass.getName());
		try {
			System.out.println(klass);
			System.err.println(klass);
			Method[] meths = klass.getMethods();
			for (int i=0; ; ++i) {
				if (i == meths.length) throw new Exception(
					"No main() method found in " + klass);
				if ("main".equals(meths[i].getName())) {
					setVisible(false);
					meths[i].invoke((Object)null, new String[]{null});
					break;
				}
			}
		} catch(Exception e) {
			Throwable cause = e.getCause();
			//e.printStackTrace(System.out);
			cause.printStackTrace(System.err);
		}
		System.out.println("====== Done running " + klass);
		System.exit(-1);
	}

	
	/** This method is called from within the constructor to
	 initialize the form.
	 WARNING: Do NOT modify this code. The content of this method is
	 always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        bLaunchDefault = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lDefaultClass = new citibob.swing.typed.JTypedLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lclasses = new javax.swing.JList();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        bLaunchDefault.setText("Launch");
        bLaunchDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLaunchDefaultActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(bLaunchDefault, gridBagConstraints);

        jLabel1.setText("Default Class: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel1, gridBagConstraints);

        lDefaultClass.setText("jTypedLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(lDefaultClass, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        lclasses.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lclasses);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

	private void bLaunchDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLaunchDefaultActionPerformed
		launchClass((Class)lDefaultClass.getValue());
		// TODO add your handling code here:
}//GEN-LAST:event_bLaunchDefaultActionPerformed
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bLaunchDefault;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private citibob.swing.typed.JTypedLabel lDefaultClass;
    private javax.swing.JList lclasses;
    // End of variables declaration//GEN-END:variables
	
	
public static void launch(String projectName, Class[] classes) {
	AppLauncher launcher = new AppLauncher(projectName, classes); //new Class[]{String.class, Integer.class});
	launcher.setVisible(true);
}
}
