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

    private final String plaintext   = "this is a test测试.";

    private final String encodeCode  = "WQEPC2BBF65166C110555C5013EEA6E9E501BB042E91EC7BA6B9";

    private final String xencodeCode = "WQEPG6FFJ:95::G554999G9457IIE:I=I945FF486I=5IG;FE:F=";

    CodecToolsFacade     tools       = new CodecToolsFacade("test-code", SecPolicy.LOCAL, 1);

    @Test
    public void testDes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String encode = tools.encode(plaintext, EncryptType.DES);

        System.out.println(encode);

        Assert.assertEquals(encode, encodeCode);

        String decode = tools.decode(encode, EncryptType.DES);

        System.out.println(decode);

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
