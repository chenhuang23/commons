/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.rabbitmq;

import com.github.commons.mq.domain.MqMessage;

/**
 * NqsMessage.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public class RabbitMessage implements MqMessage {

    private byte[] data;

    public RabbitMessage(byte[] body){

        this.data = body;
    }

    @Override
    public int getTtl() {
        return 0;
    }

    @Override
    public byte[] getMessage() {
        return data;
    }
}
