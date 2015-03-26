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
 * 应用信息
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public class AppInfo implements Serializable {

    private static final long serialVersionUID = -162985189865718994L;

    // 应用Id
    private String            appCode;

    // 应用密码
    private String            appKey;

    // 最新版本号
    private int               lastVersion      = 1;

    // 获取key的策略
    private String            policy;

    private SecKey[]          keys;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
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
        return "AppInfo{" + "appCode='" + appCode + '\'' + ", appKey='" + appKey + '\'' + ", lastVersion="
               + lastVersion + ", policy='" + policy + '\'' + ", keys=" + Arrays.toString(keys) + '}';
    }

}
