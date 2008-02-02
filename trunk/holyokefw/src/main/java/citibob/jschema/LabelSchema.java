/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.jschema;

/**
 * Sets up a schema ready to match against labels on (for example)
 * a CSV file.
 * @author fiscrob
 */
public class LabelSchema extends ConstSchema
{

/** Format: namesLabels[i] = name, namesLabels[i+1] = label */
public LabelSchema(String[] namesLabels)
{
	super.cols = new Column[namesLabels.length / 2];
	int j = 0;
	for (int i=0; i < namesLabels.length; i += 2) {
		String name = namesLabels[i];
		String label = namesLabels[i+1];
		cols[j++] = new Column(null, name, label);
	}
}
}
