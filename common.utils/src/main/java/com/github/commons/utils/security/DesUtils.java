package com.github.commons.utils.security;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 */
public class DesUtils {

    private final static String ALGORITHM = "DES";

    /**
     * �����ݽ���DES����.
     */
    public final static String decrypt(String data, String key) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
    }

    /**
     * ����DES���ܹ������ݽ��н���.
     */
    public final static String encrypt(String data, String key) throws Exception {
        return byte2hex(encrypt(data.getBytes(), key.getBytes()));
    }

    // ==============================
    /**
     * ��ָ����key�����ݽ���DES����.
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES�㷨Ҫ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ��ԭʼ�ܳ����ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(key);
        // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����
        // һ��SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher����ʵ����ɼ��ܲ���
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // ���ܳ׳�ʼ��Cipher����
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // ���ڣ���ȡ���ݲ�����
        // ��ʽִ�м��ܲ���
        return cipher.doFinal(data);
    }

    /**
     * ��ָ����key�����ݽ���DES����.
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES�㷨Ҫ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
        DESKeySpec dks = new DESKeySpec(key);
        // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����
        // һ��SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher����ʵ����ɽ��ܲ���
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // ���ܳ׳�ʼ��Cipher����
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // ���ڣ���ȡ���ݲ�����
        // ��ʽִ�н��ܲ���
        return cipher.doFinal(data);
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) throw new IllegalArgumentException("���Ȳ���ż��");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String md5Password = "xsdfasdfasdf3egfadaa";
        String str = DesUtils.encrypt("test", md5Password);
        System.out.println("str: " + str);
        str = DesUtils.decrypt(str, md5Password);
        System.out.println("str: " + str);
    }
}
