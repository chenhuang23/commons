/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.constants.EncryptType;

/**
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SignReqParams extends ReqParams {

    private static final long serialVersionUID = -893659160539262119L;
    public String             plaintext;

    public SignReqParams(String appCode, int version, EncryptType type, String plaintext){
        super(appCode, version, type);
        this.plaintext = plaintext;
    }

    public boolean validate() {
        return super.validate() && notBlank(plaintext);

    }

}
