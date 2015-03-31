/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.remote.codec;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.support.SecTool;

/**
 * RemoteCodecTools.java
 *
 * @author zhouxiaofeng 3/29/15
 */
public class RemoteCodecTools extends SecTool implements CodecTool {

    public RemoteCodecTools(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);
    }

    @Override
    public String decode(String ciphertext, EncryptType type) {
        return null;
    }

    @Override
    public String encode(String plainText, EncryptType type) {
        return null;
    }
}
