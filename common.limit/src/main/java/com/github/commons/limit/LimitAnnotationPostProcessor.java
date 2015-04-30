/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.github.commons.limit.annotation.LimitFlag;
import com.github.commons.limit.annotation.LimitMethod;
import com.github.commons.limit.handler.LogThresholdHandler;
import com.github.commons.limit.handler.ThresholdHandler;

/**
 * LimitAnnotationPostProcessor.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public class LimitAnnotationPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor, ApplicationContextAware, EnvironmentAware {

    private static final Logger        logger       = LoggerFactory.getLogger(LimitAnnotationPostProcessor.class);

    public static final String         LIMIT_ENGINE = "limitInterceptor";
    public static final String         LIMIT_SWITCH = "limitSwitch";

    private ApplicationContext         applicationContext;
    private DefaultListableBeanFactory beanFactory;

    private LimitEngine                engine       = null;

    // 从变量中获取配置
    private Environment                env;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean == null) {
            return bean;
        }

        if (engine == null) {
            Object limitEngine = applicationContext.getBean(LIMIT_ENGINE);

            if (limitEngine != null) {
                engine = (LimitEngine) limitEngine;
            }

            if (engine == null) {
                throw new RuntimeException("Startup limit engine exception.");
            }
        }

        // 找到存在limit的类
        if (bean.getClass().isAnnotationPresent(LimitFlag.class)) {

            Map<String, Spy> spyMap = new HashMap<String, Spy>();

            // 配置spy
            spyMethodConfig(bean, spyMap);

            // register engine
            engine.add(bean.getClass().getName(), spyMap);

            // proxy bean
            Advisor advisor = new DefaultPointcutAdvisor(new LimitDynamicMethodPointcut(bean.getClass(), spyMap),
                                                         new LimitMethodAdvice(spyMap, engine));
            ProxyFactory pf = new ProxyFactory();
            pf.setTarget(bean);
            pf.addAdvisor(advisor);
            return pf.getProxy();
        }

        return bean;
    }

    /**
     * 配置方法
     *
     * @param bean
     * @param spyMap
     */
    private void spyMethodConfig(Object bean, Map<String, Spy> spyMap) {
        Method[] methods = bean.getClass().getDeclaredMethods();

        if (methods != null) {
            for (Method method : methods) {

                if (method.isAnnotationPresent(LimitMethod.class)) {

                    LimitMethod annotation = method.getAnnotation(LimitMethod.class);

                    int threshold = thresholdConfig(annotation);

                    // 处理器
                    String thresholdHandlerStr = annotation.thresholdHandlerRef();
                    ThresholdHandler thresholdHandler = null;

                    beanFactory.registerSingleton("test", new LimitEngine());

                    if (thresholdHandlerStr != null && !"".equals(thresholdHandlerStr)) {
                        thresholdHandler = applicationContext.getBean(thresholdHandlerStr, ThresholdHandler.class);
                    }

                    if (thresholdHandler == null) {
                        thresholdHandler = new LogThresholdHandler();
                    }
                    thresholdHandler.setClassName(bean.getClass().getName());
                    spyMap.put(method.getName(), new Spy(bean.getClass().getName(), thresholdHandler, threshold));

                }
            }
        }
    }

    /**
     * 阀值配置
     *
     * @param annotation
     */
    private int thresholdConfig(LimitMethod annotation) {
        // 默认的阀值
        int threshold = annotation.threshold();
        // 阀值参数key
        String thresholdKey = annotation.thresholdKey();

        // 阀值配置
        String thresholdStr = env.getProperty(thresholdKey);
        if (thresholdStr != null && !"".equals(thresholdStr)) {

            try {
                threshold = Integer.parseInt(thresholdStr);
            } catch (NumberFormatException e) {
            }
        }

        return threshold;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 添加限制引擎
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        final LimitEngine limitEngine = new LimitEngine();

        if (env.getProperty(LIMIT_SWITCH) != null) {
            limitEngine.setOn(Boolean.valueOf(env.getProperty(LIMIT_SWITCH)));
        }

        beanFactory.registerSingleton(LIMIT_ENGINE, limitEngine);

        // 启动一个线程，监听限流引擎是否开启
        new Thread() {

            @Override
            public void run() {

                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                    }

                    logger.debug("fetch limit engine switch.");

                    if (env.getProperty(LIMIT_SWITCH) != null) {
                        limitEngine.setOn(Boolean.valueOf(env.getProperty(LIMIT_SWITCH)));
                    }

                }
            }
        }.start();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    public class LimitMethodAdvice implements MethodInterceptor {

        private Map<String, Spy> spyMap;

        private LimitEngine      engine;

        public LimitMethodAdvice(Map<String, Spy> spyMap, LimitEngine engine){
            this.spyMap = spyMap;
            this.engine = engine;
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {

            logger.debug("before invoke...{}", methodInvocation);

            if (engine.isOn()) {

                Spy spy = spyMap.get(methodInvocation.getMethod().getName());
                try {
                    if (spy.entry(methodInvocation)) {

                        logger.debug("limit entry before invoke...{}", methodInvocation);

                        Object obj = methodInvocation.proceed();

                        logger.debug("limit entry after invoke...{}", methodInvocation);

                        return obj;
                    } else {

                        logger.debug("limit entry failed invoke...{}", methodInvocation);

                        spy.handler(methodInvocation);
                    }
                } finally {
                    spy.release();
                }

            }

            Object obj = methodInvocation.proceed();

            logger.debug("after invoke...{}", methodInvocation);

            return obj;

        }
    }

    public class LimitDynamicMethodPointcut extends DynamicMethodMatcherPointcut {

        private Class            beanClass;
        private Map<String, Spy> spyMap;

        public LimitDynamicMethodPointcut(Class beanClass, Map<String, Spy> spyMap){
            this.beanClass = beanClass;
            this.spyMap = spyMap;
        }

        public ClassFilter getClassFilter() {
            return new ClassFilter() {

                public boolean matches(Class cls) {
                    return (cls == beanClass);
                }
            };
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object[] args) {

            return spyMap.containsKey(method.getName());
        }
    }
}
