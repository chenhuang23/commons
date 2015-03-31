/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.config.SecKey;

/**
 * CodecSpi.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public interface CodecSpi {

    /**
     *
     * @param plainText
     * @return ciphertext
     */
    public String encode(String plainText, SecKey key);

    /**
     *
     * @param ciphertext
     * @return plainText
     */
    public String decode(String ciphertext, SecKey key);

    /**
     *
     * @return
     */
    public int getCode();
}
