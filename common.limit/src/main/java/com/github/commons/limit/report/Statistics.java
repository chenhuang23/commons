/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit.report;

import com.github.commons.limit.Spy;

import java.util.Map;

/**
 * <pre>
 * 
 * Statistics.java
 * 
 * 对当前限流情况进行统计
 * 
 * </pre>
 * 
 * @author zhouxiaofeng 4/30/15
 */
public interface Statistics {

    /**
     * 开始一个统计信息
     */
    public void start();

    /**
     * 结束统计
     */
    public void stop();

    /**
     * 设置当前所有限流监控服务
     * 
     * @param classLimitRepository
     */
    public void setSpys(Map<String, Map<String, Spy>> classLimitRepository);

    /**
     * 获取当前所有的限流监控服务
     * 
     * @return
     */
    public Map<String, Map<String, Spy>> getSpys();
}
