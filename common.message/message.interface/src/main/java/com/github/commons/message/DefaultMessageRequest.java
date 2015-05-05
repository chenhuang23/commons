package com.github.commons.message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class DefaultMessageRequest implements IMessageRequest {

    private final Map<MessageChannel, IEnvelop> envelopMap = new HashMap<>();

    private final Map<String, Object> paramMap = new HashMap<>();

    private String templateId;

    @Override
    public Map<MessageChannel, IEnvelop> getEnvelops() {
        return Collections.unmodifiableMap(envelopMap);
    }

    public void addEnvelop(MessageChannel ch, IEnvelop envelop) {

        if (envelopMap.containsKey(ch))
            throw new IllegalArgumentException("duplicated envelop for channel: " + ch.name());

        envelopMap.put(ch, envelop);
    }

    @Override
    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(paramMap);
    }

    public void addParameter(String key, Object obj) {
        paramMap.put(key, obj);
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String getTemplateId() {
        return templateId;
    }
}
