package com.github.commons.message.server.template;

import com.github.commons.message.MessageChannel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 测试用的模板解析工具
 */
@Service
public class LocalTemplateResolver implements ITemplateResolver {

    private final Configuration freeMarkerConf;

    public LocalTemplateResolver(String dir) {
        freeMarkerConf = new Configuration(Configuration.VERSION_2_3_22);
        setTemplateDir(dir);
    }

    public void setTemplateDir(String dir) {
        try {
            freeMarkerConf.setDirectoryForTemplateLoading(new File(dir));
        } catch (IOException e) {
            throw new IllegalArgumentException("template dir \"" + dir + "\" doesn't exists!", e);
        }
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
        return templateId + "." + ch.suffix;
    }
}
