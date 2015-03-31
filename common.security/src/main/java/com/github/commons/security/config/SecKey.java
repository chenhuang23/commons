/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.config;

import java.io.Serializable;

/**
 * SecKey.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SecKey implements Serializable {

    private String type;

    private int    version;

    private String pubKey;

    private String priKey;

    public int getVersion() {
        return this.version;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getType() {
        return type;
    }
}
