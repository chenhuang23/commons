/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.rabbitmq;

import com.github.commons.mq.domain.ConsumerHandler;
import com.github.commons.mq.domain.MqConsumer;
import com.github.commons.mq.exception.MqRuntimeException;
import com.github.commons.mq.nqs.NqsMessage;
import com.netease.cloud.nqs.client.Message;
import com.netease.cloud.nqs.client.consumer.MessageConsumer;
import com.netease.cloud.nqs.client.consumer.MessageHandler;
import com.netease.cloud.nqs.client.exception.MessageClientException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * NqsMessageConsumer.java 消息队列消费者对象
 *
 * @author zhouxiaofeng 2/26/15
 */
public class RabbitMessageConsumer implements MqConsumer {

    private Logger           logger = LoggerFactory.getLogger(RabbitMessageConsumer.class);

    private Channel          channel;
    private QueueingConsumer consumer;

    public RabbitMessageConsumer(QueueingConsumer consumer, Channel channel){
        this.channel = channel;
        this.consumer = consumer;
    }

    @Override
    public void consumeMessage(ConsumerHandler handler) {

        while (true) {
            QueueingConsumer.Delivery delivery = null;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException e) {
                logger.error("Deal message exception.", e);

                continue;
            }

            if (handler.handler(new RabbitMessage(delivery.getBody()))) {

                final QueueingConsumer.Delivery innerDelivery = delivery;

                new Retry() {

                    public boolean deal(int currentTime) {
                        try {
                            channel.basicAck(innerDelivery.getEnvelope().getDeliveryTag(), false);
                        } catch (IOException e) {
                            logger.error("ack message exception.-->" + currentTime, e);
                            return false;
                        }

                        return true;
                    }

                }.retry(3);
            }
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void shutdown() {

    }

    abstract class Retry {

        public void retry(final int times) {

            int retryTime = times;

            while (retryTime-- > 0) {
                if (deal(retryTime)) {
                    return;
                }
            }
        }

        public abstract boolean deal(int currentTime);
    }
}