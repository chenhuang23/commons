package com.github.commons.utils.format;

import java.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author boyuan.tl
 * 
 */
public class UnitUtils {

	/**
	 * 容量精度格式化工具
	 */
	private static DecimalFormat quotaFormatter0 = new DecimalFormat("0");// 转成0格式
	private static DecimalFormat quotaFormatter1 = new DecimalFormat("0.0");// 转成0.0格式
	private static DecimalFormat quotaFormatter2 = new DecimalFormat("0.00");// 转成0.00格式
	private static DecimalFormat quotaFormatter3 = new DecimalFormat("0.000");// 转成0.000格式
	private static DecimalFormat quotaFormatter4 = new DecimalFormat("0.0000");// 转成0.0000格式

	/**
	 * 容量单位 K,1KB = 1024B
	 */
	private static final String VOLUME_K = " K";
	/**
	 * 容量单位 M,1MB = 1024KB
	 */
	private static final String VOLUME_M = " M";
	/**
	 * 容量单位 G,1GB = 1024MB
	 */
	private static final String VOLUME_G = " G";

	/**
	 * 容量单位 T,1TB = 1024GB
	 */
	private static final String VOLUME_T = " T";

	/**
	 * 容量单位 P,1PB = 1024TB
	 */
	private static final String VOLUME_P = " P";

	private static final long K_MIN_RANGE = 1024L;

	private static final long M_MIN_RANGE = 1024L * 1024;

	private static final long G_MIN_RANGE = 1024L * 1024 * 1024;

	private static final long T_MIN_RANGE = 1024L * 1024 * 1024 * 1024;

	private static final long P_MIN_RANGE = 1024L * 1024 * 1024 * 1024 * 1024;

	private static final String NEGATIVE_SIGN = "-";

	/**
	 * <p>
	 * 格式化容量单位
	 * </p>
	 * 大于1K以K为单位,1KB = 1024B<br>
	 * 大于1M以M为单位,1MB = 1024KB<br>
	 * 大于1G以G为单位,1GB = 1024MB<br>
	 * 大于1T以T为单位,1TB = 1024GB<br>
	 * 大于1P以P为单位,1PB = 1024TB
	 * 
	 * @param size
	 *            大小(bit)
	 * @param needFill
	 *            是否需要补齐位数，如果位数不足，左边加0
	 * @param decimalDigits
	 *            小数位数,最大支持4位，超过4位默认按4位处理.
	 * @return 带单位大小字符串
	 */
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
		if (0 <= bitSize && bitSize < M_MIN_RANGE) {// K单位
			volume = quotaFormatter.format((double) bitSize / K_MIN_RANGE) + VOLUME_K;
		} else if (M_MIN_RANGE <= bitSize && bitSize < G_MIN_RANGE) { // M单位
			volume = quotaFormatter.format((double) bitSize / M_MIN_RANGE) + VOLUME_M;
		} else if (G_MIN_RANGE <= bitSize && bitSize < T_MIN_RANGE) {// G单位 // G单位
			volume = quotaFormatter.format((double) bitSize / G_MIN_RANGE) + VOLUME_G;
		} else if (T_MIN_RANGE <= bitSize && bitSize < P_MIN_RANGE) {// T单位
			volume = quotaFormatter.format((double) bitSize / T_MIN_RANGE) + VOLUME_T;
		} else { // P单位
			volume = quotaFormatter.format((double) bitSize / P_MIN_RANGE) + VOLUME_P;
		}
		if (needFill) {// 需要补齐
			int lessBit = (7 + decimalDigits) - volume.length();// 7 = 小数点1位 + 4小数点前4位+ 2位 单位
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
	 * 小数点后保留4位，自动对齐数字长度
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
	 * 小数点后保留4位
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
	 * 小数点后保留2位，自动对齐数字长度
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
	 * 小数点后保留2位
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
	 * 保留1位小数点
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
