/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.nqs;

import com.github.commons.mq.domain.MqMessage;
import com.netease.cloud.nqs.client.Message;

/**
 * NqsMessage.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public class NqsMessage extends Message implements MqMessage {

    public NqsMessage(byte[] body){
        super(body);
    }

    public NqsMessage(byte[] body, int ttl){
        super(body, ttl);
    }

    public NqsMessage(byte[] body, boolean persistent){
        super(body, persistent);
    }

    public NqsMessage(byte[] body, int ttl, boolean persistent){
        super(body, ttl, persistent);
    }

    @Override
    public byte[] getMessage() {
        return super.getBody();
    }

}
