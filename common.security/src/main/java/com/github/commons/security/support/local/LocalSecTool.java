/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local;

import com.github.commons.security.ReqParams;
import com.github.commons.security.config.AppInfo;
import com.github.commons.security.config.SecKey;
import com.github.commons.security.policy.SecPolicy;
import com.github.commons.security.spi.SecKeyPolicySpi;
import com.github.commons.security.support.SecTool;

/**
 * LocalSecTool.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public abstract class LocalSecTool extends SecTool {

    // 本地模式
    protected static LocalAppManager localAppManager = null;

    public LocalSecTool(String appCode, String appKey, SecPolicy policy, int version){
        super(appCode, appKey, policy, version);
        localAppManager = new LocalAppManager();
    }

    /**
     * 获取Seckey
     *
     * @param params
     * @return
     */
    public SecKey getSecKey(ReqParams params) {
        AppInfo appInfo = localAppManager.lookup(params);

        if (appInfo == null) {
            throw new IllegalArgumentException("App config not exists.");
        }

        SecKeyPolicySpi secKeyPolicy = SuperInit.secKeyPolicyRespository.findSecKeyPolicy(appInfo.getPolicy());

        return secKeyPolicy.findSecKey(params, appInfo.getKeys());
    }
}
