/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.codec;

import com.github.commons.security.constants.SaltPrefix;
import com.github.commons.security.support.ReqParams;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.spi.CodecSpi;
import com.github.commons.security.support.local.LocalSecTool;

/**
 * LocalCodecTools.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public class LocalCodecTools extends LocalSecTool implements CodecTool {

    public LocalCodecTools(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);
    }

    @Override
    public String decode(String ciphertext, EncryptType type) {
        ReqParams reqParams = new ReqParams(appCode, version, type);
        CodecSpi codec = InitSec.codecRepository.getCodec(type.getType());

        if (codec != null) {

            // 为了兼容，解密会判断是哪个版本的加密数据
            SaltPrefix saltPrefix = SaltPrefix.lookup(ciphertext.substring(0, 4));

            if (saltPrefix != SaltPrefix.NULL) {
                reqParams.version = saltPrefix.getVersion();
                ciphertext = ciphertext.substring(4, ciphertext.length());
            }

            return codec.decode(ciphertext, super.getSecKey(reqParams));
        }
        return null;
    }

    @Override
    public String encode(String plainText, EncryptType type) {
        ReqParams reqParams = new ReqParams(appCode, version, type);
        CodecSpi codec = InitSec.codecRepository.getCodec(type.getType());

        if (codec != null) {
            String encode = codec.encode(plainText, super.getSecKey(reqParams));
            return SaltPrefix.lookup(this.version).getSalt() + encode;
        }
        return null;
    }

    /**
     * 初始化
     */

    static class InitSec {

        private static CodecRepository codecRepository = new CodecRepository();
    }

}
