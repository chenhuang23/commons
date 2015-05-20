/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.local;

import com.github.commons.security.utils.ApplicationContextUtils;
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

    public static final int     INIT_SPI_STEP        = 1;
    public static final int     INIT_SPRING_STEP     = 2;
    public static final int     INIT_DEFAULT_STEP    = 3;

    private final static String DEFAULT_CONFIG_CLASS = "com.github.commons.security.config.DefaultAppConfiguration";

    private AppConfigurationSpi appConfiguration;

    public LocalAppManager(){
        init();
    }

    private void init() {

        Integer step = INIT_SPI_STEP;

        while (appConfiguration == null && step <= INIT_DEFAULT_STEP) {
            initAppConfiguration(step);
            step++;

        }

    }

    private void initAppConfiguration(Integer step) {
        switch (step) {

            case INIT_SPI_STEP:
                appConfiguration = SpiLoader.loadFirst(AppConfigurationSpi.class);

                return;

            case INIT_SPRING_STEP:
                if (ApplicationContextUtils.getApplicationContext() != null) {
                    appConfiguration = ApplicationContextUtils.getApplicationContext().getBean(AppConfigurationSpi.class);
                }

                return;

            case INIT_DEFAULT_STEP:
            default:
                try {
                    appConfiguration = (AppConfigurationSpi) Class.forName(DEFAULT_CONFIG_CLASS).newInstance();
                } catch (Throwable e) {
                    logger.error("load AppConfigurationSpi exception.", e);
                }

                return;
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
