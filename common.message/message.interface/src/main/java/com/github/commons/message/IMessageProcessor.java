package com.github.commons.message;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 消息处理器
 */
public interface IMessageProcessor {
    IMessageResponse process(IMessageRequest envelop);
}
