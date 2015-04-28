/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.nqs;

import com.github.commons.mq.domain.ConsumerHandler;
import com.github.commons.mq.domain.MqConsumer;
import com.github.commons.mq.exception.MqRuntimeException;
import com.netease.cloud.nqs.client.Message;
import com.netease.cloud.nqs.client.consumer.MessageConsumer;
import com.netease.cloud.nqs.client.consumer.MessageHandler;
import com.netease.cloud.nqs.client.exception.MessageClientException;

/**
 * NqsMessageConsumer.java 消息队列消费者对象
 *
 * @author zhouxiaofeng 2/26/15
 */
public class NqsMessageConsumer implements MqConsumer {

    private volatile MessageConsumer messageConsumer = null;

    public NqsMessageConsumer(MessageConsumer messageConsumer){
        this.messageConsumer = messageConsumer;

    }

    @Override
    public void init() {

    }

    // /**
    // * @return
    // * @throws MqRuntimeException
    // */
    // public NqsMessage getMessage() {
    // assertInited();
    //
    // try {
    // Message message = messageConsumer.getMessage();
    //
    // if (message == null) {
    // return null;
    // }
    //
    // NqsMessage msg = new NqsMessage(message.getBody(), message.getTtl(), message.isPersistent());
    //
    // return msg;
    // } catch (MessageClientException e) {
    // throw new MqRuntimeException("Get nqs messages exception.", e);
    // }
    // }

    /**
     * 消费消息
     *
     * @param handler
     * @throws MqRuntimeException
     */
    public void consumeMessage(final ConsumerHandler handler) {
        assertInited();

        if (handler == null) {
            throw new IllegalArgumentException("ConsumerHandler ca'nt be null");
        }

        MessageHandler messageHandler = new MessageHandler() {

            @Override
            public boolean handle(Message message) {

                return handler.handler(message == null ? null : new NqsMessage(message.getBody(), message.getTtl(),
                                                                               message.isPersistent()));
            }
        };

        try {
            messageConsumer.consumeMessage(messageHandler);
        } catch (MessageClientException e) {
            throw new MqRuntimeException("Consumer nqs messages exception.", e);
        }
    }

    /**
     * 关闭消费者
     * 
     * @throws MqRuntimeException
     */
    public void shutdown() {

        assertInited();

        messageConsumer.shutdown();
    }

    private void assertInited() {

        if (messageConsumer != null) {

            throw new MqRuntimeException(" consumer hasn't inited.");

        }

    }

}
