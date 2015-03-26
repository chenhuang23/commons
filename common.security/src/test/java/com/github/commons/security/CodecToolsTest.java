package com.github.commons.security;/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import com.sun.org.apache.bcel.internal.classfile.Code;
import junit.framework.Assert;

import org.junit.Test;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.policy.SecPolicy;

/**
 * SignToolsTest.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class CodecToolsTest {

    private final String plaintext  = "this is a test.";

    private final String encodeCode = "7CA288C840AA39A6E4E0F94934A14ADC";

    @Test
    public void testSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        CodecToolsFacade tools = new CodecToolsFacade("test-code", "test-key", SecPolicy.LOCAL, 1);

        String encode = tools.encode(plaintext, EncryptType.DES);

        System.out.println(encode);

        Assert.assertEquals(encode, encodeCode);

        String decode = tools.decode(encode, EncryptType.DES);

        Assert.assertEquals(decode, plaintext);
    }
}
