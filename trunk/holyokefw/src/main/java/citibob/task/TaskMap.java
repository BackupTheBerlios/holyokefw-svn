/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.task;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author citibob
 */
public class TaskMap extends HashMap<String,Task>
{

//HashMap<String,Task> map = new HashMap();
//TaskRunner runner;
//
//public TaskMap() {}
//public TaskMap(TaskRunner runner)
//	{ this.runner = runner; }
//public void run(String key)
//	{ runner.doRun(get(key)); }

public void add(String key, Task task)
{ this.put(key, task); }
	
public void add(String key,
String[] permissions, CBRunnable runnable)
{
	this.put(key, new Task(permissions, runnable));
}
public void add(String key, CBRunnable runnable)
{
	this.put(key, new Task((String[])null, runnable));
}	

/** @param permissions Comma-separated list of permissions */
public void add(String key, String permissions, CBRunnable runnable)
{
	this.put(key, new Task(permissions, runnable));
}

//public Task get(String key) { return this.get(key); }

}
