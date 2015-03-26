/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

import com.github.commons.security.ReqParams;
import com.github.commons.security.config.SecKey;
import com.github.commons.security.policy.SecPolicy;

/**
 * SecTools.java
 *
 * @author zhouxiaofeng 3/19/15
 */

public abstract class SecTool {

    public static class SuperInit {

        // sec key ������
        public static SecKeyPolicyRespository secKeyPolicyRespository = new SecKeyPolicyRespository();

    }

    /**
     * ����ģʽ
     */
    protected SecPolicy policy;
    protected String    appKey;
    protected String    appCode;
    protected int       version = 1;

    public SecTool(String appCode, String appKey, SecPolicy policy, int version){
        this.appCode = appCode;
        this.appKey = appKey;
        this.policy = policy;
        this.version = version;
    }

    /**
     * ͨ�����������ȡkey
     * 
     * @param params
     * @return
     */
    public abstract SecKey getSecKey(ReqParams params);

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
