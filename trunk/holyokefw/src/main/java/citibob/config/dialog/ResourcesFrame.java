/*
OffstageArts: Enterprise Database for Arts Organizations
This file Copyright (c) 2005-2008 by Robert Fischer

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
 * ResourcesFrame.java
 *
 * Created on February 24, 2008, 4:35 PM
 */

package citibob.config.dialog;

import citibob.app.App;
import citibob.sql.SqlRun;

/**
 
 @author  citibob
 */
public class ResourcesFrame extends javax.swing.JFrame
{
	
	/** Creates new form ResourcesFrame */
	public ResourcesFrame()
	{
		initComponents();
	}
	public void initRuntime(SqlRun str, App app)
	{
		resources.initRuntime(str, app);
	}
	/** This method is called from within the constructor to
	 initialize the form.
	 WARNING: Do NOT modify this code. The content of this method is
	 always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        resources = new citibob.config.dialog.ResourcePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(resources, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    private citibob.config.dialog.ResourcePanel resources;
    // End of variables declaration//GEN-END:variables
	
}
