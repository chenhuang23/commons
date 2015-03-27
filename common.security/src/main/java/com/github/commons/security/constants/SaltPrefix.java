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
 * SaltPrefix.java
 *
 * @author zhouxiaofeng 3/27/15
 */
public enum SaltPrefix {
    V1(1, "WQEP"),

    NULL(-1, "");

    int    version;
    String salt;

    SaltPrefix(int version, String salt){
        this.salt = salt;
        this.version = version;
    }

    public String getSalt() {
        return salt;
    }

    public int getVersion() {
        return version;
    }

    public static SaltPrefix lookup(int version) {
        return saltMap.get(version);
    }

    public static SaltPrefix lookup(String salt) {
        for (SaltPrefix prefix : saltMap.values()) {

            if (StringUtils.equals(prefix.getSalt(), salt)) {
                return prefix;
            }
        }

        return NULL;
    }

    private static Map<Integer, SaltPrefix> saltMap;

    static {

        SaltPrefix[] saltPrefixes = SaltPrefix.values();
        saltMap = new HashMap<Integer, SaltPrefix>(saltPrefixes.length);

        for (SaltPrefix prefix : saltPrefixes) {
            saltMap.put(prefix.getVersion(), prefix);
        }
    }
}
