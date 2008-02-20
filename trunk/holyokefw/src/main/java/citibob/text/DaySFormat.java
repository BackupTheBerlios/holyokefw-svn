/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.util.Date;
import java.util.TimeZone;

/**
 * Computes days since 1970
 * @author fiscrob
 */
public class DaySFormat implements SFormat
{
	
DateSFormat sub;
DayConv dconv;

public DaySFormat(DateSFormat sub)
{
	this.sub = sub;
	dconv = new DayConv(sub.getDisplayTZ());
}

public DaySFormat(String fmtString, String nullText)
{
	this(new DateSFormat(fmtString, nullText,
			TimeZone.getTimeZone("GMT")));
}


public Object stringToValue(String text) throws java.text.ParseException
{
	Date dt = (Date)sub.stringToValue(text);
	if (dt == null) return null;
	return new Integer(dconv.toDay(dt));
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return sub.valueToString(null);
	Integer Day = (Integer)value;
	Date dt = dconv.toDate(Day.intValue());
	return sub.valueToString(dt);
}

/** Should equal valueToString(null); */
public String getNullText() { return sub.getNullText(); }

}
