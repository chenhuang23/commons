/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;

/**
 * CodecTool.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public interface CodecTool {

    public String decode(String ciphertext, EncryptType type);

    public String encode(String plainText, EncryptType type);

    public abstract SecKey getSecKey(ReqParams params);
}
