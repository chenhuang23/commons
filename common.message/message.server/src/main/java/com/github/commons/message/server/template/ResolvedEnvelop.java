package com.github.commons.message.server.template;

import com.github.commons.message.IEnvelop;

import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public class ResolvedEnvelop implements IEnvelop {

    private final IEnvelop request;

    private final String content;

    public ResolvedEnvelop(IEnvelop request, String content) {
        this.request = request;
        this.content = content;
    }

    @Override
    public Map<String, Object> getParameters() {
        return request.getParameters();
    }

    @Override
    public String getTemplateId() {
        return getTemplateId();
    }

    @Override
    public List<String> getRecipients() {
        return getRecipients();
    }

    public String getContent() {
        return this.content;
    }
}
