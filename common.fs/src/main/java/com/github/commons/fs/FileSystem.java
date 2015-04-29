/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs;

import com.github.commons.fs.filter.ImageCheckFsFilter;
import com.github.commons.fs.filter.ImageConvertFsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * FileSystem.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public abstract class FileSystem implements FileReader, FileWriter {

    private List<FsFilter> fsFilters = new ArrayList<FsFilter>(2);

    public FileSystem(){
        defaultRegisterFilter();
    }

    public FileSystem(List<FsFilter> fsFilters){
        this.fsFilters = fsFilters;
    }

    /**
     * 
     */
    public void registerFilter(FsFilter fsFilter) {
        fsFilters.add(fsFilter);
    }

    /**
     * 默认的过滤器
     */
    private void defaultRegisterFilter() {

        fsFilters.add(new ImageCheckFsFilter());
        fsFilters.add(new ImageConvertFsFilter());

    }

}
