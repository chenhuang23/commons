/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit.handler;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <pre>
 *  ThresholdHandler.java
 * 
 *  阀值处理器
 * 
 * </pre>
 * 
 * @author zhouxiaofeng 4/29/15
 */
public interface ThresholdHandler {

    /**
     * 处理当前的调用,这里对各种异常都不会处理
     * 
     * @param methodInvocation 当前调用的方法
     */
    public void handler(MethodInvocation methodInvocation);

}
