package com.github.commons.message.client;

import com.github.commons.message.IEnvelop;
import com.github.commons.message.SimpleEnvelop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/24/15.
 * <p/>
 * 工具类
 */
public final class EnvelopBuilder {

    private EnvelopBuilder(){
    }

    public static class Builder {

        private final Map<String, Object> paramMap   = new HashMap<>();

        private final List<String>        recipients = new ArrayList<>();

        private final String              templateId;

        private final String              title;
        private final IEnvelop.LEVEL      level;

        public Builder(String title, String templateId, IEnvelop.LEVEL level){

            this.title = title;
            this.templateId = templateId;
            this.level = level;
        }

        public Builder addParameterMap(Map<String, Object> params) {
            paramMap.putAll(params);
            return this;
        }

        public Builder addParameter(String key, Object value) {
            paramMap.put(key, value);
            return this;
        }

        public Builder addRecipient(String recipient) {
            recipients.add(recipient);
            return this;
        }

        public IEnvelop build() {
            return new SimpleEnvelop(title, templateId, level, paramMap,
                                     recipients.toArray(new String[recipients.size()]));
        }
    }

    public static Builder newBuilder(String title, String templateId, IEnvelop.LEVEL level) {
        return new Builder(title, templateId, level);
    }
}
