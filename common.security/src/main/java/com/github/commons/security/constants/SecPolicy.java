/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.constants;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Policy.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public enum SecPolicy {
    LOCAL("local"), REMOTE("remote");

    private String policy;

    SecPolicy(String policy){
        this.policy = policy;
    }

    public String getPolicy() {
        return policy;
    }

    public static SecPolicy lookup(String policy) {
        return saltMap.get(policy);
    }

    private static Map<String, SecPolicy> saltMap;

    static {

        SecPolicy[] secPolicies = SecPolicy.values();
        saltMap = new HashMap<String, SecPolicy>(secPolicies.length);

        for (SecPolicy policy : secPolicies) {
            saltMap.put(policy.getPolicy(), policy);
        }
    }
}
