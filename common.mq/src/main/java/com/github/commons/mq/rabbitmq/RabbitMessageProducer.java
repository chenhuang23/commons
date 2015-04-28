/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.rabbitmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.commons.mq.domain.MqMessage;
import com.github.commons.mq.domain.MqProducer;
import com.github.commons.mq.exception.MqRuntimeException;
import com.netease.cloud.nqs.client.Message;
import com.netease.cloud.nqs.client.exception.MessageClientException;
import com.netease.cloud.nqs.client.producer.MessageProducer;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NqsMessageProducerFactory.java 消息队列生产者队列
 *
 * @author zhouxiaofeng 2/26/15
 */
public class RabbitMessageProducer implements MqProducer {

    private Logger  logger = LoggerFactory.getLogger(RabbitMessageProducer.class);

    private Channel channel;
    private String  queueName;

    public RabbitMessageProducer(String queueName, Channel channel){
        this.channel = channel;
        this.queueName = queueName;
    }

    @Override
    public boolean sendMessage(byte[] message) {

        try {
            channel.basicPublish("", queueName, null, message);
        } catch (IOException e) {
            logger.error("basicPublish message exception.", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean sendMessage(MqMessage message) {
        return sendMessage(message.getMessage());
    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException e) {
                logger.error("Close channel exception.", e);
            }
        }

    }
}
