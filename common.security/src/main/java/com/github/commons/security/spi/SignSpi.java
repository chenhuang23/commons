/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.config.SecKey;

/**
 * Sign.java
 *
 *
 * @author zhouxiaofeng 3/17/15
 */
public interface SignSpi {

    /**
     *
     * @param plaintext
     * @return
     */
    public String sign(String plaintext, SecKey key);

    /**
     *
     * @param plaintext
     * @param ciphertext
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, SecKey key);

    /**
     *
     * @return
     */
    public int getCode();
}
