/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.config.AppInfo;
import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.spi.SecKeyPolicySpi;
import com.github.commons.security.support.SecTool;

/**
 * LocalSecTool.java
 *
 * @author zhouxiaofeng 3/26/15
 */
public abstract class LocalSecTool extends SecTool {



    public static class SuperInit {

        public static SecKeyPolicyRespository secKeyPolicyRespository = new SecKeyPolicyRespository();

        protected static LocalAppManager localAppManager = new LocalAppManager();;
    }

    public LocalSecTool(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);
    }

    /**
     *
     * @param params
     * @return
     */
    public SecKey getSecKey(ReqParams params) {
        AppInfo appInfo = SuperInit.localAppManager.lookup(params);

        if (appInfo == null) {
            throw new IllegalArgumentException("App config not exists.");
        }

        SecKeyPolicySpi secKeyPolicy = SuperInit.secKeyPolicyRespository.findSecKeyPolicy(appInfo.getPolicy());

        return secKeyPolicy.findSecKey(params, appInfo.getKeys());
    }

}
