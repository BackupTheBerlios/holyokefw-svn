/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.util.Date;

/**
 *
 * @author fiscrob
 */
public class MSSFormat implements SFormat
{
	
DateSFormat sub;

public MSSFormat(DateSFormat sub)
{
	this.sub = sub;
}

public Object stringToValue(String text) throws java.text.ParseException
{
	Date dt = (Date)sub.stringToValue(text);
	return(dt == null ? new Long(0) : new Long(dt.getTime()));
}
public String valueToString(Object value) throws java.text.ParseException
{
	Date dt = (value == null ? null : new Date(((Long)value).longValue()));
	return sub.valueToString(dt);
}

/** Should equal valueToString(null); */
public String getNullText() { return sub.getNullText(); }

}
