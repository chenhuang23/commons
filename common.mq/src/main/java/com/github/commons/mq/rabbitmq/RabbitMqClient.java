/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang.StringUtils;

import com.github.commons.mq.domain.MqClient;
import com.github.commons.mq.domain.MqConsumer;
import com.github.commons.mq.domain.MqProducer;
import com.github.commons.mq.exception.MqRuntimeException;
import com.netease.cloud.nqs.client.ClientConfig;
import com.netease.cloud.nqs.client.SimpleMessageSessionFactory;
import com.netease.cloud.nqs.client.consumer.ConsumerConfig;
import com.netease.cloud.nqs.client.exception.MessageClientException;
import com.netease.cloud.nqs.client.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NqMqClient.java
 *
 * 暂支持 Work queues 模式  publish/subscribe后面补充
 *
 * @author zhouxiaofeng 4/24/15
 */
public class RabbitMqClient implements MqClient {

    private static Logger          logger         = LoggerFactory.getLogger(RabbitMqClient.class);

    private Connection             connection     = null;
    private Channel                channel        = null;

    private String                 host           = null;
    private int                    port           = 5672;
    private int                    connectTimeout = 3000;
    private String                 userName;
    private String                 password;

    private volatile AtomicBoolean inited         = new AtomicBoolean(false);

    @Override
    public void init() {

        if (!inited.get()) {
            synchronized (inited) {
                if (inited.compareAndSet(false, true)) {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost(host);
                    factory.setPort(port);
                    factory.setConnectionTimeout(connectTimeout);

                    if (StringUtils.isNotBlank(userName)) {
                        factory.setUsername(userName);
                    }
                    if (StringUtils.isNotBlank(password)) {
                        factory.setPassword(password);
                    }

                    Connection connection = null;
                    try {
                        connection = factory.newConnection();
                        channel = connection.createChannel();
                    } catch (IOException e) {
                        logger.error("init client exception.", e);
                        throw new MqRuntimeException("Init rabbitmq client exception.", e);
                    }
                }
            }
        }
    }

    @Override
    public MqConsumer createMqConsumer(String queueName, String group) {

        if (channel != null) {
            if (StringUtils.isBlank(queueName)) {
                throw new IllegalArgumentException("queueName ca'nt be null");
            }

            QueueingConsumer consumer = null;
            try {
                consumer = new QueueingConsumer(channel);
                channel.basicConsume(queueName, false, consumer);
            } catch (Throwable e) {
                throw new MqRuntimeException("Create nqs consumer exception.", e);
            }

            return new RabbitMessageConsumer(consumer, channel);
        }
        return null;
    }

    @Override
    public MqProducer createMqProducer(String queueName) {

        if (channel != null) {
            return new RabbitMessageProducer(queueName, channel);
        }

        return null;
    }

    @Override
    public void shutdown() {

        try {
            if (channel != null) {
                channel.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Throwable e) {
            logger.error("shutdown exception.", e);

        }
    }

    // ==========

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
