/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

/**
 * <pre>
 * Encryptor.java 
 * 
 * 加密服务
 * </pre>
 * 
 * @author zhouxiaofeng 3/17/15
 */
public interface EncryptorSpi {

    /**
     * 加密
     * 
     * @param source
     * @return
     */
    public String encrypt(String plaintext);

}
