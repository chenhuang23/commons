/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Limit.java
 *
 * @author zhouxiaofeng 4/29/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitMethod {

    /**
     * 当前方法的调用阀值
     * 
     * @return
     */
    int threshold() default Integer.MAX_VALUE;

    /**
     * <pre>
     * 当前方法的调用阀值对应的配置key,参数将从org.springframework.core.env.Environment 中获取 
     * 不填写为threshold的值
     * </pre>
     * 
     * @return
     */
    String thresholdKey() default "";

    /**
     * 处理器的beanName,不填写为默认配置
     * 
     * @return
     */
    String thresholdHandlerRef() default "";

}
