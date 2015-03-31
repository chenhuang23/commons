package com.github.commons.security.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.commons.utils.security.RSAUtils;

/**
 * RSAUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>
 * Mar 26, 2015
 * </pre>
 */
public class RSAUtilsTest {

    private Map<String, Object> keyPair  = null;

    private final String        testSign = "this is a test.";

    private final String        signCode = "N0nIty/2b9PB+uy3kAovCNVJ83XAkC9oh3R46R4iqQjpJhcePe5vngWDcs7WrqaXoTfthv7ctA1J\n"
            + "OA3mM+QiFVGgSRLmlzgCgq6bBGR48R5vIsel3NP/kbbRHeBTzt04/vV8bW8lIns9TT7lkqh26+oM\n"
            + "G5S6F83/UCN2fB7NkdM=".trim();

    private final String        pubKey   = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcc/zw9IqnDYSXIbiRnssB43cPMmQoUzZgRuRj\n"
            + "mN4buhMv7l7OcnWw4/tP0hFHP+WOrrynDo1JyBMH5j8BYO2Xg5ma/FxwKHAr+mrvXbU9Y0vDzCWr\n"
            + "rbMSeU0RK0zPyGVrKlOfeLAXBUQLQImwtJawB0BcoZ1671pJg7KMLAlhowIDAQAB";

    private final String        priKey   = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJxz/PD0iqcNhJchuJGeywHjdw8y\n"
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

    @Before
    public void before() throws Exception {
        keyPair = RSAUtils.genKeyPair();

    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: genKeyPair()
     */
    @Test
    public void testGenKeyPair() throws Exception {

        Assert.assertNotNull(keyPair);
        Assert.assertNotNull(RSAUtils.getPublicKey(keyPair));
        Assert.assertNotNull(RSAUtils.getPrivateKey(keyPair));

        System.out.println("---------------------pub------------------------");
        System.out.printf(RSAUtils.getPublicKey(keyPair));

        System.out.println("---------------------pri------------------------");
        System.out.printf(RSAUtils.getPrivateKey(keyPair));
    }

    /**
     * Method: sign(byte[] data, String privateKey)
     */
    @Test
    public void testSign() throws Exception {

        String sign = RSAUtils.sign(testSign.getBytes("utf-8"), priKey);

        System.out.println("------------sign-------------");
        System.out.println(sign);

        Assert.assertNotNull(sign.trim());
        Assert.assertEquals(sign.trim(), signCode);
    }

    /**
     * Method: verify(byte[] data, String publicKey, String sign)
     */
    @Test
    public void testVerify() throws Exception {

        boolean verify = RSAUtils.verify(testSign.getBytes("utf-8"), pubKey, signCode);
        Assert.assertTrue(verify);
    }

    /**
     * Method: decryptByPrivateKey(byte[] encryptedData, String privateKey)
     */
    @Test
    public void testDecryptByPrivateKey() throws Exception {

    }

    /**
     * Method: decryptByPublicKey(byte[] encryptedData, String publicKey)
     */
    @Test
    public void testDecryptByPublicKey() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: encryptByPublicKey(byte[] data, String publicKey)
     */
    @Test
    public void testEncryptByPublicKey() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: encryptByPrivateKey(byte[] data, String privateKey)
     */
    @Test
    public void testEncryptByPrivateKey() throws Exception {
        // TODO: Test goes here...
    }

}
