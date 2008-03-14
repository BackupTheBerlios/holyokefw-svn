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
public class JobMap extends HashMap<String,Job>
{

//HashMap<String,Task> map = new HashMap();
//TaskRunner runner;
//
//public TaskMap() {}
//public TaskMap(TaskRunner runner)
//	{ this.runner = runner; }
//public void run(String key)
//	{ runner.doRun(get(key)); }

public void add(String key, Job task)
{ this.put(key, task); }
	
public void add(String key,
String[] permissions, CBTask runnable)
{
	this.put(key, new Job(permissions, runnable));
}
public void add(String key, CBTask runnable)
{
	this.put(key, new Job((String[])null, runnable));
}	

/** @param permissions Comma-separated list of permissions */
public void add(String key, String permissions, CBTask runnable)
{
	this.put(key, new Job(permissions, runnable));
}

//public Task get(String key) { return this.get(key); }

}
