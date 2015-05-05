/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit;

import com.github.commons.limit.handler.ThresholdHandler;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.env.Environment;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 针对每个方法的监听服务
 *
 * @author zhouxiaofeng 4/29/15
 */
public class Spy implements ThresholdHandler {

    private ThresholdSynchronizer thresholdSynchronizer;
    private ThresholdHandler      handler;

    private String                className;

    private AtomicInteger         count = new AtomicInteger(0);

    public Spy(String className, ThresholdHandler handler, ThresholdSynchronizer thresholdSynchronizer){
        this.className = className;
        this.handler = handler;
        this.thresholdSynchronizer = thresholdSynchronizer;
    }

    /**
     * 
     */
    public boolean entry(MethodInvocation methodInvocation) {

        if (count.incrementAndGet() <= thresholdSynchronizer.fetch()) {
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

    public int currentEntrants() {
        return count.get();
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

    /**
     * 阀值同步器
     */
    public static class ThresholdSynchronizer {

        long        interval;
        String      thresholdKey;
        Environment environment;
        int         defaultThreshold;
        long        lastAccess;
        int         threshold;

        public ThresholdSynchronizer(Environment environment, int defaultThreshold, String thresholdKey, long interval){
            this.defaultThreshold = defaultThreshold;
            this.environment = environment;
            this.interval = interval;
            this.thresholdKey = thresholdKey;
            // 默认的阀值
            threshold = defaultThreshold;
        }

        public int fetch() {

            if (lastAccess < System.currentTimeMillis() - interval) {
                // 阀值参数key
                if (thresholdKey != null && !"".equals(thresholdKey)) {
                    // 阀值配置
                    String thresholdStr = environment.getProperty(thresholdKey);
                    if (thresholdStr != null && !"".equals(thresholdStr)) {

                        try {
                            threshold = Integer.parseInt(thresholdStr);
                        } catch (NumberFormatException e) {
                        }
                    }
                }

                // 不能小于最小值
                if (threshold < 0) {
                    threshold = defaultThreshold;
                }

                lastAccess = System.currentTimeMillis();
            }
            return threshold;
        }
    }

    @Override
    public String toString() {
        return "Spy{" + "className='" + className + '\'' + ", count=" + count.get() + '}';
    }
}
