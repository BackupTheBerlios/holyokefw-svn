/*
JSchema: library for GUI-based database applications
This file Copyright (c) 2006-2007 by Robert Fischer

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
/*
 * AuthenticatorDialog.java
 *
 * Created on July 23, 2004, 12:08 AM
 */

package citibob.mail;

import java.util.prefs.*;

/**
 *
 * @author  citibob
 */
public class MailExpDialog extends javax.swing.JDialog {

boolean okPressed = false;

public boolean getOK() { return okPressed; }
public String getMsg() { return taMessage.getText(); }

    /** Creates new form AuthenticatorDialog */
    public MailExpDialog(java.awt.Frame parent, MailSender sender, String expText, String guiNodePath)
	{
        super(parent, true);
        initComponents();
		taMessage.setText(expText);
		jTextArea2.setBackground(jPanel3.getBackground());
		pack();
		
		// Mess with preferences
		this.setName("dialog");
		Preferences guiPrefs = Preferences.userRoot();
		guiPrefs = guiPrefs.node(guiNodePath);
		new citibob.swing.prefs.SwingPrefs().setPrefs(this, "", guiPrefs);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        bOK = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMessage = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mail Server Password Required");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTextArea2.setLineWrap(true);
        jTextArea2.setText("This program has encountered an error; the details are shown below.  Please press \"Send to Author\" in order to send this error report to the author of this software to help improve it.  If you wish, you may add details of what you were doing below.");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setFocusable(false);
        jPanel2.add(jTextArea2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        bOK.setText("Send to Author");
        bOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOKActionPerformed(evt);
            }
        });

        jPanel1.add(bOK);

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        jPanel1.add(bCancel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setViewportView(taMessage);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        okPressed=false;
        hide();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        okPressed=false;
        hide();
    }//GEN-LAST:event_formWindowClosed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        okPressed=false;
        hide();
    }//GEN-LAST:event_bCancelActionPerformed

    private void bOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOKActionPerformed
        okPressed=true;
        hide();
    }//GEN-LAST:event_bOKActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        new AuthenticatorDialog(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea taMessage;
    // End of variables declaration//GEN-END:variables
    
}
