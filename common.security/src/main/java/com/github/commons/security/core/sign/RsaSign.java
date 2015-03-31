/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.core.sign;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.spi.SignSpi;
import com.github.commons.utils.security.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * RsaSignSpi.java
 * 
 *
 * </pre>
 * 
 * @author zhouxiaofeng 3/19/15
 */
public class RsaSign implements SignSpi {

    private static final Logger logger = LoggerFactory.getLogger(RsaSign.class);

    @Override
    public String sign(String source, SecKey key) {

        try {
            return RSAUtils.sign(source.getBytes("utf-8"), key.getPriKey());
        } catch (Exception e) {
            logger.error("rsa exception.", e);
        }

        return null;
    }

    @Override
    public boolean validateSign(String source, String signCode, SecKey key) {

        try {
            return RSAUtils.verify(source.getBytes("utf-8"), key.getPubKey(), signCode);
        } catch (Exception e) {
            logger.error("rsa exception.", e);
        }

        return false;
    }

    @Override
    public int getCode() {
        return EncryptType.RSA.getCode();
    }

}
