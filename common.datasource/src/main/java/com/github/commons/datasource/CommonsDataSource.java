/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.datasource;

import java.io.Closeable;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * @author zhouxiaofeng 3/17/15
 */
public class CommonsDataSource implements FactoryBean<DataSource>, ApplicationContextAware, DisposableBean {

    private static final Logger       logger = LoggerFactory.getLogger(CommonsDataSource.class);

    private ApplicationContext        applicationContext;
    private DataSource                dataSource;
    private String                    dataSourceName;
    private FetchDataSourcesConfigSpi fetchDataSourcesConfigSpi;
    private DataSourceConfigSpi       dataSourceConfigSpi;

    private volatile Object           lock   = new Object();

    @Override
    public DataSource getObject() throws Exception {

        if (dataSource != null) {

            return dataSource;
        }

        if ((dataSourceName == null || "".equals(dataSourceName))) {

            throw new IllegalArgumentException("dataSourceImpl can't be null.");
        }

        synchronized (lock) {

            if (dataSource != null) {

                return dataSource;
            }

            if (dataSourceConfigSpi == null) {
                initDataSourceConfig();
                Assert.notNull(dataSourceConfigSpi);
            }

            if (fetchDataSourcesConfigSpi == null) {
                initFetchDataSourcesConfig();
                Assert.notNull(fetchDataSourcesConfigSpi);
            }

            fetchDataSourcesConfigSpi.setApplicationContext(this.applicationContext);
            Properties properties = fetchDataSourcesConfigSpi.fetchConfigProp();

            Assert.notNull(properties);

            if (dataSource == null) {
                dataSource = dataSourceConfigSpi.createDateSource(properties);
            }

        }

        logger.debug("[Common-datasource] dataSource: " + dataSource);

        return dataSource;

    }

    private void initFetchDataSourcesConfig() {
        ServiceLoader<FetchDataSourcesConfigSpi> fetchDataSourcesConfigSpis = ServiceLoader.load(FetchDataSourcesConfigSpi.class);

        if (fetchDataSourcesConfigSpis != null) {

            for (FetchDataSourcesConfigSpi fetch : fetchDataSourcesConfigSpis)
                if (fetch != null) {

                    fetchDataSourcesConfigSpi = fetch;
                }
        }
    }

    private void initDataSourceConfig() {
        ServiceLoader<DataSourceConfigSpi> dataSourceConfigloader = ServiceLoader.load(DataSourceConfigSpi.class);

        if (dataSourceConfigloader != null) {

            for (DataSourceConfigSpi config : dataSourceConfigloader)
                if (config != null && config.getName().equals(dataSourceName)) {

                    dataSourceConfigSpi = config;
                    return;

                }
        }
    }

    @Override
    public Class<? extends DataSource> getObjectType() {
        return (this.dataSource != null ? this.dataSource.getClass() : DataSource.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    // get set ==========>
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        if (dataSource != null && dataSource instanceof Closeable) {
            ((Closeable) dataSource).close();
        }
    }

    public void setFetchDataSourcesConfigSpi(FetchDataSourcesConfigSpi fetchDataSourcesConfigSpi) {
        this.fetchDataSourcesConfigSpi = fetchDataSourcesConfigSpi;
    }

    public void setDataSourceConfigSpi(DataSourceConfigSpi dataSourceConfigSpi) {
        this.dataSourceConfigSpi = dataSourceConfigSpi;
    }
}
