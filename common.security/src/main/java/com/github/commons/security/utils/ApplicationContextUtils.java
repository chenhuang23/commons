/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.utils;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContextUtils.java
 *
 * @author zhouxiaofeng 3/30/15
 */
public class ApplicationContextUtils {

    private static ApplicationContext applicationContext;

    public static void init(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
