package com.github.commons.utils.format;

import java.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author boyuan.tl
 * 
 */
public class UnitUtils {

	private static DecimalFormat quotaFormatter0 = new DecimalFormat("0");
	private static DecimalFormat quotaFormatter1 = new DecimalFormat("0.0");
	private static DecimalFormat quotaFormatter2 = new DecimalFormat("0.00");
	private static DecimalFormat quotaFormatter3 = new DecimalFormat("0.000");
	private static DecimalFormat quotaFormatter4 = new DecimalFormat("0.0000");

	private static final String VOLUME_K = " K";
	/**
	 */
	private static final String VOLUME_M = " M";
	/**
	 */
	private static final String VOLUME_G = " G";

	/**
	 */
	private static final String VOLUME_T = " T";

	/**
	 */
	private static final String VOLUME_P = " P";

	private static final long K_MIN_RANGE = 1024L;

	private static final long M_MIN_RANGE = 1024L * 1024;

	private static final long G_MIN_RANGE = 1024L * 1024 * 1024;

	private static final long T_MIN_RANGE = 1024L * 1024 * 1024 * 1024;

	private static final long P_MIN_RANGE = 1024L * 1024 * 1024 * 1024 * 1024;

	private static final String NEGATIVE_SIGN = "-";

	public static String formatSize(Long size, boolean needFill, int decimalDigits) {
		if (size == null) {
			size = 0l;
		}
		long bitSize = 0;

		if (size != null) {
			bitSize = size.longValue();
		}

		if (size < 0) {
			return NEGATIVE_SIGN + formatSize(Math.abs(size), needFill, decimalDigits);
		}

		DecimalFormat quotaFormatter = null;
		switch (decimalDigits) {
			case 0:
				quotaFormatter = quotaFormatter0;
				break;
			case 1:
				quotaFormatter = quotaFormatter1;
				break;
			case 2:
				quotaFormatter = quotaFormatter2;
				break;
			case 3:
				quotaFormatter = quotaFormatter3;
				break;
			case 4:
				quotaFormatter = quotaFormatter4;
				break;
			default:
				quotaFormatter = quotaFormatter0;
				break;
		}
		String volume = "";
		if (0 <= bitSize && bitSize < M_MIN_RANGE) {
			volume = quotaFormatter.format((double) bitSize / K_MIN_RANGE) + VOLUME_K;
		} else if (M_MIN_RANGE <= bitSize && bitSize < G_MIN_RANGE) {
			volume = quotaFormatter.format((double) bitSize / M_MIN_RANGE) + VOLUME_M;
		} else if (G_MIN_RANGE <= bitSize && bitSize < T_MIN_RANGE) {
			volume = quotaFormatter.format((double) bitSize / G_MIN_RANGE) + VOLUME_G;
		} else if (T_MIN_RANGE <= bitSize && bitSize < P_MIN_RANGE) {
			volume = quotaFormatter.format((double) bitSize / T_MIN_RANGE) + VOLUME_T;
		} else {
			volume = quotaFormatter.format((double) bitSize / P_MIN_RANGE) + VOLUME_P;
		}
		if (needFill) {
			int lessBit = (7 + decimalDigits) - volume.length();
			if (lessBit > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < lessBit; i++) {
					sb.append("0");
				}
				sb.append(volume);
				volume = sb.toString();
			}
		}

		return volume;
	}

	/**
	 *
	 * @param sizeBit
	 * @return
	 */
	public static String formatStrSize4(String sizeBit) {
		if (!StringUtils.isNumeric(sizeBit)) {
			return formatSize(null, true, 4);
		}
		if (sizeBit == null || "".equals(sizeBit)) {
			return formatSize(null, true, 4);
		}
		return formatSize(Long.valueOf(sizeBit), true, 4);
	}

	/**
	 *
	 * @param sizeBit
	 * @return
	 */
	public static String formatStrSize4NotFill(String sizeBit) {
		if (!StringUtils.isNumeric(sizeBit)) {
			return formatSize(null, false, 4);
		}
		if (sizeBit == null || "".equals(sizeBit)) {
			return formatSize(null, false, 4);
		}
		return formatSize(Long.valueOf(sizeBit), false, 4);
	}

	/**
	 *
	 * @param sizeBit
	 * @return
	 */
	public static String formatStrSize2(String sizeBit) {
		if (!StringUtils.isNumeric(sizeBit)) {
			return formatSize(null, true, 2);
		}
		if (sizeBit == null || "".equals(sizeBit)) {
			return formatSize(null, true, 2);
		}
		return formatSize(Long.valueOf(sizeBit), true, 2);
	}

	/**
	 *
	 * @param sizeBit
	 * @return
	 */
	public static String formatStrSize2NotFill(String sizeBit) {
		if (!StringUtils.isNumeric(sizeBit)) {
			return formatSize(null, false, 2);
		}
		if (sizeBit == null || "".equals(sizeBit)) {
			return formatSize(null, false, 2);
		}
		return formatSize(Long.valueOf(sizeBit), false, 2);
	}

	/**
	 *
	 * @param sizeBit
	 * @return
	 */
	public static String formatLongSize2NotFill(Long sizeBit) {
		if (sizeBit == null) {
			return formatSize(null, false, 1);
		}
		return formatSize(Long.valueOf(sizeBit), false, 1);
	}
}
