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
 * ���ܷ���
 * </pre>
 * 
 * @author zhouxiaofeng 3/17/15
 */
public interface EncryptorSpi {

    /**
     * ����
     * 
     * @param source
     * @return
     */
    public String encrypt(String plaintext);

}
