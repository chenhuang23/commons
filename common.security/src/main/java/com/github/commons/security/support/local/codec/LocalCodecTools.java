/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.codec;

import com.github.commons.security.ReqParams;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.policy.SecPolicy;
import com.github.commons.security.spi.CodecSpi;
import com.github.commons.security.support.local.LocalSecTool;

/**
 * LocalCodecTools.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public class LocalCodecTools extends LocalSecTool implements CodecTool {

    public LocalCodecTools(String appCode, String appKey, SecPolicy policy, int version){
        super(appCode, appKey, policy, version);
    }

    @Override
    public String decode(String ciphertext, EncryptType type) {
        ReqParams reqParams = new ReqParams(appCode, appKey, version, type);
        CodecSpi codec = InitSec.codecRepository.getCodec(type.getType());

        if (codec != null) {
            return codec.decode(ciphertext, super.getSecKey(reqParams));
        }
        return null;
    }

    @Override
    public String encode(String plainText, EncryptType type) {
        ReqParams reqParams = new ReqParams(appCode, appKey, version, type);
        CodecSpi codec = InitSec.codecRepository.getCodec(type.getType());

        if (codec != null) {
            return codec.encode(plainText, super.getSecKey(reqParams));
        }
        return null;
    }

    /**
     * ≥ı ºªØ
     */

    static class InitSec {

        private static CodecRepository codecRepository = new CodecRepository();
    }

}
