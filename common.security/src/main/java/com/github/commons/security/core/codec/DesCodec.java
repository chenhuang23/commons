/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.core.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.spi.CodecSpi;
import com.github.commons.utils.security.DesUtils;

/**
 * DecCodec.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public class DesCodec implements CodecSpi {

    private static final Logger logger = LoggerFactory.getLogger(DesCodec.class);

    @Override
    public String encode(String plainText, SecKey key) {
        try {
            return DesUtils.encrypt(plainText, key.getPriKey());
        } catch (Exception e) {
            logger.error("des exception.", e);
        }

        return plainText;
    }

    @Override
    public String decode(String ciphertext, SecKey key) {
        try {
            return DesUtils.decrypt(ciphertext, key.getPriKey());
        } catch (Exception e) {
            logger.error("des exception.", e);
        }

        return ciphertext;
    }

    @Override
    public int getCode() {
        return EncryptType.DES.getCode();
    }

}
