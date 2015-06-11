/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.constants.SecPolicy;

/**
 * SecTools.java
 *
 * @author zhouxiaofeng 3/19/15
 */

public abstract class SecTool {

    /**
     */
    protected SecPolicy policy;
    protected String    appCode;
    protected int       version = 1;

    public SecTool(String appCode, SecPolicy policy, int version){
        this.appCode = appCode;
        this.policy = policy;
        this.version = version;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public SecPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SecPolicy policy) {
        this.policy = policy;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
