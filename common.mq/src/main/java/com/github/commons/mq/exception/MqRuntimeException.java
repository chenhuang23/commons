/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.mq.exception;

/**
 * NqsMessageException.java
 *
 *  MessageQueue 运行时异常
 *
 * @author zhouxiaofeng 2/26/15
 */
public class MqRuntimeException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public MqRuntimeException(){
    }

    public MqRuntimeException(String message){
        super(message);
    }

    public MqRuntimeException(String message, Throwable cause){
        super(message, cause);
    }

    public MqRuntimeException(Throwable cause){
        super(cause);
    }

}
