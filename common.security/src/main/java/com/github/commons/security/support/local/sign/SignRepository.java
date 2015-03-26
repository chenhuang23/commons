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
 * 签名仓库,签名服务涉及到使用者、版本、签名类型、时间段等纬度
 * </pre>
 *
 * @author zhouxiaofeng 3/17/15
 */
public class SignRepository {

    // 签名算法: type-->sign
    private static final Map<String, SignSpi> signMapping = new ConcurrentHashMap<String, SignSpi>(8);

    public SignRepository(){
        init();
    }

    /**
     * 初始化仓库
     */
    private void init() {
        // 初始化sign的算法实例

        Iterator<SignSpi> iterator = SpiLoader.load(SignSpi.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                SignSpi signSpi = iterator.next();
                if (signSpi != null) {
                    signMapping.put(signSpi.getType(), signSpi);
                }
            }
        }

    }

    /**
     * 获取签名信息
     *
     * @param type 签名算法名称
     * @return
     */
    public SignSpi getSign(String type) {

        return signMapping.get(type);

    }

    /**
     * 获取支持的sign列表
     * 
     * @return unmodifiableList
     */
    public List<SignSpi> findSupportSigns() {
        return Collections.unmodifiableList(new ArrayList<SignSpi>(signMapping.values()));
    }

}
