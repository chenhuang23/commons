package com.github.commons.security;/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import junit.framework.Assert;

import org.junit.Test;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.policy.SecPolicy;

/**
 * SignToolsTest.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SignToolsTest {

    private final String plaintext = "this is a test.";

    private final String signCode  = "N0nIty/2b9PB+uy3kAovCNVJ83XAkC9oh3R46R4iqQjpJhcePe5vngWDcs7WrqaXoTfthv7ctA1J\n"
                                     + "OA3mM+QiFVGgSRLmlzgCgq6bBGR48R5vIsel3NP/kbbRHeBTzt04/vV8bW8lIns9TT7lkqh26+oM\n"
                                     + "G5S6F83/UCN2fB7NkdM=";

    @Test
    public void testSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        SignToolsFacade tools = new SignToolsFacade("test-code", "test-key", SecPolicy.LOCAL, 1);

        String sign = tools.sign(plaintext, EncryptType.RSA);

        Assert.assertEquals(sign, signCode);

        Assert.assertTrue(tools.validateSign(plaintext, sign, EncryptType.RSA));

    }
}
