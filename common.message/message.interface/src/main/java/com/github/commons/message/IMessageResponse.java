package com.github.commons.message;

import java.io.Serializable;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 消息处理结果
 */
public interface IMessageResponse extends Serializable {
    int getErrorCode();

    String getErrorMessage();
}
