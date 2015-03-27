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
public class CodecToolsTest {

    private final String plaintext   = "this is a test.";

    private final String encodeCode  = "WQEP7CA288C840AA39A6E4E0F94934A14ADC";

    private final String xencodeCode = "WQEP;GE6<<G<84EE7=E:I8I4J=8=78E58EHG";

    CodecToolsFacade     tools       = new CodecToolsFacade("test-code", SecPolicy.LOCAL, 1);

    @Test
    public void testDes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String encode = tools.encode(plaintext, EncryptType.DES);

        System.out.println(encode);

        Assert.assertEquals(encode, encodeCode);

        String decode = tools.decode(encode, EncryptType.DES);

        Assert.assertEquals(decode, plaintext);
    }

    @Test
    public void testXDes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String encode = tools.encode(plaintext, EncryptType.XDES);

        System.out.println(encode);

        Assert.assertEquals(encode, xencodeCode);

        String decode = tools.decode(encode, EncryptType.XDES);

        Assert.assertEquals(decode, plaintext);
    }
}
