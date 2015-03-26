/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

/**
 * Decryptor.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface DecryptorSpi {

    /**
     * Ω‚√‹
     * @param ciphertext
     * @return
     */
    public String decrypt(String ciphertext);
}
