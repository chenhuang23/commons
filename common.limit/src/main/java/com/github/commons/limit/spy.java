/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit;

import com.github.commons.limit.handler.ThresholdHandler;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 针对每个方法的监听服务
 *
 * @author zhouxiaofeng 4/29/15
 */
public class Spy implements ThresholdHandler {

    private int              threshold;
    private ThresholdHandler handler;

    private String           className;

    private AtomicInteger    count = new AtomicInteger(0);

    public Spy(String className, ThresholdHandler handler, int threshold){
        this.className = className;
        this.handler = handler;
        this.threshold = threshold;
    }

    /**
     * 
     */
    public boolean entry(MethodInvocation methodInvocation) {

        if (count.incrementAndGet() <= threshold) {
            return true;
        }

        handler.handler(methodInvocation);

        return false;
    }

    /**
     * 
     */
    public void release() {
        count.decrementAndGet();
    }

    @Override
    public void handler(MethodInvocation methodInvocation) {
        handler.handler(methodInvocation);
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
