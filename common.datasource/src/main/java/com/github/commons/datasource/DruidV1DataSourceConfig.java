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
        source.setMaxActive(Integer.parseInt(String.valueOf(properties.get("druid.maxActive"))));

        if (properties.get("druid.logAbandoned") != null) {
            // 关闭abanded连接时输出错误日志
            source.setLogAbandoned(Boolean.valueOf(String.valueOf(properties.get("druid.logAbandoned"))));
        }

        if (properties.get("druid.removeAbandoned") != null) {
            source.setRemoveAbandoned(Boolean.valueOf(String.valueOf(properties.get("druid.removeAbandoned"))));
        }

        if (properties.get("druid.removeAbandonedTimeout") != null) {
            source.setRemoveAbandonedTimeout(Integer.parseInt(String.valueOf(properties.get("druid.removeAbandonedTimeout"))));
        }

        if (properties.get("druid.removeAbandonedTimeoutMillis") != null) {

            source.setRemoveAbandonedTimeoutMillis(Integer.parseInt(String.valueOf(properties.get("druid.removeAbandonedTimeoutMillis"))));
        }

        source.setConnectProperties(properties);

        return source;
    }

    @Override
    public String getName() {
        return "druid-V1";
    }
}
