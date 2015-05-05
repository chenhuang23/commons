package com.github.commons.message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class SimpleEnvelop implements IEnvelop {

    private final Map<String, Object> paramMap;

    private final String templateId;

    private final List<String> recipients;

    public SimpleEnvelop(String templateId, Map<String, Object> paramMap, List<String> recipients) {
        this.paramMap = paramMap == null ? Collections.<String, Object>emptyMap() : Collections.unmodifiableMap(paramMap);
        this.templateId = templateId;
        this.recipients = recipients == null ? Collections.<String>emptyList() : Collections.unmodifiableList(recipients);
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
    public List<String> getRecipients() {
        return recipients;
    }
}
