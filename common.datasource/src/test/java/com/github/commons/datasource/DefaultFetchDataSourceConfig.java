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

import com.github.commons.datasource.FetchDataSourcesConfigSpi;
import com.github.commons.utils.format.JsonUtils;
import com.github.diamond.client.spring.MessagePropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Properties;

/**
 * DefaultFetchDataSourceConfig.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public class DefaultFetchDataSourceConfig implements FetchDataSourcesConfigSpi {

    private ApplicationContext applicationContext;

    @Override
    public Properties fetchConfigProp() {

        Map<String, MessagePropertyPlaceholderConfigurer> beansOfType = applicationContext.getBeansOfType(MessagePropertyPlaceholderConfigurer.class);

        if (beansOfType == null || beansOfType.isEmpty()) {
            return null;
        }

        MessagePropertyPlaceholderConfigurer messagePropertyPlaceholderConfigurer = beansOfType.values().iterator().next();

        Object mysqlDatasSource = messagePropertyPlaceholderConfigurer.getContextProperty("commonDatasSource");

        if (mysqlDatasSource == null || !(mysqlDatasSource instanceof String)) {
            return null;
        }

        return JsonUtils.toProperties(JsonUtils.parseJson(String.valueOf(mysqlDatasSource)));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;

    }
}
