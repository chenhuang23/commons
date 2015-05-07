package com.github.commons.message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class SimpleEnvelop implements IEnvelop {

    private static final long         serialVersionUID = -974367103003622830L;
    private final Map<String, Object> paramMap;

    private final String              templateId;

    private final String              title;
    // 消息发送级别
    private final LEVEL               level;

    private final String[]            recipients;

    public SimpleEnvelop(String title, String templateId, LEVEL level, Map<String, Object> paramMap,
                         String... recipients){
        this.paramMap = paramMap == null ? Collections.<String, Object> emptyMap() : Collections.unmodifiableMap(paramMap);
        this.templateId = templateId;
        this.title = title;
        this.level = level;
        this.recipients = recipients;
    }

    @Override
    public Map<String, Object> getParameters() {
        return paramMap;
    }

    @Override
    public String getTemplateId() {
        return templateId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public LEVEL getLevel() {
        return level;
    }

    @Override
    public String[] getRecipients() {
        return recipients;
    }
}
