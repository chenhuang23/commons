/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.constants;

/**
 * <pre>
 * EncryptType.java
 * 
 * ���ܽ��ܣ�����
 * 
 * </pre>
 * 
 * @author zhouxiaofeng 3/19/15
 */
public enum EncryptType {

    DES("des"), RSA("rsa"),

    // ������չ
    XDES("xdes"), XRSA("xrsa");

    private String type;

    EncryptType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
