/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.policy.SecPolicy;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.support.SignReqParams;
import com.github.commons.security.support.SignTool;
import com.github.commons.security.support.ValidateSignReqParams;
import com.github.commons.security.support.local.codec.LocalCodecTools;
import com.github.commons.security.support.local.sign.LocalSignTools;

/**
 * SignToolsFacade.java 加解密/摘要/编解码 工具集中服务
 *
 * @author zhouxiaofeng 3/26/15
 */
public class CodecToolsFacade implements CodecTool {

    private CodecTool tool;

    public CodecToolsFacade(String appCode, String appKey, SecPolicy policy, int version){
        switch (policy) {
            case LOCAL:
                tool = new LocalCodecTools(appCode, appKey, policy, version);
                break;
            case REMOTE:
            default:
                throw new IllegalArgumentException("Not Support remote.");

        }
    }

    @Override
    public String decode(String ciphertext, EncryptType type) {
        return tool.decode(ciphertext, type);
    }

    @Override
    public String encode(String plainText, EncryptType type) {
        return tool.encode(plainText, type);
    }
}
