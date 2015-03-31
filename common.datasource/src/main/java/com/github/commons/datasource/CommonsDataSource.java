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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author zhouxiaofeng 3/17/15
 */
public class CommonsDataSource implements FactoryBean<DataSource>, ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;
    private DataSource         dataSource;

    private String             dataSourceName;

    @Override
    public DataSource getObject() throws Exception {

        if (dataSource != null) {

            return dataSource;
        }

        if ((dataSourceName == null || "".equals(dataSourceName))) {

            throw new IllegalArgumentException("dataSourceImpl can't be null.");
        }

        ServiceLoader<DataSourceConfigSpi> dataSourceConfigloader = ServiceLoader.load(DataSourceConfigSpi.class);

        if (dataSourceConfigloader != null) {

            for (DataSourceConfigSpi config : dataSourceConfigloader)
                if (config != null && config.getName().equals(dataSourceName)) {

                    ServiceLoader<FetchDataSourcesConfigSpi> fetchDataSourcesConfigSpis = ServiceLoader.load(FetchDataSourcesConfigSpi.class);

                    Properties properties = null;

                    if (fetchDataSourcesConfigSpis != null) {

                        for (FetchDataSourcesConfigSpi fetch : fetchDataSourcesConfigSpis)
                            if (fetch != null) {

                                fetch.setApplicationContext(this.applicationContext);
                                properties = fetch.fetchConfigProp();
                            }
                    }

                    if (properties == null) {
                        return null;
                    }

                    return config.createDateSource(properties);
                }

        }

        return dataSource;

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
}
