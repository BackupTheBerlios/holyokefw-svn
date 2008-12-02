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
/*
 * AuthenticatorDialog.java
 *
 * Created on July 23, 2004, 12:08 AM
 */

package citibob.mail;

import citibob.swing.prefs.SwingPrefs;
import citibob.task.AppError;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.prefs.*;

/**
 *
 * @author  citibob
 */
public class MailExpDialog extends javax.swing.JDialog {

boolean reportErrorPressed = false;

public boolean isReportError() { return reportErrorPressed; }
public String getMsg() { return taMessage.getText(); }

    /** Creates new form AuthenticatorDialog */
    public MailExpDialog(java.awt.Frame parent, String progName,
	Throwable exp, String expText, boolean askUser,
	SwingPrefs swingPrefs, Preferences prefNode)
	{
        super(parent, true);
        initComponents();

		if (!askUser) this.bReportError.setVisible(false);
		
		summary.setBackground(jPanel4.getBackground());
		taMessage.setText(expText);

		if (exp instanceof AppError) {
			summary.setText(
				"<p>" + exp.getMessage() + "</p>");
		} else {
			summary.setText(
				"<p>" + progName + " has encountered an error.  " +
				(askUser ? "Please press \"Report Error\"" +
				" below to help improve this application." :
				"Details will be reported, to help improve this application.") +
				"</p><br>" +
				"<b>" + exp.getClass().getSimpleName() + "</b>: " + exp.getMessage() +
				"<br>");
		}
		pack();
		
		// Mess with preferences
		this.setName("dialog");
		if (prefNode != null) swingPrefs.setPrefs(this, prefNode);
//		if (prefNode != null) new citibob.swing.prefs.SwingPrefs().setPrefs(this, prefNode);
    }

	/** Really primitive version, for before we're fully initialized. */
    public MailExpDialog(java.awt.Frame parent, String progName, Throwable exp)
	{
        super(parent, true);
        initComponents();
		summary.setBackground(jPanel4.getBackground());

		bReportError.setVisible(false);
		
		StringWriter ss = new StringWriter();
		PrintWriter pw = new PrintWriter(ss);
		exp.printStackTrace(pw);
		String expText = 
			"User: " + System.getProperty("user.name") + "\n" +
			ss.getBuffer().toString() + "\n";
//		System.out.println(ss.toString());
		taMessage.setText(expText);

		
		
		
		
		summary.setText(
			"<p>" + progName + " has encountered a fatal error.  Please notify your" +
			" System Administrator.  You may wish to cut-and-paste the error text" +
			" from the \"Details\" tab.</p>" +
			"<br>" +
			"<b>" + exp.getClass().getSimpleName() + "</b>: " + exp.getMessage() +
			"<br>");
		pack();
    }
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        bExit = new javax.swing.JButton();
        bReportError = new javax.swing.JButton();
        bOK = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        summary = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        taMessage = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Error");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosed(java.awt.event.WindowEvent evt)
            {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        bExit.setText("Exit Application");
        bExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bExitActionPerformed(evt);
            }
        });
        jPanel1.add(bExit);

        bReportError.setText("Report Error");
        bReportError.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bReportErrorActionPerformed(evt);
            }
        });
        jPanel1.add(bReportError);

        bOK.setText("OK");
        bOK.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bOKActionPerformed(evt);
            }
        });
        jPanel1.add(bOK);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(400, 400));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        summary.setContentType("text/html");
        jScrollPane2.setViewportView(summary);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Summary", jPanel4);

        jScrollPane1.setViewportView(taMessage);

        jTabbedPane1.addTab("Details", jScrollPane1);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        reportErrorPressed=false;
        hide();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        reportErrorPressed=false;
        hide();
    }//GEN-LAST:event_formWindowClosed

	private void bOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bOKActionPerformed
	{//GEN-HEADEREND:event_bOKActionPerformed
        reportErrorPressed=false;
        hide();
}//GEN-LAST:event_bOKActionPerformed

	private void bReportErrorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bReportErrorActionPerformed
	{//GEN-HEADEREND:event_bReportErrorActionPerformed
        reportErrorPressed=true;
        hide();
}//GEN-LAST:event_bReportErrorActionPerformed

	private void bExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bExitActionPerformed
	{//GEN-HEADEREND:event_bExitActionPerformed
		System.exit(-1);
		// TODO add your handling code here:
}//GEN-LAST:event_bExitActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        new AuthenticatorDialog(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bOK;
    private javax.swing.JButton bReportError;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane summary;
    private javax.swing.JTextArea taMessage;
    // End of variables declaration//GEN-END:variables
    
}
