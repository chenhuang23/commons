/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs;

import com.github.commons.fs.contants.FileType;

import java.io.InputStream;

/**
 * FileWriter.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public interface FileWriter {

    public void writeFile(String filename, FileType type, InputStream inputStream, UserMetadata... userMetadatas);

}
