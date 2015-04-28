package com.github.commons.mq.domain;

/**
 * MqConsumer.java 处理器, 处理消息得地方，返回成功就认为消息被消费掉
 *
 * @author zhouxiaofeng 2/26/15
 */
public interface ConsumerHandler {

    public boolean handler(MqMessage msg);

}
