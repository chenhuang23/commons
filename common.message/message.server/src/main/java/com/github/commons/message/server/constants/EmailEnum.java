/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.message.server.constants;

/**
 * EmailEnum.java
 *
 * @author zhouxiaofeng 6/9/15
 */
public enum EmailEnum {
    __Attachment_ENTITY("__Attachment_ENTITY", ""), __Attachment_FILE("__Attachment_FILE", ""),
    __Attachment_ENTITYS("__Attachment_ENTITYS", ""), __Attachment_FILES("__Attachment_FILES", "");

    private String key;
    private String desc;

    EmailEnum(String key, String desc){
        this.desc = desc;
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public String getKey() {
        return key;
    }
}
