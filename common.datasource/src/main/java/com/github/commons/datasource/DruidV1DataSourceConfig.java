/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * DruidDataSourceConfig.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public class DruidV1DataSourceConfig implements DataSourceConfigSpi {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public DataSource createDateSource(Properties properties) {


        DruidDataSource source = new DruidDataSource();

        source.setUrl(String.valueOf(properties.get("druid.url")));
        source.setUsername(String.valueOf(properties.get("druid.username")));
        source.setPassword(String.valueOf(properties.get("druid.password")));

        source.setConnectProperties(properties);

        return source;
    }

    @Override
    public String getName() {
        return "druid-V1";
    }
}
