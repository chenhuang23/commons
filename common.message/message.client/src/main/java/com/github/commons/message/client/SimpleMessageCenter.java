package com.github.commons.message.client;

import com.github.commons.message.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/24/15.
 * <p/>
 * 消息中心的代理，客户端通过该接口来发送消息
 */
public class SimpleMessageCenter {

    private IMessageProcessor messageProcessor;

    public void sendSms(List<String> recipients, String templateId, Map<String, Object> paramMap) {
        send(MessageChannel.SMS, templateId, paramMap, recipients);
    }

    private void send(MessageChannel ch, String templateId, Map<String, Object> paramMap, List<String> recipients) {
        doSend(ch, new SimpleEnvelop(templateId, paramMap, recipients));
    }

    private IMessageResponse doSend(MessageChannel ch, IEnvelop envelop) {
        DefaultMessageRequest request = new DefaultMessageRequest();
        request.addEnvelop(ch, envelop);

        return messageProcessor.process(request);
    }

    public void sendSms(String templateId, Map<String, Object> paramMap, List<String> recipients) {
        doSend(MessageChannel.SMS, new SimpleEnvelop(templateId, paramMap, recipients));
    }

    public void sendSms(IEnvelop envelop) {
        doSend(MessageChannel.SMS, envelop);
    }

    public void sendEmail(List<String> recipients, String templateId, Map<String, Object> paramMap) {
        doSend(MessageChannel.EMAIL, new SimpleEnvelop(templateId, paramMap, recipients));
    }

    public void sendEmail(IEnvelop envelop) {
        doSend(MessageChannel.EMAIL, envelop);
    }

    public IMessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(IMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
