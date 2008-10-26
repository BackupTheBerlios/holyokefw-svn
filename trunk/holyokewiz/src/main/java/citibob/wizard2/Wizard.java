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
 * Wizard.java
 *
 * Created on January 27, 2007, 6:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard2;


import java.util.*;
import java.sql.*;
import citibob.sql.*;
import citibob.app.*;

/**
 *
 * @author citibob
 */
public abstract class Wizard
{
	
/** Wiz screens are cached through course of a run...
This allows us to go back to previous screens without having to re-load
their data. */
protected Map<Wiz,WizScreen> screenCache;

//protected Wiz wiz;
protected WizContext con;		// Values, etc.

//protected String wizardName;
protected Navigator nav;


/** Presents one Wiz to the user */
protected abstract void showScreen(WizScreen wiz, Map<String,Object> values) throws Exception;

/** Ask the user if really wants to cancel the Wizard */
protected boolean reallyCancel() throws Exception { return true; }


/** Runs before the Wiz, even if cached Wiz is being re-used. */
public void preWiz(Wiz wiz, WizContext con) throws Exception
{
	wiz.pre(con);
}

/** Runs after the Wiz.  This should flush any Sql.  It
 returns the name of the state we should navigate to (if override).
 */
public String postWiz(Wiz wiz, WizContext con) throws Exception
{
	return wiz.post(con);
	// str.flush();			// Do this in a subclass of Wizard
}

/** Returns true if wizard was not cancelled */
public boolean runWizard() throws Exception
{
	// Tells us what came before each state
	Map<Wiz,Wiz> prevWizs = new TreeMap();
	// v = (xv == null ? new TypedHashMap() : xv);
	screenCache = new TreeMap();
	con = new WizContext();
	Wiz wiz = nav.getStart();		// Current state
	for (; wiz != null; ) {

		// Get the screen for this Wiz
		WizScreen screen = screenCache.get(wiz);
		if (screen == null) {
			screen = wiz.newScreen(con);
			if (wiz.isCached(Wiz.NAVIGATE_BACK)) screenCache.put(wiz,screen);
		}

		// Prepare and show the Wiz
		preWiz(wiz, con);
		Map<String,Object> values = new TreeMap();
		showScreen(screen, values);

		// Run the post-processing
		wiz.getAllValues(values);
		con.localValues = values;
		String suggestedNextName = postWiz(wiz, con);

		// Figure out where we're going next
		Wiz nextWiz = null;
		String submit = (String)values.get("submit");
System.out.println("submit = " + submit);
		if ("next".equals(submit)) {
			if (suggestedNextName != null) {
				nextWiz = nav.getWiz(suggestedNextName);
			} else {
				nextWiz = nav.getNext(wiz, Wiz.NAVIGATE_FWD);
			}
			con.addValues(values);
		} else if ("back".equals(submit)) {
			// Remove it from the cache so we re-make
			// it going "forward" in the Wizard
			if (!wiz.isCached(Wiz.NAVIGATE_FWD)) screenCache.remove(wiz);

			// Nav will usually NOT set a NAVIGATE_BACK...
			nextWiz = nav.getNext(wiz, Wiz.NAVIGATE_BACK);

			// ... which leaves us free to do it from our history
			if (nextWiz == null) nextWiz = prevWizs.get(wiz);

			// Falling off the beginning of our history...
			if (nextWiz == null && reallyCancel()) return false;
			continue;
		} else if ("cancel".equals(submit) && reallyCancel()) {
			return false;
		} else {
			// Navigation without a "submit"; rare but possible...
			if (suggestedNextName != null) {
				nextWiz = nav.getWiz(suggestedNextName);
			}
			// Custom navigation!
			// Incorporate the values into the main WizContext
			con.addValues(values);
		}

		// ================= Finish up
		wiz.post(con);
		wiz = nextWiz;
	}
	return true;		// Won't get here
}


static boolean checkFieldsFilledIn(WizContext con)
{
	boolean hasNull = false;
	for (Object o : con.localValues.values()) {
		if (o == null) {
			hasNull = true;
			break;
		}
	}
	return !hasNull;
}

}
