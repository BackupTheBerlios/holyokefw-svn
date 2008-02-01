/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

/**
 *
 * @author fiscrob
 */
public class ClassSFormat extends AbstractSFormat
{
	public String valueToString(Object value) throws java.text.ParseException
		{ return ((Class)value).getSimpleName(); }
}
