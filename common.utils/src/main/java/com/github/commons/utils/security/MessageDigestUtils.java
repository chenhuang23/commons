package com.github.commons.utils.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <pre>
 * desc: ����ժҪ������ ,�ṩMD5,SHA-1,SHA-256,SHA-384,SHA-512ժҪ
 * created: 2012-7-31 ����11:16:58
 * author: xiaofeng.zhouxf
 * todo: 
 * history:
 * </pre>
 */
public final class MessageDigestUtils {

	/**
	 * ����MD5ժҪ
	 * 
	 * @param data
	 * 
	 * @return ����16 bitsժҪ�����dataΪblank,���ؿ�byte����
	 */
	public static byte[] md5(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	/**
	 * ����MD5ժҪ
	 * 
	 * @param data
	 * 
	 * @return ����16 bitsժҪ�����dataΪblank,���ؿ�byte����
	 */
	public static byte[] md5(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.md5(data);
	}

	/**
	 * ����MD5ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���س���32��16���Ƶ��ַ��� ժҪ�����dataΪblank,���ؿ��ַ���
	 */
	public static String md5Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.md5Hex(data);
	}

	/**
	 * ����SHA-1ժҪ
	 * 
	 * @param data
	 * 
	 * @return ����160 bitժҪ�����dataΪblank���ؿ�byte����
	 */
	public static byte[] sha(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	/**
	 * ����SHA-1ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank,���ؿ�byte����
	 */
	public static byte[] sha(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha(data);
	}

	/**
	 * ����SHA-256ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha256(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	/**
	 * ����SHA-256ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha256(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha256(data);
	}

	/**
	 * ����SHA-256ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ��ַ���
	 */
	public static String sha256Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha256Hex(data);
	}

	/**
	 * ����SHA-384ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha384(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	/**
	 * ����SHA-384ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha384(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha384(data);
	}

	/**
	 * ����SHA-384ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ��ַ���
	 */
	public static String sha384Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha384Hex(data);
	}

	/**
	 * ����SHA-512ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha512(byte[] data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	/**
	 * ����SHA-512ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ�byte����
	 */
	public static byte[] sha512(String data) {
		if (!validateData(data)) {
			return new byte[] {};
		}
		return DigestUtils.sha512(data);
	}

	/**
	 * ����SHA-512ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ��ַ���
	 */
	public static String sha512Hex(String data) {
		if (!validateData(data)) {
			return StringUtils.EMPTY;
		}
		return DigestUtils.sha512Hex(data);
	}

	/**
	 * ����SHA-1ժҪ
	 * 
	 * @param data
	 * 
	 * @return ���dataΪblank���ؿ��ַ���
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
