/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.github.commons.security.spi.AppConfigurationSpi;
import com.github.commons.security.support.ReqParams;
import com.github.commons.utils.format.JsonUtils;

/**
 * AutoConfAppConfigurationSpi.java
 *
 * @author zhouxiaofeng 3/30/15
 */
public class SpringAppConfiguration implements AppConfigurationSpi, InitializingBean, DisposableBean {

    private static final Logger  logger = LoggerFactory.getLogger(SpringAppConfiguration.class);

    private String               config;

    private Map<String, AppInfo> appMap = null;

    @Override
    public AppInfo[] findAll() {
        return appMap != null ? appMap.values().toArray(new AppInfo[0]) : null;
    }

    @Override
    public AppInfo lookup(String appCode) {
        return appMap != null ? appMap.get(appCode) : null;
    }

    @Override
    public AppInfo lookup(ReqParams params) {
        return lookup(params.appCode);
    }

    @Override
    public void afterPropertiesSet() {

        AppInfo[] appInfos = null;
        if (StringUtils.isNotBlank(config)) {
            appInfos = JsonUtils.fromJson(config, AppInfo[].class);
        }

        if (appInfos != null) {

            appMap = new HashMap<String, AppInfo>(appInfos.length);
            for (AppInfo appinfo : appInfos) {
                appMap.put(appinfo.getAppCode(), appinfo);
                logger.info("[LOAD APP CONF]" + appinfo);
            }
        }

    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public void destroy() throws Exception {
        if (appMap != null) appMap.clear();
    }
}
