package com.github.commons.message;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 消息处理结果
 */
public interface IMessageResponse {
    int getErrorCode();

    String getErrorMessage();
}
