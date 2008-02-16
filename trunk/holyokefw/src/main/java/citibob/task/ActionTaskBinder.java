/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.task;

import citibob.task.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

/**
 *
 * @author citibob
 */
public class ActionTaskBinder
{
TaskMap map;
SwingTaskRunner runner;
Component component;			// Parent component


public ActionTaskBinder(Component component, SwingTaskRunner runner, TaskMap map)
{
	this.component = component;
	this.runner = runner;
	this.map = map;
}

/** Binds a Swing component to a previously registered runnable. */
public ActionListener newListener(final Task task)
{
//	final Task task = map.get(key);
	ActionListener listener = new ActionListener() {
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		runner.doRun(component, task);
	}};
	return listener;
}

/** Convenience Function */
public void bind(AbstractButton button, String key)
{
	button.addActionListener(newListener(map.get(key)));
}

//public void bind(AbstractButton button, Task task)
//{
//	button.addActionListener(newListener(task));
//}


}
