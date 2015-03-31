/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * EncryptType.java
 * 
 *
 * </pre>
 * 
 * @author zhouxiaofeng 3/19/15
 */
public enum EncryptType {

    DES(1, "des"), RSA(2, "rsa"), AES(3, "aes"),

    XDES(10, "xdes"), XRSA(11, "xrsa"),

    // null
    NULL(-1, "");

    private String type;
    private int    code;

    EncryptType(int code, String type){
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public static EncryptType lookup(int code) {
        encryptTypeMap.get(code);

        return NULL;
    }

    private static Map<Integer, EncryptType> encryptTypeMap;

    static {

        EncryptType[] encryptTypes = EncryptType.values();
        encryptTypeMap = new HashMap<Integer, EncryptType>(encryptTypes.length);

        for (EncryptType encryptType : encryptTypes) {
            encryptTypeMap.put(encryptType.getCode(), encryptType);
        }
    }
}
