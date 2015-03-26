/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.policy.SecPolicy;
import com.github.commons.security.support.local.sign.LocalSignTools;
import com.github.commons.security.support.SignReqParams;
import com.github.commons.security.support.SignTool;
import com.github.commons.security.support.ValidateSignReqParams;

/**
 * SignToolsFacade.java 签名工具集中服务
 *
 * @author zhouxiaofeng 3/26/15
 */
public class SignToolsFacade implements SignTool {

    private SignTool tool;

    public SignToolsFacade(String appCode, String appKey, SecPolicy policy, int version){
        switch (policy) {
            case LOCAL:
                tool = new LocalSignTools(appCode, appKey, policy, version);
                break;
            case REMOTE:
            default:
                throw new IllegalArgumentException("Not Support remote.");

        }
    }

    @Override
    public String sign(SignReqParams params) {
        return tool.sign(params);
    }

    @Override
    public String sign(String plaintext, EncryptType type) {
        return tool.sign(plaintext, type);
    }

    @Override
    public boolean validateSign(ValidateSignReqParams params) {
        return tool.validateSign(params);
    }

    @Override
    public boolean validateSign(String plaintext, String ciphertext, EncryptType type) {
        return tool.validateSign(plaintext, ciphertext, type);
    }
}
