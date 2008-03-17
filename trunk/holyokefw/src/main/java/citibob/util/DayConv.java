/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Convert between milliseconds, java.util.Date and days since 1/1/1970 (in your preferred timezone)
 * @author fiscrob
 */
public class DayConv {
//Calendar cal;
//DateFormat dfmt;
//
//// Used to speed up conversion
//long lastDayStartMS;		// Start of day for last toDay()
//long lastNextDayMS;
//int lastDay;			// Result of last toDay()
//
//public DayConv(TimeZone tz)
//{
//	// Compute base day in this time zone
//	cal = Calendar.getInstance(tz);
//	cal.clear();
//	cal.set(1970,0,1);
//	ms1970 = cal.getTimeInMillis();
//	dfmt = new SimpleDateFormat("yyyyMMdd");
//	dfmt.setTimeZone(tz);
//}

// ---------------------------------------------------------------
/** @param midnightMS This must already be truncated to midnight in the timezone.
 */
public static int midnightToDayNum(long midnightMS, TimeZone tz)
{
	return Math.round((midnightMS - ms1970(tz)) / (86400*1000));
}
/** @param ms This must already be truncated to midnight in Market's time zone.
@param cal Calendar object to use (with timezone set)
 */
public static long toMS(int dayNum, Calendar cal)
{
	cal.setTimeInMillis(ms1970(cal.getTimeZone()));
	cal.add(Calendar.DATE, dayNum);
	return cal.getTimeInMillis();
}

public static int ms1970(TimeZone tz)
{
	return -tz.getOffset(0L);
}
//// ---------------------------------------------------------------
//public int toDayNum(long ms, Calendar cal, long ms1970)
//{
//	// Re-use last day's calculation if we're still on same day
//	if (ms >= lastDayStartMS && ms < lastNextDayMS) return lastDay;
//
//	// Re-figure the day
//	cal.setTimeInMillis(ms);
//	cal.set(Calendar.HOUR_OF_DAY, 0);
//	cal.set(Calendar.MINUTE, 0);
//	cal.set(Calendar.SECOND, 0);
//	cal.set(Calendar.MILLISECOND, 0);
//	lastDayStartMS = cal.getTimeInMillis();
//	cal.add(Calendar.DATE, 1);
//	lastNextDayMS = cal.getTimeInMillis();
//	lastDay = Math.round((lastDayStartMS - ms1970) / (86400*1000));
//	return lastDay;
//}
// 
///** @param dt This must already be truncated to midnight in Market's time zone. */
//public int toDayNum(java.util.Date dt)
//{
//	return toDayNum(dt.getTime());
//}
//public Date toDate(int day)
//{
//	cal.setTimeInMillis(ms1970);
//	cal.add(Calendar.DATE, day);
//	return cal.getTime();
//}

}
