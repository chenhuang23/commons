/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.datasource;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;

import java.util.Properties;

/**
 * DataSourceConfig.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public interface DataSourceConfigSpi {

    public void setApplicationContext(ApplicationContext applicationContext);

    public DataSource createDateSource(Properties properties);

    public String getName();

}
