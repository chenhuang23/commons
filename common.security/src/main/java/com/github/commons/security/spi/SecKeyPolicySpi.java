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
 * 获取key 的策略
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface SecKeyPolicySpi {

    /**
     * 获取策略名称
     * 
     * @return
     */
    public String getName();

    /**
     * 根据不同策略获取对应的secKey
     * 
     * @param params
     * @param keys
     * @return
     */
    public SecKey findSecKey(ReqParams params, SecKey[] keys);

}
