package com.github.commons.utils.time;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * <pre>
 * desc: 时间格式转换
 * created: 2012-5-15 上午11:13:03
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public class TimeUtils {
	private static String[] TIMESTAMP_PATTERN = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm" };

	/**
	 * 创建Date对象
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
	 * 创建Date对象, hourOfDay,minuteOfHour,secondOfMinute,millisOfSecond 为0
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
	 * 增加dayOfMonth,如果参数days为负数，则相当于减dayOfMonth
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加year,如果参数years为负数，则相当于减year
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Week,如果参数weeks为负数，则相当于减Week
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Second,如果参数seconds为负数，则相当于减Second
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Month,如果参数months为负数，则相当于减Month
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Minute,如果参数minutes为负数，则相当于减Minute
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Milli,如果参数millis为负数，则相当于减Milli
	 * 
	 * @param date
	 *            需要修改的Date对象
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
	 * 增加Hour,如果参数hours为负数，则相当于减Hour
	 * 
	 * @param date
	 *            需要修改的Date对象
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

	/**
	 * 
	 * 把"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm" 格式的字符串转化成<code>Date</code>对象
	 * 
	 * @param strDate
	 * @return 符合"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm"格式的字符串将返回Date对象,当字符串为空或者null时返回null,
	 *         当字符串格式不正确时返回null
	 */
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
	 * 格式化字符串日志，返回"yyyy-MM-dd hh:mm:ss" 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate(Date date) {
		return parseDate(date, TIMESTAMP_PATTERN[0]);
	}

	/**
	 * 格式化字符串日志，返回"yyyy-MM-dd" 格式
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
