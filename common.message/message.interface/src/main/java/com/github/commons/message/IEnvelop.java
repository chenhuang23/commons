package com.github.commons.message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public interface IEnvelop extends ITemplate, Serializable {
    Map<String, Object> getParameters();

    String getTemplateId();

    List<String> getRecipients();
}
