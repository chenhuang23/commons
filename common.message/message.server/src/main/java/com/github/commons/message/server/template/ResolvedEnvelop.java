package com.github.commons.message.server.template;

import com.github.commons.message.IEnvelop;

import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public class ResolvedEnvelop implements IEnvelop {

    private static final long serialVersionUID = 6378403910279033909L;

    private final IEnvelop    request;

    private final String      title;

    private final String      content;

    // 消息发送级别

    private final LEVEL       level;

    public ResolvedEnvelop(IEnvelop request, String title, String content, LEVEL level){
        this.request = request;
        this.content = content;
        this.title = title;
        this.level = level;
    }

    @Override
    public Map<String, Object> getParameters() {
        return request.getParameters();
    }

    @Override
    public String getTemplateId() {
        return request.getTemplateId();
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
        return request.getRecipients();
    }

    public String getContent() {
        return this.content;
    }

}
