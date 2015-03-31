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

    private static final Map<Integer, CodecSpi> codecMapping = new ConcurrentHashMap<Integer, CodecSpi>(8);

    public CodecRepository(){
        init();
    }

    /**
     */
    private void init() {

        Iterator<CodecSpi> iterator = SpiLoader.load(CodecSpi.class);
        if (iterator != null) {
            while (iterator.hasNext()) {
                CodecSpi codecSpi = iterator.next();
                if (codecSpi != null) {
                    codecMapping.put(codecSpi.getCode(), codecSpi);
                }
            }
        }

    }

    /**
     *
     * @return
     */
    public CodecSpi getCodec(int type) {

        return codecMapping.get(type);

    }

    /**
     *
     * @return unmodifiableList
     */
    public List<CodecSpi> findSupportCodecs() {
        return Collections.unmodifiableList(new ArrayList<CodecSpi>(codecMapping.values()));
    }

}
