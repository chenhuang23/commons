/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.sign;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.github.commons.security.spi.SignSpi;
import com.github.commons.utils.spi.SpiLoader;

/**
 * <pre>
 * SignRepository.java
 * 
 * </pre>
 *
 * @author zhouxiaofeng 3/17/15
 */
public class SignRepository {

    private static final Map<Integer, SignSpi> signMapping = new ConcurrentHashMap<Integer, SignSpi>(8);

    public SignRepository(){
        init();
    }

    /**
     */
    private void init() {

        Iterator<SignSpi> iterator = SpiLoader.load(SignSpi.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                SignSpi signSpi = iterator.next();
                if (signSpi != null) {
                    signMapping.put(signSpi.getCode(), signSpi);
                }
            }
        }

    }

    /**
     *
     * @return
     */
    public SignSpi getSign(int type) {

        return signMapping.get(type);

    }

    /**
     *
     * @return unmodifiableList
     */
    public List<SignSpi> findSupportSigns() {
        return Collections.unmodifiableList(new ArrayList<SignSpi>(signMapping.values()));
    }

}
