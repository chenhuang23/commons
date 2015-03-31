package com.github.commons.utils.log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * <pre>
 * created: 2012-4-26 ����09:52:51
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public class LogUtils {

	public static void debug(Logger logger, String message) {
		if (logger != null && logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	public static void debug(Logger logger, String pattern, Object... arguments) {
		if (logger != null && logger.isDebugEnabled()) {
			logger.debug(pattern, arguments);
		}
	}

	public static void debug(Logger logger, Exception exception) {
		if (logger != null && logger.isDebugEnabled()) {
			logger.debug(formatException(exception));
		}
	}

	public static void warn(Logger logger, String message) {
		if (logger != null && logger.isWarnEnabled()) {
			logger.warn(message);
		}
	}

	public static void warn(Logger logger, String pattern, Object... arguments) {
		if (logger != null && logger.isWarnEnabled()) {
			logger.warn(pattern, arguments);
		}
	}

	public static void warn(Logger logger, Exception exception) {
		if (logger != null && logger.isWarnEnabled()) {
			logger.warn(formatException(exception));
		}
	}

	public static void info(Logger logger, String message) {
		if (logger != null && logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public static void info(Logger logger, String pattern, Object... arguments) {
		if (logger != null && logger.isInfoEnabled()) {
			logger.info(pattern, arguments);
		}
	}

	public static void info(Logger logger, Exception exception) {
		if (logger != null && logger.isInfoEnabled()) {
			logger.info(formatException(exception));
		}
	}

	public static void error(Logger logger, String message) {
		if (logger != null && logger.isErrorEnabled()) {
			logger.error(message);
		}
	}

	public static void error(Logger logger, String pattern, Object... arguments) {
		if (logger != null && logger.isErrorEnabled()) {
			logger.error(pattern, arguments);
		}
	}

	public static void error(Logger logger, Exception exception) {
		if (logger != null && logger.isErrorEnabled()) {
			logger.error(formatException(exception));
		}
	}

	public static void error(Logger logger, Exception exception, String message) {
		if (logger != null && logger.isErrorEnabled()) {
			logger.error(formatException(exception), message);
		}
	}

	// ================
	protected static String formatException(Exception exception, String message) {
		if (exception != null) {
			return buildTraceMessage(exception, message);
		}

		return StringUtils.EMPTY;
	}

	protected static String formatException(Exception exception) {
		return formatException(exception, null);
	}

	protected static String buildTraceMessage(Exception exception, String message) {
		StringBuilder sb = new StringBuilder();
		if (exception != null) {
			sb.append("Exception happend: ");
			sb.append(exception.toString());
			if (StringUtils.isNotBlank(message)) {
				sb.append("\n");
				sb.append(message);
			}

			if (StringUtils.isNotBlank(exception.getMessage())) {
				sb.append("\n");
				sb.append(exception.getMessage());
			}

			StackTraceElement[] trace = exception.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				sb.append("\nat ");
				sb.append(trace[i].toString());
				sb.append("\n");
			}
			Throwable childCause = exception.getCause();
			if (childCause != null)
				printStackTraceAsCause(childCause, sb, trace);
		}
		return sb.toString();
	}

	protected static void printStackTraceAsCause(Throwable ourCause, StringBuilder sb,
				StackTraceElement[] causedTrace) {
		StackTraceElement[] trace = ourCause.getStackTrace();
		int m = trace.length - 1, n = causedTrace.length - 1;
		while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
			m--;
			n--;
		}
		int framesInCommon = trace.length - 1 - m;

		sb.append("Caused by: " + ourCause);
		for (int i = 0; i <= m; i++) {
			sb.append("\nat ");
			sb.append(trace[i]);
		}
		if (framesInCommon != 0) {
			sb.append("\n... ");
			sb.append(framesInCommon);
			sb.append(" more");
		}
		Throwable childCause = ourCause.getCause();
		if (childCause != null)
			printStackTraceAsCause(childCause, sb, trace);
	}

	// ===================================
	// public static void main(String[] args) {
	// try {
	// String test = null;
	// test.toString();
	// } catch (Exception e) {
	// System.out.println(formatException(e));
	// }
	// }
}
