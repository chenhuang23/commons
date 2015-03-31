/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs;

import java.io.InputStream;

/**
 * FileReader.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public interface FileReader {

    public InputStream getFile(String filename);

}
