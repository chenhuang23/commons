/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.config.SecKey;

/**
 * <pre>
 * SecKeyPolicy.java
 * 
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface SecKeyPolicySpi {

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @param params
     * @param keys
     * @return
     */
    public SecKey findSecKey(ReqParams params, SecKey[] keys);

}
