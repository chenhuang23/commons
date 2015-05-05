package com.github.commons.message;

import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public interface ITemplate {
    Map<String, Object> getParameters();

    String getTemplateId();
}
