package com.github.commons.utils.time;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * <pre>
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public class TimeUtils {
	private static String[] TIMESTAMP_PATTERN = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm" };

	/**
	 *
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minuteOfHour
	 * @param secondOfMinute
	 * @param millisOfSecond
	 * @return
	 */
	public static Date createDate(int year,
			int monthOfYear,
			int dayOfMonth,
			int hourOfDay,
			int minuteOfHour,
			int secondOfMinute,
			int millisOfSecond) {
		DateTime date = new DateTime(year,
					monthOfYear,
					dayOfMonth,
					hourOfDay,
					minuteOfHour,
					secondOfMinute,
					millisOfSecond);
		return date.toDate();
	}

	/**
	 *
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static Date createDate(int year,
			int monthOfYear,
			int dayOfMonth) {
		return createDate(year, monthOfYear, dayOfMonth, 0, 0, 0, 0);
	}

	/**
	 *
	 * @param date
	 * @param days
	 */
	public static Date pulsDays(Date date, int days) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusDays(days);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param years
	 */
	public static Date pulsYears(Date date, int years) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusYears(years);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param weeks
	 */
	public static Date pulsWeeks(Date date, int weeks) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusWeeks(weeks);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param seconds
	 */
	public static Date pulsSeconds(Date date, int seconds) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusSeconds(seconds);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param months
	 */
	public static Date pulsMonths(Date date, int months) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusMonths(months);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param minutes
	 */
	public static Date pulsMinutes(Date date, int minutes) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusMinutes(minutes);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param millis
	 */
	public static Date pulsMillis(Date date, int millis) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusMillis(millis);
			return plusDays.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @param hours
	 */
	public static Date pulsHours(Date date, int hours) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);

			DateTime plusDays = dateTime.plusHours(hours);
			return plusDays.toDate();
		}
		return null;
	}

	public static Date parseDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		for (String pattern : TIMESTAMP_PATTERN) {
			Date parseDateTime = parseDate(strDate, pattern);
			if (parseDateTime != null) {
				return parseDateTime;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		DateTime parseDateTime = null;
		try {
			DateTimeFormatter forPattern = DateTimeFormat.forPattern(pattern);
			parseDateTime = forPattern.parseDateTime(strDate);
		} catch (Exception e) {
			// ignore the exception
		}
		if (parseDateTime != null) {
			return parseDateTime.toDate();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		return parseDate(date, TIMESTAMP_PATTERN[0]);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String parseShortDate(Date date) {
		return parseDate(date, TIMESTAMP_PATTERN[1]);
	}

	public static String parseDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		DateTimeFormatter forPattern = DateTimeFormat.forPattern(pattern);
		return forPattern.print(date.getTime());
	}
}
