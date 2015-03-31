/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.core.codec;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.utils.security.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.spi.CodecSpi;

/**
 * AesCodec.java
 *
 * @author zhouxiaofeng 3/30/15
 */
public class AesCodec implements CodecSpi {

    private static final Logger logger = LoggerFactory.getLogger(AesCodec.class);
    public static final String  UTF_8  = "utf-8";

    @Override
    public String encode(String plainText, SecKey key) {
        try {
            return AESUtil.encrypt(plainText, key.getPriKey());
        } catch (Exception e) {
            logger.error("aes encode exception.", e);
        }

        return null;
    }

    @Override
    public String decode(String ciphertext, SecKey key) {
        try {
            return AESUtil.decrypt(ciphertext, key.getPriKey());
        } catch (Exception e) {
            logger.error("aes decode exception.", e);
        }

        return null;

    }

    @Override
    public int getCode() {
        return EncryptType.AES.getCode();
    }
}
