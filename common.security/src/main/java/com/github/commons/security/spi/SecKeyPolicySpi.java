/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.ReqParams;
import com.github.commons.security.config.SecKey;

/**
 * <pre>
 * SecKeyPolicy.java
 * 
 * ��ȡkey �Ĳ���
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface SecKeyPolicySpi {

    /**
     * ��ȡ��������
     * 
     * @return
     */
    public String getName();

    /**
     * ���ݲ�ͬ���Ի�ȡ��Ӧ��secKey
     * 
     * @param params
     * @param keys
     * @return
     */
    public SecKey findSecKey(ReqParams params, SecKey[] keys);

}
