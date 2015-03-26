/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.policy;

import com.github.commons.security.ReqParams;
import com.github.commons.security.config.SecKey;
import com.github.commons.security.constants.Constants;
import com.github.commons.security.spi.SecKeyPolicySpi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * VersionSecKeyPolicy.java 
 * 
 * 按版本号区分可以
 * 
 * </pre>
 * 
 * @author zhouxiaofeng 3/19/15
 */
public class VersionSecKeyPolicy implements SecKeyPolicySpi {

    private Map<SecKey[], Map<String, SecKey>> map = new ConcurrentHashMap<SecKey[], Map<String, SecKey>>();

    @Override
    public String getName() {
        return Constants.VERSION_POLICY;
    }

    /**
     * 通过secType + version 来查找秘钥
     * 
     * @param params
     * @param keys
     * @return
     */
    @Override
    public SecKey findSecKey(ReqParams params, SecKey[] keys) {

        if (map.containsKey(keys)) {
            if (map.get(keys).containsKey(params.secType.getType() + params.version)) {
                return map.get(keys).get(params.secType.getType() + params.version);
            }
        } else {
            map.put(keys, new HashMap<String, SecKey>());
        }

        for (SecKey key : keys) {

            if (key.getVersion() == params.version) {
                map.get(keys).put((params.secType.getType() + params.version), key);
                return key;
            }
        }

        return null;
    }
}
