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
 *  ǩ����֤
 *
 * @author zhouxiaofeng 3/17/15
 */
public interface SignSpi {

    /**
     * ��ĳ������ǩ��
     * 
     * @param plaintext
     * @return
     */
    public String sign(String plaintext, SecKey key);

    /**
     * ��֤ĳ��ǩ���Ƿ���ȷ
     * 
     * @param plaintext
     * @param ciphertext
     * @return
     */
    public boolean validateSign(String plaintext, String ciphertext, SecKey key);

    /**
     * ��ȡǩ������
     * 
     * @return
     */
    public String getType();
}
