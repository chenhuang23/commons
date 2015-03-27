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
 * Ӧ����������
 *
 * </pre>
 *
 * @author zhouxiaofeng 3/19/15
 */
public interface AppConfigurationSpi {

    /**
     * ������е�appInfo
     * 
     * @return
     */
    public AppInfo[] findAll();

    /**
     * ͨ��appcode ��ȡ����
     * 
     * @param appCode
     * @return
     */
    public AppInfo lookup(String appCode);

    /**
     * ��ȡӦ������
     * 
     * @param params
     * @return
     */
    public AppInfo lookup(ReqParams params);
}
