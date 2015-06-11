package com.github.commons.utils.log;

/**
 * EsapiLog.java
 * 
 * @author zhouxiaofeng 5/6/15
 */
public class EsapiLog implements org.owasp.esapi.Logger {

    private org.slf4j.Logger logger;

    public EsapiLog(org.slf4j.Logger logger){
        this.logger = logger;
    }

    @Override
    public void setLevel(int level) {
    }

    @Override
    public int getESAPILevel() {
        return 0;
    }

    @Override
    public void fatal(EventType type, String message) {
        logger.info(message);
    }

    @Override
    public void fatal(EventType type, String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void error(EventType type, String message) {
        logger.error(message);
    }

    @Override
    public void error(EventType type, String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void warning(EventType type, String message) {
        logger.warn(message);
    }

    @Override
    public void warning(EventType type, String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    @Override
    public boolean isWarningEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void info(EventType type, String message) {
        logger.info(message);
    }

    @Override
    public void info(EventType type, String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void debug(EventType type, String message) {
        logger.debug(message);
    }

    @Override
    public void debug(EventType type, String message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void trace(EventType type, String message) {
        logger.trace(message);
    }

    @Override
    public void trace(EventType type, String message, Throwable throwable) {
        logger.trace(message, throwable);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void always(EventType type, String message) {
        logger.debug(message);
    }

    @Override
    public void always(EventType type, String message, Throwable throwable) {
        logger.debug(message, throwable);
    }
}
