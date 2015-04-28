/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.domain;

/**
 * MqProducer.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public interface MqProducer extends Shutdownable, Initializer {

    /**
     * 发送单个信息
     *
     * @param message
     * @return
     * @throws com.github.commons.mq.exception.MqRuntimeException
     */
    public boolean sendMessage(byte[] message);

    /**
     * 发送单个信息
     *
     * @param message
     * @return
     * @throws com.github.commons.mq.exception.MqRuntimeException
     */
    public boolean sendMessage(MqMessage message);

}
