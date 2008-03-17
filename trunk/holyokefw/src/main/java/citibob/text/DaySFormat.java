/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import citibob.util.Day;
import citibob.util.DayConv;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Computes days since 1970
 * @author fiscrob
 */
public class DaySFormat implements SFormat
{
String nullText;
DateFormat dfmt;

public DaySFormat(String fmtString)
	{ this(fmtString, ""); }
public DaySFormat(String fmtString, String nullText)
{
	this.nullText = nullText;
	dfmt = new SimpleDateFormat(fmtString);
	dfmt.setTimeZone(Day.gmt);
}

public Object stringToValue(String text) throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	
	long ms = dfmt.parse(text).getTime();
	int dayNum = DayConv.midnightToDayNum(ms, dfmt.getTimeZone());
	return new Day(dayNum);
}
public String valueToString(Object value) //throws java.text.ParseException
{
	if (value == null) return nullText;

	Day day = (Day)value;
	long ms = day.toMS(dfmt.getCalendar());
	return dfmt.format(new Date(ms));
}

/** Should equal valueToString(null); */
public String getNullText() { return nullText; }

}
