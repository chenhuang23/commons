/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.filter;

import java.io.IOException;
import java.io.InputStream;

import com.github.commons.fs.FsFilter;
import com.github.commons.fs.contants.FileType;
import com.github.commons.fs.exception.FileSystemException;
import com.github.commons.fs.utils.ImageUtils;

/**
 * ImageCheckFsFilter.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public class ImageConvertFsFilter implements FsFilter {

    private String convertFormat = ImageUtils.FORMAT_TYP_JPG;

    @Override
    public InputStream filter(InputStream inputStream, FileType type) throws Exception {
        switch (type) {
            case IMG:

                // convert
                try {
                    inputStream = ImageUtils.convert(inputStream, convertFormat);
                } catch (IOException e) {
                    throw new FileSystemException("Convert image exception.", e);
                }
        }

        return inputStream;
    }

    public void setConvertFormat(String convertFormat) {
        this.convertFormat = convertFormat;
    }
}
