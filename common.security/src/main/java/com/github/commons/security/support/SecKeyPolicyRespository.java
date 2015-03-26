/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support;

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

        // 初始化sign的算法实例

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
     * 算法名称
     * 
     * @param name 策略类型
     * @return
     */
    public SecKeyPolicySpi findSecKeyPolicy(String name) {
        return map.get(name);
    }
}
