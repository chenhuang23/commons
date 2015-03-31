/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.github.commons.security.spi.SecKeyPolicySpi;
import com.github.commons.utils.spi.SpiLoader;

/**
 * SecKeyPolicyRespository.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SecKeyPolicyRespository {

    private Map<String, SecKeyPolicySpi> map = new HashMap<String, SecKeyPolicySpi>();

    public SecKeyPolicyRespository(){
        init();
    }

    private void init() {


        Iterator<SecKeyPolicySpi> iterator = SpiLoader.load(SecKeyPolicySpi.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                SecKeyPolicySpi secKeyPolicySpi = iterator.next();
                if (secKeyPolicySpi != null) {
                    map.put(secKeyPolicySpi.getName(), secKeyPolicySpi);
                }
            }
        }

    }

    /**
     *
     * @return
     */
    public SecKeyPolicySpi findSecKeyPolicy(String name) {
        return map.get(name);
    }
}
