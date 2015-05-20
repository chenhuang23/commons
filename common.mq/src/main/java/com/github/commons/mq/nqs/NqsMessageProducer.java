/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.nqs;

import java.util.ArrayList;
import java.util.List;

import com.github.commons.mq.domain.MqMessage;
import com.github.commons.mq.domain.MqProducer;
import com.github.commons.mq.exception.MqRuntimeException;
import com.netease.cloud.nqs.client.Message;
import com.netease.cloud.nqs.client.exception.MessageClientException;
import com.netease.cloud.nqs.client.producer.MessageProducer;
import com.netease.cloud.nqs.client.producer.ProducerConfig;

/**
 * NqsMessageProducerFactory.java 消息队列生产者队列
 *
 * @author zhouxiaofeng 2/26/15
 */
public class NqsMessageProducer implements MqProducer {

    private volatile MessageProducer producer = null;

    public NqsMessageProducer(MessageProducer producer){

        this.producer = producer;
    }

    @Override
    public void init() {

    }

    /**
     * 发送单个信息
     *
     * @param message
     * @return
     * @throws MqRuntimeException
     */
    public boolean sendMessage(byte[] message) {
        assertInited();

        if (message == null || message.length == 0) {
            throw new IllegalArgumentException("message can't be null");
        }

        Message msg = new Message(message);

        try {
            return producer.sendMessage(msg);
        } catch (MessageClientException e) {
            throw new MqRuntimeException("Send nqs message exception.", e);
        }
    }

    /**
     * 发送单个信息
     *
     * @param message
     * @return
     * @throws MqRuntimeException
     */
    public boolean sendMessage(MqMessage message) {
        assertInited();

        if (message == null) {
            throw new IllegalArgumentException("message can't be null");
        }

        if (!(message instanceof NqsMessage)) {
            throw new IllegalArgumentException("message must NqsMessage");
        }

        try {
            return producer.sendMessage((NqsMessage) message);
        } catch (MessageClientException e) {
            throw new MqRuntimeException("Send nqs message exception.", e);
        }
    }

    // /**
    // * 发送信息列表
    // *
    // * @param list
    // * @return
    // * @throws MqRuntimeException
    // */
    // public boolean sendMessage(List<MqMessage> list) {
    // assertInited();
    //
    // if (list == null || list.isEmpty()) {
    // throw new IllegalArgumentException("message list can't be null");
    // }
    //
    // List<Message> temp = new ArrayList<Message>(list.size());
    // for (MqMessage msg : list) {
    // temp.add((Message) msg);
    // }
    //
    // try {
    // return producer.sendMessage(temp);
    // } catch (MessageClientException e) {
    // throw new MqRuntimeException("Send nqs messages exception.", e);
    // }
    // }

    /**
     * 关闭生产者
     *
     * @return
     * @throws MqRuntimeException
     */
    public void shutdown() {
        assertInited();

        producer.shutdown();
    }

    private void assertInited() {

        if (producer == null) {

            throw new MqRuntimeException(" producer hasn't inited.");

        }

    }
}
