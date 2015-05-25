/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit.exception;

/**
 * LimitException.java
 *
 * 黑名单限制
 *
 * @author zhouxiaofeng 4/30/15
 */
public class BlackListLimitException extends RuntimeException {
    public BlackListLimitException() {
        super();
    }

    public BlackListLimitException(String message) {
        super(message);
    }

    public BlackListLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlackListLimitException(Throwable cause) {
        super(cause);
    }

    protected BlackListLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
