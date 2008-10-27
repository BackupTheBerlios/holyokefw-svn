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
 * Wiz.java
 *
 * Created on January 27, 2007, 6:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.wizard2;

import citibob.wizard.*;
import java.util.Map;

/**
 * Represents (logically) one Wizard screen.
 * @author citibob
 */
public abstract class Wiz implements Comparable<Wiz>
{
	
public static final int NAVIGATE_BACK = 0;
public static final int NAVIGATE_FWD = 1;
	
	/** Name of this state. */
	public abstract String getStateName();
	
	/** Name of the "back" or "fwd" state.  This is just a suggestion
	 for the navigator.*/
	public abstract String getNavStateName(int nav);

	/** Do we cache the state's display when we navigate away in this way? */
	public abstract boolean isCached(int nav);
	
	/** Runs before the Wiz, even if cached Wiz is being re-used. */
	public abstract void pre(WizContext con) throws Exception;

	/** Create the physical screen to display */
	public abstract WizScreen newScreen(WizContext con) throws Exception;

	/** After the Wiz is done running but before Post is called,
	 * report its output into a Map (except for "submit", which was
	 * reported when the Wiz was originally displayed.) */
	public abstract void getAllValues(Map<String,Object> con);
	
	/** Runs after the Wiz.  Returns the name of the String we
	 want to navigate to (only for overrides; usually just return
	 null and let the Navigator figure out where we're going, based
	 on "submit". */
	public abstract String post(WizContext con) throws Exception;

	
	public int compareTo(Wiz b)
	{
		return this.getStateName().compareTo(b.getStateName());
	}
}
