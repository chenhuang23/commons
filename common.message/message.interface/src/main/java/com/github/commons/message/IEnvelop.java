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

    String getTitle();

    LEVEL getLevel();

    String[] getRecipients();

    public static enum LEVEL {
        LEVEL_HIGH(0), LEVEL_NORMAL(3), LEVEL_LOW(5);

        LEVEL(int level){
            this.level = level;
        }

        private int level;

    }
}
