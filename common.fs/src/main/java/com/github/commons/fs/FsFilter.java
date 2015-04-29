/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs;

import com.github.commons.fs.contants.FileType;

import java.io.InputStream;

/**
 * FsFilter.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public interface FsFilter {

    public InputStream filter(InputStream source, FileType type) throws Exception;
}
