package com.github.commons.utils.log;

import org.owasp.esapi.LogFactory;
import org.owasp.esapi.Logger;
import org.slf4j.LoggerFactory;

/**
 * EsapiLogFactory.java
 * 
 * @author zhouxiaofeng 5/6/15
 */
public class EsapiLogFactory implements LogFactory {

    @Override
    public Logger getLogger(String moduleName) {

        org.slf4j.Logger logger = LoggerFactory.getLogger(moduleName);
        return new EsapiLog(logger);
    }

    @Override
    public Logger getLogger(Class clazz) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        return new EsapiLog(logger);
    }
}
