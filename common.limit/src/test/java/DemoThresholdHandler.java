/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import com.github.commons.limit.exception.LimitException;
import com.github.commons.limit.handler.ThresholdHandler;
import org.aopalliance.intercept.MethodInvocation;

/**
 * ThresholdHandler.java
 *
 * @author zhouxiaofeng 5/25/15
 */
public class DemoThresholdHandler implements ThresholdHandler {

    private String className;

    @Override
    public boolean permission(MethodInvocation methodInvocation) throws LimitException {

        System.out.println("look permission");

        return true;
    }

    @Override
    public void handlerFailed(MethodInvocation methodInvocation) throws LimitException {

        System.out.printf("failed. entry..." + methodInvocation.getMethod().getName());
    }

    @Override
    public void setClassName(String name) {
        this.className = name;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
