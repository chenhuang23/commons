/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.nqs;

import java.util.concurrent.atomic.AtomicBoolean;

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

/**
 * NqMqClient.java
 *
 * @author zhouxiaofeng 4/24/15
 */
public class NqMqClient implements MqClient {

    private SimpleMessageSessionFactory simpleMessageSessionFactory;

    private String                      host             = null;
    private int                         port             = 5672;
    private String                      productId        = null;
    private String                      accessKey        = null;
    private String                      accessSecret     = null;
    private int                         heartbeatTimeout = 600;
    private int                         idleTimeout      = 60;

    private String                      authMechanism    = "cloud";
    private final static String         DEF_G_NAME       = "consumer_group";

    private volatile AtomicBoolean      inited           = new AtomicBoolean(false);

    @Override
    public void init() {

        if (!inited.get()) {
            synchronized (inited) {
                if (inited.compareAndSet(false, true)) {
                    ClientConfig clientConfig = new ClientConfig();

                    clientConfig.setHost(host);
                    clientConfig.setPort(port);
                    clientConfig.setProductId(productId);
                    clientConfig.setAccessKey(accessKey);
                    clientConfig.setAccessSecret(accessSecret);
                    clientConfig.setHeartbeatTimeout(heartbeatTimeout);
                    clientConfig.setIdleTimeout(idleTimeout);
                    clientConfig.setAuthMechanism(authMechanism);

                    simpleMessageSessionFactory = new SimpleMessageSessionFactory(clientConfig);
                }
            }
        }
    }

    @Override
    public MqConsumer createMqConsumer(String queueName, String group) {

        if (StringUtils.isBlank(queueName)) {
            throw new IllegalArgumentException("queueName ca'nt be null");
        }

        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setProductId(productId);
        consumerConfig.setQueueName(queueName);
        consumerConfig.setGroup(StringUtils.isEmpty(group) ? DEF_G_NAME : group);

        try {
            return new NqsMessageConsumer(simpleMessageSessionFactory.createConsumer(consumerConfig));
        } catch (MessageClientException e) {
            throw new MqRuntimeException("Create nqs consumer exception.", e);
        }

    }

    @Override
    public MqProducer createMqProducer(String queueName) {

        if (simpleMessageSessionFactory == null) {
            ProducerConfig producerConfig = new ProducerConfig();
            producerConfig.setProductId(productId);
            producerConfig.setQueueName(queueName);

            try {
                return new NqsMessageProducer(simpleMessageSessionFactory.createProducer(producerConfig));
            } catch (MessageClientException e) {
                throw new MqRuntimeException("Create nqs procucer exception.", e);
            }
        }

        return null;
    }

    @Override
    public void shutdown() {

        if (simpleMessageSessionFactory != null) {
            simpleMessageSessionFactory.shutdown();
        }
    }

    // ==========

    // =================
    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public void setHeartbeatTimeout(int heartbeatTimeout) {
        this.heartbeatTimeout = heartbeatTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setAuthMechanism(String authMechanism) {
        this.authMechanism = authMechanism;
    }

    //

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getProductId() {
        return productId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public int getHeartbeatTimeout() {
        return heartbeatTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public String getAuthMechanism() {
        return authMechanism;
    }

}
