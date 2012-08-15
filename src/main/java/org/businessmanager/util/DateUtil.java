package org.businessmanager.util;

import java.util.Calendar;
import java.util.Date;

/**
 * A utility class to convert between {@link Calendar} and {@link Date}.
 * 
 * @author Christian Ternes
 *
 */
public final class DateUtil {

	private DateUtil() {
	}
	
	public static Calendar convertDateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		if(date != null) {
			cal.setTime(date);
		}
		
		return cal;
	} 
	
	public static Date convertCalendarToDate(Calendar calendar) {
		if(calendar == null) {
			return null;
		}
		return calendar.getTime();
	}
}
