/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.contants;

/**
 * FileType.java
 *
 * @author zhouxiaofeng 4/1/15
 */
public enum FileType {

    // 图片的链接
    IMG_URL("img_url"),
    // 图
    IMG("img"),

    ALL("all");

    private String type;

    private FileType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
