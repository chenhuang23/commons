/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.datasource;

import org.springframework.context.ApplicationContext;

import java.util.Properties;

/**
 * FetchDataSourcesConfigSpi.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public interface FetchDataSourcesConfigSpi {

    public Properties fetchConfigProp();

    public void setApplicationContext(ApplicationContext applicationContext);

}
