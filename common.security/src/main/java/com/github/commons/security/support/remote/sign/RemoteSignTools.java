/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.remote.sign;

import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.support.*;

/**
 * RemoteCodecTools.java
 *
 * @author zhouxiaofeng 3/29/15
 */
public class RemoteSignTools extends SecTool implements SignTool {

    public RemoteSignTools(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);
    }

    @Override
    public String sign(String plaintext, EncryptType type) {
        return null;
    }

    @Override
    public boolean validateSign(String plaintext, String ciphertext, EncryptType type) {
        return false;
    }
}
