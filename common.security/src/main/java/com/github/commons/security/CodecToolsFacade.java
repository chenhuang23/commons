/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security;

import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.support.ReqParams;
import com.github.commons.security.support.local.codec.LocalCodecTools;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author zhouxiaofeng 3/26/15
 */
public class CodecToolsFacade implements CodecTool {

    private CodecTool tool;

    public CodecToolsFacade(String appCode, SecPolicy policy, int version){
        switch (policy) {
            case LOCAL:
                tool = new LocalCodecTools(appCode, policy, version);
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

    @Override
    public SecKey getSecKey(ReqParams params) {
        return tool.getSecKey(params);
    }

}
