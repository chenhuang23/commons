/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.message;

/**
 * MessageException.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class MessageException extends RuntimeException {

    public MessageException(){
    }

    public MessageException(Throwable cause){
        super(cause);
    }

    public MessageException(String message){
        super(message);
    }

    public MessageException(String message, Throwable cause){
        super(message, cause);
    }
}
