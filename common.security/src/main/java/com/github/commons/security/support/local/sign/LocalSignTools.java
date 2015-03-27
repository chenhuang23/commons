/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.sign;

import com.github.commons.security.constants.SaltPrefix;
import org.apache.commons.lang.StringUtils;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.support.SignReqParams;
import com.github.commons.security.support.SignTool;
import com.github.commons.security.support.ValidateSignReqParams;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.spi.SignSpi;
import com.github.commons.security.support.local.LocalSecTool;

/**
 * SecurityTools.java 本地签名工具
 *
 * @author zhouxiaofeng 3/17/15
 */
public class LocalSignTools extends LocalSecTool implements SignTool {

    public LocalSignTools(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);
    }

    /**
     * 获取签名
     *
     * @param params
     * @return
     */
    private SignSpi getSign(ReqParams params) {

        return InitSec.signRepository.getSign(params.secType.getType());
    }

    /**
     * 签名操作
     *
     * @param params
     * @return
     */
    public String sign(SignReqParams params) {

        // 补齐参数
        CompParams(params);

        validateParams(params);

        SignSpi sign = getSign(params);
        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        String signRes = sign.sign(params.plaintext, getSecKey(params));

        return SaltPrefix.lookup(params.version).getSalt() + signRes;
    }

    private void CompParams(SignReqParams params) {
        if (StringUtils.isBlank(params.appCode)) {
            params.appCode = this.appCode;
        }

        if (params.version <= 0) {
            params.version = this.version;
        }
    }

    /**
     * 签名操作
     *
     * @param plaintext
     * @param type
     * @return
     */
    public String sign(String plaintext, EncryptType type) {
        validateSignParams(plaintext, type);

        ReqParams reqParams = new ReqParams(appCode, version, type);
        SignSpi sign = getSign(reqParams);

        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        String signRes = sign.sign(plaintext, getSecKey(reqParams));

        return SaltPrefix.lookup(version).getSalt() + signRes;
    }

    private void validateSignParams(String plaintext, EncryptType type) {
        if (StringUtils.isBlank(appCode)) {
            throw new IllegalArgumentException("appCode can't be blank.");
        }

        if (StringUtils.isBlank(plaintext)) {
            throw new IllegalArgumentException("plaintext can't be blank.");
        }

        if (type == null) {
            throw new IllegalArgumentException("EncryptType can't be null.");
        }
    }

    /**
     * 验证签名
     *
     * @param params
     * @return
     */
    public boolean validateSign(ValidateSignReqParams params) {
        validateParams(params);

        SignSpi sign = getSign(params);
        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        // 为了兼容，解密会判断是哪个版本的加密数据
        SaltPrefix saltPrefix = SaltPrefix.lookup(params.ciphertext.substring(0, 4));

        if (saltPrefix != SaltPrefix.NULL) {
            params.version = saltPrefix.getVersion();
            params.ciphertext = params.ciphertext.substring(4, params.ciphertext.length());
        }

        return sign.validateSign(params.plaintext, params.ciphertext, getSecKey(params));
    }

    /**
     * 验证签名
     *
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, EncryptType type) {
        if (StringUtils.isBlank(appCode)) {
            throw new IllegalArgumentException("Params is illegal. appCode can't be blank.");
        }

        if (StringUtils.isBlank(plaintext) || type == null) {
            throw new IllegalArgumentException("Params is illegal。");
        }

        ReqParams reqParams = new ReqParams(appCode, version, type);
        SignSpi sign = getSign(reqParams);
        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        // 为了兼容，解密会判断是哪个版本的加密数据
        SaltPrefix saltPrefix = SaltPrefix.lookup(ciphertext.substring(0, 4));

        if (saltPrefix != SaltPrefix.NULL) {
            reqParams.version = saltPrefix.getVersion();
            ciphertext = ciphertext.substring(4, ciphertext.length());
        }

        return sign.validateSign(plaintext, ciphertext, getSecKey(reqParams));
    }

    public String getType() {
        throw new RuntimeException("Not support.");
    }

    private void validateParams(ReqParams param) {
        if (!param.validate()) {
            throw new IllegalArgumentException("Params is illegal。");
        }
    }

    /**
     * 初始化
     */

    static class InitSec {

        private static SignRepository signRepository = new SignRepository();
    }

}
