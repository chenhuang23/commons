/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit.handler;

import com.github.commons.limit.exception.LimitException;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogThresholdHandler.java
 *
 * @author zhouxiaofeng 4/30/15
 */
public class LogThresholdHandler implements ThresholdHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogThresholdHandler.class);

    private String              className;

    @Override
    public void handler(MethodInvocation methodInvocation) throws LimitException {

        logger.error("Method is exceed limit. {}-{}", className, methodInvocation.getMethod());

        throw new LimitException(className + " - " + methodInvocation.getMethod() + "exceed limit");

    }

    @Override
    public void setClassName(String name) {
        this.className = name;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
