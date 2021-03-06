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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitFlag {

    /**
     * 处理器的beanName,不填写为默认配置
     *
     * @return
     */
    String thresholdHandlerRef() default "";

    /**
     * 使用 ThresholdHandle 类型注入
     *
     * @return
     */
    boolean thresholdHandleByType() default true;
}
