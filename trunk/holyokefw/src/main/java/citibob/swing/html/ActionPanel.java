/*
OffstageArts: Enterprise Database for Arts Organizations
This file Copyright (c) 2005-2007 by Robert Fischer

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

package citibob.swing.html;

import citibob.app.App;
import java.util.*;
import citibob.task.*;
import citibob.swing.*;
import javax.swing.*;
import citibob.sql.*;

/**
 *
 * @author citibob
 */
public class ActionPanel
extends ObjHtmlPanel
implements ObjHtmlPanel.Listener
{

protected App app;
HashMap<String,Task> actionMap = new HashMap();



public void addAction(String url,
String name, String[] permissions, CBRunnable runnable)
{
	actionMap.put(url, new Task(permissions, runnable));
}
public void addAction(String url, String name, CBRunnable runnable)
{
	actionMap.put(url, new Task((String[])null, runnable));
}	

/** @param permissions Comma-separated list of permissions */
public void addAction(String url, String name, String permissions, CBRunnable runnable)
{
	actionMap.put(url, new Task(permissions, runnable));
}


/** Creates a new instance of ActionPanel */
public void initRuntime(App xapp)
throws org.xml.sax.SAXException, java.io.IOException
{
	this.app = xapp;

	addListener(this);
	loadHtml();
}

// ===================================================
// ObjHtmlPanel.Listener
public void linkSelected(java.net.URL href, String target)
{
	String url = href.toExternalForm();
	int slash = url.lastIndexOf('/');
	if (slash > 0) url = url.substring(slash+1);
	
	Task t = actionMap.get(url);
	if (t != null) app.runGui(this, t.getPermissions(), t.getCBRunnable());
}
}
