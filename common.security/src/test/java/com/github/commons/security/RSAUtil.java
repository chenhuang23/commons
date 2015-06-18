package com.github.commons.security;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;

import javax.crypto.Cipher;

import com.github.commons.utils.format.Base64Utils;
import org.apache.commons.lang.StringUtils;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 需要bcprov-jdk16-140.jar包。
 */
public class RSAUtil {

    private static String RSAKeyStore = "RSAKey.txt";

    /**
     * * 生成密钥对 *
     * 
     * @return KeyPair *
     */
    public static KeyPair generateKeyPair(String basePath) throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                                                                       new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // 大小
            final int KEY_SIZE = 1024;
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            saveKeyPair(keyPair, basePath);

            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 获取密钥对
     * 
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(String basePath) throws Exception {
        FileInputStream fis = new FileInputStream(
                                                  StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    /**
     * 保存密钥
     * 
     * @param kp
     * @throws Exception
     */
    public static void saveKeyPair(KeyPair kp, String basePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(
                                                    StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // 生成密钥
        oos.writeObject(kp);
        oos.close();
        fos.close();
    }

    /**
     * * 生成公钥 *
     * 
     * @param modulus *
     * @param publicExponent *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * * 生成私钥 *
     * 
     * @param modulus *
     * @param privateExponent *
     * @return RSAPrivateKey *
     * @throws Exception
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * * 加密 *
     * 
     * @param data 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            int blockSize = cipher.getBlockSize();
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * * 解密 *
     * 
     * @param raw 已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 解密方法 paramStr ->密文 basePath ->RSAKey.txt所在的文件夹路径
     **/
    public static String decryptStr(String paramStr, String basePath) throws Exception {
        byte[] en_result = new BigInteger(paramStr, 16).toByteArray();
        byte[] de_result = decrypt(getKeyPair(basePath).getPrivate(), en_result);
        StringBuffer sb = new StringBuffer();
        sb.append(new String(de_result));
        // 返回解密的字符串
        return sb.reverse().toString();
    }

    public static void main(String[] args) throws Exception {

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcc/zw9IqnDYSXIbiRnssB43cPMmQoUzZgRuRj\n"
                           + "mN4buhMv7l7OcnWw4/tP0hFHP+WOrrynDo1JyBMH5j8BYO2Xg5ma/FxwKHAr+mrvXbU9Y0vDzCWr\n"
                           + "rbMSeU0RK0zPyGVrKlOfeLAXBUQLQImwtJawB0BcoZ1671pJg7KMLAlhowIDAQAB";
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);

        byte[] encrypt = RSAUtil.encrypt(publicK, "test".getBytes());

        String encode = Base64Utils.encode(encrypt);

        System.out.println("encode: ----> " + encode);

        // ======

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJxz/PD0iqcNhJchuJGeywHjdw8y\n"
                            + "ZChTNmBG5GOY3hu6Ey/uXs5ydbDj+0/SEUc/5Y6uvKcOjUnIEwfmPwFg7ZeDmZr8XHAocCv6au9d\n"
                            + "tT1jS8PMJautsxJ5TRErTM/IZWsqU594sBcFRAtAibC0lrAHQFyhnXrvWkmDsowsCWGjAgMBAAEC\n"
                            + "gYEAk76Q0fcM7L6+RH5qrtGXAjyNVYOPw+j5A6hOy6MztFle/zeKvkimzZot3G4TNQapQLnQm64J\n"
                            + "TPCs0BvlyeZ/1scX9nyLNQEoWKhRezKKiYI6vyX016QO/43aSDxwLHFS6zNWF+YcOJsmDge4WKhH\n"
                            + "sPy0mpMsHeEA7qm7NgfpxskCQQDYtfWh0UESkioCfi01skMnYs43RM8aHZyPhn+QTYuJ+b0g1FVQ\n"
                            + "AY4Nv1ITnKZS9mC+ESm7sDikw8WDbtqjgzodAkEAuNFU3WJ2arIiHTXKC10fZqA9OdO/JFtEQ56o\n"
                            + "+S2Q87cOVAORXvjWytvWAvOy2xqKU+dQjI9xv4rZHi8Nu+Y+vwJACqko5ET/BoLaPjUm1DVoyE88\n"
                            + "BcwExCwgv47GR7sI2kjG3Q6VA9KPwm1fBEf4hqxIJhGCFBGfN7vJw6V4bALQoQJAAWEGehRm/8rO\n"
                            + "eFtTY5xRRKnDazAKSBIqQzrm1d0iLL9b6wKPzh6bM65KPYFl/z6Gc7PRJQtY9O3rSs9dEd7Y7wJA\n"
                            + "RAQFzFJFcStJZp0g3Z3FnNDuicJYzkrApG9hV5rI9W7yz+uevbxVQOTTUaHMH33XGNTfMpsc7Q23\n"
                            + "mJdGXc/CLw==";

        keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        byte[] decrypt = RSAUtil.decrypt(privateK, encrypt);

        System.out.println(new String(decrypt));

    }
}
