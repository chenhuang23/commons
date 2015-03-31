package com.github.commons.utils.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <pre>
 * created: 2012-7-31 ����11:16:58
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public final class MessageDigestUtils {

	public static byte[] md5(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	public static byte[] md5(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	public static String md5Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.md5Hex(data);
	}

	public static byte[] sha(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	public static byte[] sha(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	public static byte[] sha256(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	public static byte[] sha256(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	public static String sha256Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha256Hex(data);
	}

	public static byte[] sha384(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	public static byte[] sha384(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	public static String sha384Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha384Hex(data);
	}

	public static byte[] sha512(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	public static byte[] sha512(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	public static String sha512Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha512Hex(data);
	}

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
