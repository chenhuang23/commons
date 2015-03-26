/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.config.SecKey;

/**
 * Sign.java
 *
 *  签名认证
 *
 * @author zhouxiaofeng 3/17/15
 */
public interface SignSpi {

    /**
     * 对某端内容签名
     * 
     * @param plaintext
     * @return
     */
    public String sign(String plaintext, SecKey key);

    /**
     * 验证某段签名是否正确
     * 
     * @param plaintext
     * @param ciphertext
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, SecKey key);

    /**
     * 获取签名类型
     * 
     * @return
     */
    public String getType();
}
