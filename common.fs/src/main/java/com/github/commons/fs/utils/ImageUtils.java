/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.utils;

import java.io.InputStream;

import org.devlib.schmidt.imageinfo.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImageUtils.java
 *
 * @author zhouxiaofeng 4/1/15
 */
public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * check image
     * 
     * @param inputStream
     * @return
     */
    public static boolean check(InputStream inputStream) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setInput(inputStream);
        imageInfo.setCollectComments(false);
        imageInfo.setDetermineImageNumber(false);

        try {
            return imageInfo.check();
        } catch (Throwable e) {
            logger.error("check image exception.", e);
            return false;
        }
    }
}
