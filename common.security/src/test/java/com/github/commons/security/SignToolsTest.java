package com.github.commons.security;/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import junit.framework.Assert;

import org.junit.Test;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.constants.SecPolicy;

/**
 * SignToolsTest.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SignToolsTest {

    private final String plaintext = "this is a test.";

    private final String signCode  = "WQEPN0nIty/2b9PB+uy3kAovCNVJ83XAkC9oh3R46R4iqQjpJhcePe5vngWDcs7WrqaXoTfthv7ctA1J\n"
                                     + "OA3mM+QiFVGgSRLmlzgCgq6bBGR48R5vIsel3NP/kbbRHeBTzt04/vV8bW8lIns9TT7lkqh26+oM\n"
                                     + "G5S6F83/UCN2fB7NkdM=";

    private final String xsignCode = "WQEPR4rMx}36f=TF/y}7oEszGRZN<7\\EoG=sl7V8:V8muUntNlgiTi9zrk[Hgw;[vue\\sXjxlz;gxE5N\u000ESE7qQ/UmJZKkWVPqp~kGku:fFKV8<V9zMwip7RT3offVLiFX~x483zZ<f[<pMrw=XX;poul6:/sQ\u000EK9W:J<73YGR6jF;RohQA";

    @Test
    public void testSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        SignToolsFacade tools = new SignToolsFacade("test-code", SecPolicy.LOCAL, 1);

        String sign = tools.sign(plaintext, EncryptType.RSA);

        System.out.println(sign);

        Assert.assertEquals(sign, signCode);

        Assert.assertTrue(tools.validateSign(plaintext, sign, EncryptType.RSA));

    }

    @Test
    public void testXSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        SignToolsFacade tools = new SignToolsFacade("test-code", SecPolicy.LOCAL, 1);

        String sign = tools.sign(plaintext, EncryptType.XRSA);

        System.out.println(sign);

        System.out.println(xsignCode);

        Assert.assertEquals(sign, xsignCode);

        Assert.assertTrue(tools.validateSign(plaintext, sign, EncryptType.XRSA));

    }
}
