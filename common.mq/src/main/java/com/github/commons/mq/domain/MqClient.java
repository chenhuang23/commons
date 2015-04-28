/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.domain;

/**
 * MqClient.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public interface MqClient extends Shutdownable, Initializer {

    /**
     * 创建消费端
     * 
     * @return
     */
    public MqConsumer createMqConsumer(String queueName, String group);

    /**
     * 创建生产端
     * 
     * @return
     */
    public MqProducer createMqProducer(String queueName);

}
