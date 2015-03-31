/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.exception;

/**
 * FileSystemException.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class FileSystemException extends RuntimeException {

    private static final long serialVersionUID = 7626981027622208802L;

    public FileSystemException(){
    }

    public FileSystemException(Throwable cause){
        super(cause);
    }

    public FileSystemException(String message){
        super(message);
    }

    public FileSystemException(String message, Throwable cause){
        super(message, cause);
    }

}
