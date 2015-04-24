package com.github.commons.message;

import java.util.Map;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 消息载体，由客户端发送给服务端。载体包含了消息需要使用的模板、解析模板的参数以及消息期望的通道。
 */
public interface IMessageRequest extends ITemplate {
    Map<MessageChannel, IEnvelop> getEnvelops();
}
