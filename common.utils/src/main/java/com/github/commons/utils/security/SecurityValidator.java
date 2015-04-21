/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.utils.security;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.Validator;
import org.owasp.esapi.reference.DefaultValidator;

/**
 * SecurityValidator.java
 *
 * @author zhouxiaofeng 4/16/15
 */
public class SecurityValidator extends DefaultValidator implements SecValidator {

    private static volatile SecurityValidator instance = null;

    public SecurityValidator(Encoder encoder){
        super(encoder);
    }

    private SecurityValidator(){
        super(ESAPI.encoder());
    }

    public static SecValidator getInstance() {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    instance = new SecurityValidator();
                }
            }
        }
        return instance;
    }

}
