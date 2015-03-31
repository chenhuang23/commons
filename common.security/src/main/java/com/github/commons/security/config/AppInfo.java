/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.config;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <pre>
 * AppInfo.java
 * 
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public class AppInfo implements Serializable {

    private static final long serialVersionUID = -162985189865718994L;

    private String            appCode;

    private int               lastVersion      = 1;

    private String            policy;

    private SecKey[]          keys;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public SecKey[] getKeys() {
        return keys;
    }

    public void setKeys(SecKey[] keys) {
        this.keys = keys;
    }

    public int getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(int lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @Override
    public String toString() {
        return "AppInfo{" + "appCode='" + appCode + '\'' + ", lastVersion=" + lastVersion + ", policy='" + policy
               + '\'' + ", keys=" + Arrays.toString(keys) + '}';
    }

}
