package com.github.commons.message.server.template;

import com.github.commons.message.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 测试用的模板解析工具
 */
@Service
public class LocalTemplateResolver implements ITemplateResolver {
    @Override
    public String resolve(MessageChannel ch, String templateId, Map<String, Object> params) {
        String realTplId = createRealTemplateId(ch, templateId);
        return evaluate(realTplId, params);
    }

    private String evaluate(String realTplId, Map<String, Object> params) {
        return loadTemplate(realTplId);
    }

    private String loadTemplate(String realTplId) {
        return String.format("just do it, %s", realTplId);
    }

    private String createRealTemplateId(MessageChannel ch, String templateId) {
        return templateId + "." + ch.name();
    }
}
