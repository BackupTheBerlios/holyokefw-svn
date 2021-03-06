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
package citibob.task;

public interface ExpHandler {
	void consume(Throwable e);


public Throwable getRootCause(Throwable t);

public String getNestedMessages(Throwable t);

/** Sees if this exception or any of its causes is of a certain class.
 * If so, returns the first one it finds.
 * @param t
 * @return
 */
public Throwable findCauseByClass(Throwable t, Class klass);

}
