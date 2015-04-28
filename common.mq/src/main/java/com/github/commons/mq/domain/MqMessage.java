/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.domain;

/**
 * CacheMessage.java
 *
 * @author zhouxiaofeng 2/26/15
 */
public interface MqMessage {

    int getTtl();

    byte[] getMessage();

}
