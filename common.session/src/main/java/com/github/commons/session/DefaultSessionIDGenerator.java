/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.session;

import com.github.commons.session.utils.UUID;

/**
 * DefaultSessionIDGenerator.java
 *
 * @author zhouxiaofeng 4/20/15
 */
public class DefaultSessionIDGenerator extends UUID implements SessionIDGenerator {

    @Override
    public String generateId() {
        return nextID();
    }
}
