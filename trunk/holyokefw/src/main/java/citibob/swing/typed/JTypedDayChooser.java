/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.swing.typed;

import citibob.types.JDate;
import citibob.swing.calendar.JCalendarDateOnly;
import citibob.text.DateSFormat;
import citibob.types.JType;
import citibob.util.Day;
import citibob.util.DayConv;

/**
 *
 * @author fiscrob
 */
public class JTypedDayChooser extends JTypedDateChooser
{

//JDateType jType;
JType dayType;
DateSFormat sformat;

public void setJType(JType dayType,
DateSFormat sformat)
{
	this.dayType = dayType;
	this.sformat = sformat;
	super.setJType(new JDate(dayType.isInstance(null)), sformat,
		new JCalendarDateOnly());
}

// TypedWidget
/** Returns last legal value of the widget.  Same as method in JFormattedTextField */
public Object getValue()
{
	return new Day(DayConv.midnightToDayNum(
		super.getValueInMillis(), sformat.getDisplayTZ()));
}

/** Sets the value.  Same as method in JFormattedTextField.  Fires a
 * propertyChangeEvent("value") when calling setValue() changes the value. */
public void setValue(Object o)
{
	Day day = (Day)o;
	super.setValueInMillis(DayConv.toMS(
		day.getDayNum(), sformat.getCalendar()));
}

/** Is this object an instance of the class available for this widget?
 * If so, then setValue() will work.  See SqlType.. */
public boolean isInstance(Object o) { return dayType.isInstance(o); }


}
