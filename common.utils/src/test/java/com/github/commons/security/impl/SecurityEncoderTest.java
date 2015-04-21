/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.impl;

import org.junit.Test;

import com.github.commons.utils.security.SecurityEncoder;

/**
 * SecurityEncoderTesst.java
 *
 * @author zhouxiaofeng 4/16/15
 */
public class SecurityEncoderTest {

    @Test
    public void testGetInstance() throws Exception {
        System.out.println(SecurityEncoder.getInstance().encodeForHTML("12312312<>"));
    }
}
