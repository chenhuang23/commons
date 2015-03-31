/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.core.sign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.spi.SignSpi;
import com.github.commons.utils.security.RSAUtils;

/**
 * <pre>
 * RsaSignSpi.java
 * 
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public class XRsaSign implements SignSpi {

    private static final Logger logger = LoggerFactory.getLogger(XRsaSign.class);
    public static final String  UTF_8  = "utf-8";

    @Override
    public String sign(String source, SecKey key) {

        try {
            String sign = RSAUtils.sign(source.getBytes(UTF_8), key.getPriKey());

            StringBuilder res = new StringBuilder();
            for (char c : sign.toCharArray()) {

                char cs = (char) (c + 4);
                res.append(cs);
            }
            return res.toString();

        } catch (Exception e) {
            logger.error("rsa exception.", e);
        }

        return null;
    }

    @Override
    public boolean validateSign(String source, String signCode, SecKey key) {

        try {

            StringBuilder res = new StringBuilder();
            for (char c : signCode.toCharArray()) {

                char cs = (char) (c - 4);
                res.append(cs);
            }

            return RSAUtils.verify(source.getBytes(UTF_8), key.getPubKey(), res.toString());
        } catch (Exception e) {
            logger.error("rsa exception.", e);
        }

        return false;
    }

    @Override
    public int getCode() {
        return EncryptType.XRSA.getCode();
    }

}
