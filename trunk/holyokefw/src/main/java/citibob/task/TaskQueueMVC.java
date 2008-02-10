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
package citibob.task;

import java.util.*;
import java.io.*;

public abstract class TaskQueueMVC extends Thread implements TaskRunner
{
public static interface Listener {
    /**  Task added to queue. */
    public void taskAdded(Task t);


    /**  Task removed from the queue */
    public void taskRemoved(Task t);


    /**  Task being executed */
    public void taskStarting(Task t);


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(Task t, Throwable e);


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared();
}
// ======================================================
public static class Adapter implements TaskQueueMVC.Listener {
    /**  Task added to queue. */
    public void taskAdded(Task t) {}


    /**  Task removed from the queue */
    public void taskRemoved(Task t) {}


    /**  Task being executed */
    public void taskStarting(Task t) {}


    /**  Task finished running --- exception (if any) is passed along */
    public void taskFinished(Task t, Throwable e) {}


    /**  Queue cleared (usually, the running task will be finished here as well.) */
    public void queueCleared() {}
}
// ======================================================
java.util.LinkedList listeners = new java.util.LinkedList();
public void addListener(TaskQueueMVC.Listener l)
	{ listeners.add(l); }
public void removeListener(TaskQueueMVC.Listener l)
	{ listeners.remove(l); }

// ======================================================
public void fireTaskAdded(Task t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskAdded(t);
	}
}
public void fireTaskRemoved(Task t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskRemoved(t);
	}
}
public void fireTaskStarting(Task t)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskStarting(t);
	}
}
public void fireTaskFinished(Task t, Throwable e)
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.taskFinished(t, e);
	}
}
public void fireQueueCleared()
{
	for (java.util.Iterator ii=listeners.iterator(); ii.hasNext(); ) {
		TaskQueueMVC.Listener l = (TaskQueueMVC.Listener)ii.next();
		l.queueCleared();
	}
}
}
