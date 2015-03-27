/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local;

import com.github.commons.utils.spi.SpiLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.config.AppInfo;
import com.github.commons.security.spi.AppConfigurationSpi;

/**
 * SecKeyPolicyRespository.java
 *
 * @author zhouxiaofeng 3/19/15
 */
public class LocalAppManager implements AppConfigurationSpi {

    private static final Logger logger               = LoggerFactory.getLogger(LocalAppManager.class);

    private final static String DEFAULT_CONFIG_CLASS = "com.github.commons.security.config.DefaultAppConfiguration";

    private AppConfigurationSpi appConfiguration;

    public LocalAppManager(){
        init();
    }

    private void init() {

        // ��ȡӦ�õ�����
        // ��ʼ��ʹ���ߺ�key, Ĭ�ϴ��ļ��л�ȡ
        appConfiguration = SpiLoader.loadFirst(AppConfigurationSpi.class);

        if (appConfiguration == null) {
            try {
                appConfiguration = (AppConfigurationSpi) Class.forName(DEFAULT_CONFIG_CLASS).newInstance();
            } catch (Throwable e) {
                logger.error("load AppConfigurationSpi exception.", e);
            }
        }

    }

    @Override
    public AppInfo[] findAll() {
        return appConfiguration.findAll();
    }

    @Override
    public AppInfo lookup(String appCode) {
        return appConfiguration.lookup(appCode);
    }

    @Override
    public AppInfo lookup(ReqParams params) {
        return appConfiguration.lookup(params);
    }
}
