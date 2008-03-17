package citibob.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents dates WITHOUT timezone.
 * @author fiscrob
 */
public class Day {

public static final TimeZone gmt = TimeZone.getTimeZone("GMT");
static DateFormat dfmtGMT;
static {
	dfmtGMT = new SimpleDateFormat("yyyyMMdd");
	dfmtGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
}
	
int day;			// Days since 1/1/1970
public int getDayNum() { return day; }

public Day(int day)
{
	this.day = day;
}

public long toMS(Calendar cal)
	{ return DayConv.toMS(day, cal); }
public Date toDate(Calendar cal)
	{ return new Date(toMS(cal)); }

public Day(String sdt) throws ParseException
{
	day = DayConv.midnightToDayNum(dfmtGMT.parse(sdt).getTime(), gmt);
}

public void setInCal(Calendar cal)
	{ cal.setTimeInMillis(toMS(cal)); }

}
