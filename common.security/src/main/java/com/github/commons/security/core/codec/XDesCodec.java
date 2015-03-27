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
public class XDesCodec implements CodecSpi {

    private static final Logger logger = LoggerFactory.getLogger(XDesCodec.class);

    private String              salt   = "";

    @Override
    public String encode(String plainText, SecKey key) {
        try {
            String encrypt = DesUtils.encrypt(plainText, key.getPriKey());

            // 每个加密结果加4
            StringBuilder res = new StringBuilder();
            for (char c : encrypt.toCharArray()) {

                char cs = (char) (c + 4);
                res.append(cs);
            }
            return res.toString();
        } catch (Exception e) {
            logger.error("des exception.", e);
        }

        return null;
    }

    @Override
    public String decode(String ciphertext, SecKey key) {
        try {

            // 每个加密结果加4
            StringBuilder res = new StringBuilder();
            for (char c : ciphertext.toCharArray()) {

                char cs = (char) (c - 4);
                res.append(cs);
            }

            return DesUtils.decrypt(res.toString(), key.getPriKey());

        } catch (Exception e) {
            logger.error("des exception.", e);
        }

        return null;
    }

    @Override
    public String getType() {
        return EncryptType.XDES.getType();
    }

    public static void main(String[] args) {

        String s = "WQEPN0nIty/2b9PB+uy3kAovCNVJ83XAkC9oh3R46R4iqQjpJhcePe5vngWDcs7WrqaXoTfthv7ctA1J\n"
                   + "OA3mM+QiFVGgSRLmlzgCgq6bBGR48R5vIsel3NP/kbbRHeBTzt04/vV8bW8lIns9TT7lkqh26+oM\n"
                   + "G5S6F83/UCN2fB7NkdM=";

        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();
        for (char c : s.toCharArray()) {

            char c1 = (char) (c + 4);
            s1.append(c);
            s2.append(c1);
            s3.append((char) (c1 - 4));

        }

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

    }
}
