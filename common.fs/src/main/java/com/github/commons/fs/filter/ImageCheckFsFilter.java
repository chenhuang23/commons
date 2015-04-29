/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.filter;

import java.io.InputStream;

import com.github.commons.fs.FsFilter;
import com.github.commons.fs.contants.FileType;
import com.github.commons.fs.utils.ImageUtils;

/**
 * ImageCheckFsFilter.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public class ImageCheckFsFilter implements FsFilter {

    @Override
    public InputStream filter(InputStream inputStream, FileType type) throws Exception {
        switch (type) {
            case IMG:
                // first check the img
                inputStream.mark(inputStream.available());
                try {
                    if (!ImageUtils.check(inputStream)) {
                        throw new IllegalArgumentException("The image content is not legal.");
                    }
                } finally {
                    // not break;
                    inputStream.reset();
                }
        }

        return inputStream;
    }
}
