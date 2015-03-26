/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.policy;

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
}
