/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.spi;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.config.AppInfo;

/**
 * <pre>
 *
 * AppConfiguration.java
 * 
 * 应用配置载入
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface AppConfigurationSpi {

    /**
     * 获得所有的appInfo
     * 
     * @return
     */
    public AppInfo[] findAll();

    /**
     * 通过appcode 获取配置
     * 
     * @param appCode
     * @return
     */
    public AppInfo lookup(String appCode);

    /**
     * 获取应用配置
     * 
     * @param params
     * @return
     */
    public AppInfo lookup(ReqParams params);
}
