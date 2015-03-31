/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.utils.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SpiLoader.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class SpiLoader {

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Iterator<T> load(Class<T> clazz) {
        ServiceLoader<T> appConfigurationLoader = ServiceLoader.load(clazz);
        return appConfigurationLoader != null ? appConfigurationLoader.iterator() : null;
    }

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T loadFirst(Class<T> clazz) {
        Iterator<T> iterator = load(clazz);
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }
}
