/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.domain;

/**
 * MqConsumer.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public interface MqConsumer extends Shutdownable, Initializer {

    /**
     * 消费消息
     *
     * @param handler
     * @throws com.github.commons.mq.exception.MqRuntimeException
     */
    public void consumeMessage(final ConsumerHandler handler);

}
