/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.support.remote;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RemoteReq.java
 *
 *
 * @author zhouxiaofeng 3/29/15
 */
public class RemoteResp implements Serializable {

    private static final long serialVersionUID = -931379142875111126L;

    private int               command;

    private int               typeCode;

    private int               paramsLength;

    private String[]          params;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public int getParamsLength() {
        return paramsLength;
    }

    public void setParamsLength(int paramsLength) {
        this.paramsLength = paramsLength;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return "RemoteReq{" + "command=" + command + ", typeCode=" + typeCode + ", paramsLength=" + paramsLength
               + ", params=" + Arrays.toString(params) + '}';
    }
}
