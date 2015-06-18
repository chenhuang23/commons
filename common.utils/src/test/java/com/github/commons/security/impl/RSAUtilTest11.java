/**
 *
 */
package com.github.commons.security.impl;

import com.github.commons.utils.format.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;

import javax.crypto.Cipher;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 */
public class RSAUtilTest11 {

    static String keyFile = "/tmp/RSAKey.txt";

    /**
     * * 生成密钥对 *
     *
     * @return KeyPair *
     * @throws EncryptException
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                                                                       new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低

            AlgorithmParameterSpec spec = new RSAKeyGenParameterSpec(KEY_SIZE, RSAKeyGenParameterSpec.F4);

            // keyPairGen.initialize(KEY_SIZE, new SecureRandom());

            keyPairGen.initialize(spec);

            KeyPair keyPair = keyPairGen.generateKeyPair();
            saveKeyPair(keyPair);
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static KeyPair getKeyPair() throws Exception {
        FileInputStream fis = new FileInputStream(keyFile);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    public static void saveKeyPair(KeyPair kp) throws Exception {

        FileOutputStream fos = new FileOutputStream(keyFile);
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
     * @param key 加密的密钥 *
     * @param data 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) cipher.doFinal(data, i * blockSize, blockSize, raw,
                                                                            i * outputSize);
                else cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。

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
     * @param key 解密的密钥 *
     * @param raw 已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static byte[] decrypt(PrivateKey pk, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;

            // while (raw.length - j * blockSize > 0) {
            // bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
            // j++;
            // }

            int outputSize = cipher.getOutputSize(data.length);

            byte[] raw = new byte[outputSize * blockSize];
            int i = 0;

            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                i++;
            }

            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * * *
     *
     * @param args *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // KeyPair keyPair = generateKeyPair();

        KeyPair keyPair = getKeyPair();
        RSAPublicKey rsap = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();

        String s = new BigInteger(aPrivate.getEncoded()).toString(16);

        System.out.println("private hex: " + s);

        System.out.println("private modules: " + aPrivate.getModulus().toString(16));

        System.out.println("private PrivateExponent: " + aPrivate.getPrivateExponent().toString(16));

        System.out.println("--------------------------------------------");
        // =================

        System.out.println("public : " + rsap.getEncoded());

        System.out.println("public Modulus: " + rsap.getModulus().toString(16));

        System.out.println("public ublicExponent: " + rsap.getPublicExponent());

        String test = "test";

        byte[] en_test = encrypt(rsap, test.getBytes());

        System.out.println("base64: --> " + new BigInteger(en_test).toString(16));

        byte[] de_test = decrypt(keyPair.getPrivate(), en_test);

        System.out.println(new String(de_test));

        String encode = "a70a95214fdb8555dbb506a827dfea6a936fa8fe6f54f2843629234c82a3dfe17f9e8d54090ace8ddc29a0d74a8da07bb87b851cca5b7dde01418f9a704110c925d8e25d78157e86d3fb452e91c4e416868cca23013f484014d90913a0bd1edb5241490d6f18478c2a0aed448c30091bbc9f85df5d0f38b0174dd8c8b38765cb8aa953af6667c61fe814b9455584b673b535dd6ff259b15accc5";

        // byte[] decode = Base64Utils.decode(encode);

        byte[] en_result = hexStringToBytes(encode);

        System.out.println("decode.length: --> " + en_result.length);

        byte[] decrypt = decrypt(keyPair.getPrivate(), en_result);

        System.out.println("new-----> " + new String(decrypt));
    }

    /** * 16进制 To byte[] * @param hexString * @return byte[] */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /** * Convert char to byte * @param c char * @return byte */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
