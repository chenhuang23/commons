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
 * ǩ���ֿ�,ǩ�������漰��ʹ���ߡ��汾��ǩ�����͡�ʱ��ε�γ��
 * </pre>
 *
 * @author zhouxiaofeng 3/17/15
 */
public class SignRepository {

    // ǩ���㷨: type-->sign
    private static final Map<String, SignSpi> signMapping = new ConcurrentHashMap<String, SignSpi>(8);

    public SignRepository(){
        init();
    }

    /**
     * ��ʼ���ֿ�
     */
    private void init() {
        // ��ʼ��sign���㷨ʵ��

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
     * ��ȡǩ����Ϣ
     *
     * @param type ǩ���㷨����
     * @return
     */
    public SignSpi getSign(String type) {

        return signMapping.get(type);

    }

    /**
     * ��ȡ֧�ֵ�sign�б�
     * 
     * @return unmodifiableList
     */
    public List<SignSpi> findSupportSigns() {
        return Collections.unmodifiableList(new ArrayList<SignSpi>(signMapping.values()));
    }

}
