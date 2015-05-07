package com.github.commons.message.server.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.github.commons.message.MessageChannel;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 字符串模版
 *
 * @author xiaofengzhouxf
 */
public class StringTemplateResolver implements ITemplateResolver {

    private final Configuration  freeMarkerConf;

    private Map<String, String>  templates;
    private StringTemplateLoader stringLoader;

    public StringTemplateResolver(){
        stringLoader = new StringTemplateLoader();
        freeMarkerConf = new Configuration(Configuration.VERSION_2_3_22);
        freeMarkerConf.setTemplateLoader(stringLoader);
    }

    @Override
    public String resolve(MessageChannel ch, String templateId, Map<String, Object> params) {
        String realTplId = createRealTemplateId(ch, templateId);
        return evaluate(realTplId, params);
    }

    private String evaluate(String realTplId, Map<String, Object> params) {
        try {
            Template tpl = freeMarkerConf.getTemplate(realTplId);

            StringWriter out = new StringWriter();
            tpl.process(params, out);

            return out.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot load template: \"" + realTplId + "\"", e);
        } catch (TemplateException e) {
            throw new IllegalStateException("Cannot evaluate template", e);
        }
    }

    private String createRealTemplateId(MessageChannel ch, String templateId) {
        return templateId;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;

        if (stringLoader != null && templates != null && templates.size() > 0) {

            for (Map.Entry<String, String> entry : templates.entrySet()) {
                stringLoader.putTemplate(entry.getKey(), entry.getValue());
            }
        }
    }
}
