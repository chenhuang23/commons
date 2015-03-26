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

    // ǩ���㷨: type-->sign
    private static final Map<String, CodecSpi> codecMapping = new ConcurrentHashMap<String, CodecSpi>(8);

    public CodecRepository(){
        init();
    }

    /**
     * ��ʼ���ֿ�
     */
    private void init() {
        // ��ʼ��sign���㷨ʵ��

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
     * ��ȡǩ����Ϣ
     *
     * @param type ǩ���㷨����
     * @return
     */
    public CodecSpi getCodec(String type) {

        return codecMapping.get(type);

    }

    /**
     * ��ȡ֧�ֵ�sign�б�
     * 
     * @return unmodifiableList
     */
    public List<CodecSpi> findSupportCodecs() {
        return Collections.unmodifiableList(new ArrayList<CodecSpi>(codecMapping.values()));
    }

}
