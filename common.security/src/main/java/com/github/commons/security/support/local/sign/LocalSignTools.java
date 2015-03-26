/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.sign;

import org.apache.commons.lang.StringUtils;

import com.github.commons.security.ReqParams;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.support.SignReqParams;
import com.github.commons.security.support.SignTool;
import com.github.commons.security.support.ValidateSignReqParams;
import com.github.commons.security.policy.SecPolicy;
import com.github.commons.security.spi.SignSpi;
import com.github.commons.security.support.local.LocalSecTool;

/**
 * SecurityTools.java ����ǩ������
 *
 * @author zhouxiaofeng 3/17/15
 */
public class LocalSignTools extends LocalSecTool implements SignTool {

    public LocalSignTools(String appCode, String appKey, SecPolicy policy, int version){
        super(appCode, appKey, policy, version);
    }

    /**
     * ��ȡǩ��
     *
     * @param params
     * @return
     */
    private SignSpi getSign(ReqParams params) {

        return InitSec.signRepository.getSign(params.secType.getType());
    }

    /**
     * ǩ������
     *
     * @param params
     * @return
     */
    public String sign(SignReqParams params) {

        // �������
        CompParams(params);

        validateParams(params);

        SignSpi sign = getSign(params);
        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        return sign.sign(params.plaintext, getSecKey(params));
    }

    private void CompParams(SignReqParams params) {
        if (StringUtils.isBlank(params.appCode)) {
            params.appCode = this.appCode;
        }

        if (StringUtils.isBlank(params.appKey)) {
            params.appKey = this.appKey;
        }

        if (params.version <= 0) {
            params.version = this.version;
        }
    }

    /**
     * ǩ������
     *
     * @param plaintext
     * @param type
     * @return
     */
    public String sign(String plaintext, EncryptType type) {
        validateSignParams(plaintext, type);

        ReqParams reqParams = new ReqParams(appCode, appKey, version, type);
        SignSpi sign = getSign(reqParams);

        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        return sign.sign(plaintext, getSecKey(reqParams));
    }

    private void validateSignParams(String plaintext, EncryptType type) {
        if (StringUtils.isBlank(appCode) || StringUtils.isBlank(appKey)) {
            throw new IllegalArgumentException("appCode can't be blank.");
        }

        if (StringUtils.isBlank(appKey)) {
            throw new IllegalArgumentException("appKey can't be blank.");
        }

        if (StringUtils.isBlank(plaintext)) {
            throw new IllegalArgumentException("plaintext can't be blank.");
        }

        if (type == null) {
            throw new IllegalArgumentException("EncryptType can't be null.");
        }
    }

    /**
     * ��֤ǩ��
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

        return sign.validateSign(params.plaintext, params.ciphertext, getSecKey(params));
    }

    /**
     * ��֤ǩ��
     *
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, EncryptType type) {
        if (StringUtils.isBlank(appCode) || StringUtils.isBlank(appKey)) {
            throw new IllegalArgumentException("Params is illegal��");
        }

        if (StringUtils.isBlank(plaintext) || type == null) {
            throw new IllegalArgumentException("Params is illegal��");
        }

        ReqParams reqParams = new ReqParams(appCode, appKey, version, type);
        SignSpi sign = getSign(reqParams);
        if (sign == null) {
            throw new IllegalArgumentException("Sign not exist for this params.");
        }

        return sign.validateSign(plaintext, ciphertext, getSecKey(reqParams));
    }

    public String getType() {
        throw new RuntimeException("Not support.");
    }

    private void validateParams(ReqParams param) {
        if (!param.validate()) {
            throw new IllegalArgumentException("Params is illegal��");
        }
    }

    /**
     * ��ʼ��
     */

    static class InitSec {

        private static SignRepository signRepository = new SignRepository();
    }

}
