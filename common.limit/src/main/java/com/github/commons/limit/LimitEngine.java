/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit;

import java.util.HashMap;
import java.util.Map;

/**
 * 限制器引擎,提供Mbean方式获取
 * 
 * @author zhouxiaofeng 4/29/15
 */
public class LimitEngine {

    private boolean                       on                   = true;

    private Map<String, Map<String, Spy>> classLimitRepository = new HashMap<String, Map<String, Spy>>();

    public void setOn(boolean switchon) {
        this.on = switchon;
    }

    public boolean isOn() {
        return on;
    }

    public void add(String className, Map<String, Spy> spyMap) {
        classLimitRepository.put(className, spyMap);
    }

    public Map<String, Map<String, Spy>> getClassLimitRepository() {
        return classLimitRepository;
    }

}
