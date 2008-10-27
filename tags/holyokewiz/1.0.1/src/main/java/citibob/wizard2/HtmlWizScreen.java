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
 * HtmlWiz.java
 *
 * Created on October 8, 2006, 5:25 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package citibob.wizard2;

import citibob.swing.html.*;
import citibob.wizard.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import citibob.swing.typed.*;
import java.awt.event.*;
import java.net.URL;
import org.xml.sax.SAXException;

/**
 *
 * @author citibob
 */
public class HtmlWizScreen extends ObjHtmlPanel
implements ObjHtmlPanel.Listener, WizScreen
{

//WizContext con;  Will be used by constructors to set up HTML, etc. but not stored.


// ---------------------------------------------------------
/** This is how we tell the Wizard framework we've submitted something
 * and it's time to move on.
 * @param name
 */
protected void doSubmit(String name)
{
	firePropertyChange("submit", null, name);	
}
// ---------------------------------------------------------
protected JButton setSubmit(final String name, final JButton button)
{
	if (button == null) return null;
	
	button.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		doSubmit(name);
	}});
	return button;
}
public JButton addSubmitButton(String name, String text)
{
	return setSubmit(name, addJButton(name, text));
}
// ---------------------------------------------------------
public void linkSelected(URL href, String target) {
	String url = href.toExternalForm();
	int slash = url.lastIndexOf('/');
	if (slash > 0) url = url.substring(slash+1);

	doSubmit(url);
}
// ---------------------------------------------------------
protected void init(String html)
throws SAXException, IOException
{
	super.loadHtml(new StringReader(html));


	this.setPreferredSize(new Dimension(600, 400));

	// Standard Wizard buttons
	addSubmitButton("back", "<< Back");
	addSubmitButton("next", ">> Next");
	addSubmitButton("cancel", "Cancel");
}
	
}
