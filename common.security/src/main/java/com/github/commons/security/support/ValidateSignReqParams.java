/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.constants.EncryptType;

/**
 * ValidateSignReqParams.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class ValidateSignReqParams extends ReqParams {

    private static final long serialVersionUID = -7649290941106381320L;

    public String             plaintext;
    public String             ciphertext;

    public ValidateSignReqParams(String appCode, int version, EncryptType type, String plaintext, String ciphertext){
        super(appCode, version, type);
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
    }

    public boolean validate() {
        return super.validate() && notBlank(plaintext) && notBlank(ciphertext);

    }
}
