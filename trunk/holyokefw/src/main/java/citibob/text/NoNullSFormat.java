/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

/**
 * Ensures that Null will never be returned as stringToValue()
 * @author fiscrob
 */
public class NoNullSFormat implements SFormat
{

SFormat fmt;
Object nullValue;	// We replace nulls with this

public NoNullSFormat(SFormat fmt, Object nullValue)
{
	this.fmt = fmt;
	this.nullValue = nullValue;
}

public Object stringToValue(String text) throws java.text.ParseException
{
	Object obj = fmt.stringToValue(text);
	if (obj == null) return nullValue;
	return obj;
}
public String valueToString(Object value) throws java.text.ParseException
	{ return fmt.valueToString(value); }

/** Should equal valueToString(null); */
public String getNullText() { return fmt.getNullText(); }

}
