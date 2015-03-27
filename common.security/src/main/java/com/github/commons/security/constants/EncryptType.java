/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.constants;

/**
 * <pre>
 * EncryptType.java
 * 
 * 加密解密－类型
 * 
 * </pre>
 * 
 * @author zhouxiaofeng 3/19/15
 */
public enum EncryptType {

    DES("des"), RSA("rsa"),

    // 加密扩展
    XDES("xdes"), XRSA("xrsa");

    private String type;

    EncryptType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
