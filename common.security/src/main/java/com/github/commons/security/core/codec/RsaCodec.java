/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.core.codec;

import com.github.commons.utils.format.Base64Utils;
import com.github.commons.utils.security.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.spi.CodecSpi;
import com.github.commons.utils.security.AESUtil;

/**
 * AesCodec.java
 *
 * @author zhouxiaofeng 3/30/15
 */
public class RsaCodec implements CodecSpi {

    private static final Logger logger = LoggerFactory.getLogger(RsaCodec.class);
    public static final String  UTF_8  = "utf-8";

    @Override
    public String encode(String plainText, SecKey key) {
        try {
            byte[] bytes = RSAUtils.encryptByPublicKey(plainText.getBytes(UTF_8), key.getPubKey());

            return Base64Utils.encode(bytes);

        } catch (Exception e) {
            logger.error("aes encode exception.", e);
        }

        return null;
    }

    @Override
    public String decode(String ciphertext, SecKey key) {
        try {
            byte[] bytes = RSAUtils.decryptByPrivateKey(Base64Utils.decode(ciphertext), key.getPriKey());
            return new String(bytes, UTF_8);
        } catch (Exception e) {
            logger.error("aes decode exception.", e);
        }

        return null;

    }

    @Override
    public int getCode() {
        return EncryptType.RSA.getCode();
    }
}
