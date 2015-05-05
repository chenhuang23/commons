package com.github.commons.message.server.template;

import com.github.commons.message.MessageChannel;

import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 模板解析器
 */
public interface ITemplateResolver {
    String resolve(MessageChannel ch, String templateId, Map<String, Object> params);
}
