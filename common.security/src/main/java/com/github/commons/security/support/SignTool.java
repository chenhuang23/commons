/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.constants.EncryptType;

/**
 * SignTool.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public interface SignTool {

    /**
     * 签名操作
     *
     * @param params
     * @return
     */
    public String sign(SignReqParams params);

    /**
     * 签名操作
     *
     * @param plaintext
     * @param type
     * @return
     */
    public String sign(String plaintext, EncryptType type);

    /**
     * 验证签名
     *
     * @param params
     * @return
     */
    public boolean validateSign(ValidateSignReqParams params);

    /**
     * 验证签名
     *
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, EncryptType type);

}
