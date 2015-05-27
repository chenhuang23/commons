/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.datasource;

/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 *
 */

import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.github.commons.utils.format.JsonUtils;
import com.github.diamond.client.spring.MessagePropertyPlaceholderConfigurer;

/**
 * DefaultFetchDataSourceConfig.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public class DefaultFetchDataSourceConfig implements FetchDataSourcesConfigSpi {

    private ApplicationContext applicationContext;

    private String             dataSourceKey = "commonDatasSource";

    @Override
    public Properties fetchConfigProp() {

        Map<String, MessagePropertyPlaceholderConfigurer> beansOfType = applicationContext.getBeansOfType(MessagePropertyPlaceholderConfigurer.class);

        if (beansOfType == null || beansOfType.isEmpty()) {
            return null;
        }

        MessagePropertyPlaceholderConfigurer messagePropertyPlaceholderConfigurer = beansOfType.values().iterator().next();

        Object mysqlDatasSource = messagePropertyPlaceholderConfigurer.getContextProperty(dataSourceKey);

        if (mysqlDatasSource == null || !(mysqlDatasSource instanceof String)) {
            return null;
        }

        return JsonUtils.toProperties(JsonUtils.parseJson(String.valueOf(mysqlDatasSource)));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

    }

    public void setDataSourceKey(String dataSourceKey) {
        this.dataSourceKey = dataSourceKey;
    }
}
