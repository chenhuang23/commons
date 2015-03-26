/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local.codec;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.github.commons.security.spi.CodecSpi;
import com.github.commons.utils.spi.SpiLoader;

/**
 * <pre>
 * CodecRepository.java
 * 
 * 
 * </pre>
 *
 * @author zhouxiaofeng
 */
public class CodecRepository {

    // 签名算法: type-->sign
    private static final Map<String, CodecSpi> codecMapping = new ConcurrentHashMap<String, CodecSpi>(8);

    public CodecRepository(){
        init();
    }

    /**
     * 初始化仓库
     */
    private void init() {
        // 初始化sign的算法实例

        Iterator<CodecSpi> iterator = SpiLoader.load(CodecSpi.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                CodecSpi codecSpi = iterator.next();
                if (codecSpi != null) {
                    codecMapping.put(codecSpi.getType(), codecSpi);
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
    public CodecSpi getCodec(String type) {

        return codecMapping.get(type);

    }

    /**
     * 获取支持的sign列表
     * 
     * @return unmodifiableList
     */
    public List<CodecSpi> findSupportCodecs() {
        return Collections.unmodifiableList(new ArrayList<CodecSpi>(codecMapping.values()));
    }

}
