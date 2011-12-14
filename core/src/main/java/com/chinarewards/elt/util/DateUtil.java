/**
 * 
 */
package com.chinarewards.elt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstraction of DateTimeProvider. User need to implement the
 * {@link #getTime()} method.
 * 
 * @author cyril
 * @since 0.2.0
 */
public class DateUtil {

	public static Date getTime() {
		return new Date();
	}

	public static Date getLastTimeOfThisDay(Date date) {
		Date para = null;
		para = date == null ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(para);
		// calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	public static Date getEarlierTimeOfThisDay(Date date) {
		Date para = null;
		para = date == null ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(para);
		// calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得参数时间后的parameter天的最早时间
	 * 
	 * like : Fri Sep 04 00:00:00 CST 2009
	 * 
	 * @param date
	 * @param parameter
	 * @return
	 */
	public static Date getDateOfParameterOnDay(Date date, int parameter) {
		Date para = null;
		para = date == null ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(para);
		// calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + parameter);
		return calendar.getTime();
	}

	public static Date getDateOfParameterOnYear(Date date, int parameter) {
		Date para = null;
		para = date == null ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(para);
		// calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + parameter);
		return calendar.getTime();
	}

	public static Date getDateOfParameterOnMonth(Date date, int parameter) {
		Date para = null;
		para = date == null ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(para);
		// calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) + parameter);
		return calendar.getTime();
	}

	public static String formatData(String partten, Date date) {
		if (StringUtil.isEmptyString(partten)) {
			partten = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(partten);
		return sdf.format(date);
	}

	public static int betweenDays(Date begin, Date end) {
		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(begin);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(end);
		if (beginDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) {
			return endDate.get(Calendar.DAY_OF_YEAR)
					- beginDate.get(Calendar.DAY_OF_YEAR);
		} else {
			if (beginDate.getTimeInMillis() < endDate.getTimeInMillis()) {
				int days = beginDate.getActualMaximum(Calendar.DAY_OF_YEAR)
						- beginDate.get(Calendar.DAY_OF_YEAR)
						+ endDate.get(Calendar.DAY_OF_YEAR);
				for (int i = beginDate.get(Calendar.YEAR) + 1; i < endDate
						.get(Calendar.YEAR); i++) {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, i);
					days += c.getActualMaximum(Calendar.DAY_OF_YEAR);
				}
				return days;
			} else {
				int days = endDate.getActualMaximum(Calendar.DAY_OF_YEAR)
						- endDate.get(Calendar.DAY_OF_YEAR)
						+ beginDate.get(Calendar.DAY_OF_YEAR);
				for (int i = endDate.get(Calendar.YEAR) + 1; i < beginDate
						.get(Calendar.YEAR); i++) {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, i);
					days += c.getActualMaximum(Calendar.DAY_OF_YEAR);
				}
				return days;
			}
		}
	}

}
