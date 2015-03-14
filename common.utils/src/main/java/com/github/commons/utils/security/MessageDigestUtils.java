package com.github.commons.utils.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <pre>
 * desc: 计算摘要工具类 ,提供MD5,SHA-1,SHA-256,SHA-384,SHA-512摘要
 * created: 2012-7-31 上午11:16:58
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public final class MessageDigestUtils {

	/**
	 * 计算MD5摘要
	 * 
	 * @param data
	 * 
	 * @return 返回16 bits摘要，如果data为blank,返回空byte数组
	 */
	public static byte[] md5(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	/**
	 * 计算MD5摘要
	 * 
	 * @param data
	 * 
	 * @return 返回16 bits摘要，如果data为blank,返回空byte数组
	 */
	public static byte[] md5(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	/**
	 * 计算MD5摘要
	 * 
	 * @param data
	 * 
	 * @return 返回长度32的16进制的字符串 摘要，如果data为blank,返回空字符串
	 */
	public static String md5Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.md5Hex(data);
	}

	/**
	 * 计算SHA-1摘要
	 * 
	 * @param data
	 * 
	 * @return 返回160 bit摘要，如果data为blank返回空byte数组
	 */
	public static byte[] sha(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	/**
	 * 计算SHA-1摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank,返回空byte数组
	 */
	public static byte[] sha(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	/**
	 * 计算SHA-256摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha256(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	/**
	 * 计算SHA-256摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha256(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	/**
	 * 计算SHA-256摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空字符串
	 */
	public static String sha256Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha256Hex(data);
	}

	/**
	 * 计算SHA-384摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha384(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	/**
	 * 计算SHA-384摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha384(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	/**
	 * 计算SHA-384摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空字符串
	 */
	public static String sha384Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha384Hex(data);
	}

	/**
	 * 计算SHA-512摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha512(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	/**
	 * 计算SHA-512摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空byte数组
	 */
	public static byte[] sha512(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	/**
	 * 计算SHA-512摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空字符串
	 */
	public static String sha512Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha512Hex(data);
	}

	/**
	 * 计算SHA-1摘要
	 * 
	 * @param data
	 * 
	 * @return 如果data为blank返回空字符串
	 */
	public static String shaHex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.shaHex(data);
	}

	// ===============

	private static boolean validateData(byte[] data) {
		return data != null && data.length > 0;
	}

	private static boolean validateData(String data) {
		return StringUtils.isNotBlank(data);
	}
}
