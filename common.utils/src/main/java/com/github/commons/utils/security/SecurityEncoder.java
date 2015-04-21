/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.utils.security;

import java.util.ArrayList;
import java.util.List;

import org.owasp.esapi.reference.DefaultEncoder;

/**
 * SecurityEncoder.java
 *
 * @author zhouxiaofeng 4/16/15
 */
public class SecurityEncoder extends DefaultEncoder implements SecEncoder {

    private static volatile SecurityEncoder instance = null;

    // Codecs
    private static List<String>             codecs   = new ArrayList();

    static {
        codecs.add("org.owasp.esapi.codecs.HTMLEntityCodec");
        codecs.add("org.owasp.esapi.codecs.PercentCodec");
        codecs.add("org.owasp.esapi.codecs.JavaScriptCodec");
    }

    private SecurityEncoder(){
        this(codecs);
    }

    public SecurityEncoder(List<String> codecNames){
        super(codecNames);
    }

    public static SecEncoder getInstance() {
        if (instance == null) {
            synchronized (SecEncoder.class) {
                if (instance == null) {
                    instance = new SecurityEncoder();
                }
            }
        }
        return instance;
    }
}
