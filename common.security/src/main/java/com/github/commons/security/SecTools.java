/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security;

import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.support.CodecTool;
import com.github.commons.security.support.SecTool;
import com.github.commons.security.support.SignTool;
import com.github.commons.security.utils.ApplicationContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author zhouxiaofeng 3/27/15
 */
public class SecTools extends SecTool implements ApplicationContextAware {

    private CodecTool codecTool;
    private SignTool  signTool;

    public SecTools(String appCode, String policy, int version){
        super(appCode, SecPolicy.lookup(policy), version);

        codecTool = new CodecToolsFacade(appCode, SecPolicy.lookup(policy), version);
        signTool = new SignToolsFacade(appCode, SecPolicy.lookup(policy), version);
    }

    public SecTools(String appCode, SecPolicy policy, int version){
        super(appCode, policy, version);

        codecTool = new CodecToolsFacade(appCode, policy, version);
        signTool = new SignToolsFacade(appCode, policy, version);
    }

    public CodecTool codecTool() {
        return codecTool;
    }

    public SignTool signTool() {
        return signTool;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.init(applicationContext);
    }
}
