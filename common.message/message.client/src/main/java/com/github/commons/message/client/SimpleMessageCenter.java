package com.github.commons.message.client;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.github.commons.message.*;

/**
 * Created by Yang Tengfei on 4/24/15.
 * <p/>
 * 消息中心的代理，客户端通过该接口来发送消息
 */
public class SimpleMessageCenter {
    private IMessageProcessor messageProcessor;

    public boolean sendSms(String templateId, Map<String, Object> paramMap, String... recipients) {
        return send(MessageChannel.SMS, StringUtils.EMPTY, templateId, paramMap, recipients);
    }

    private boolean send(MessageChannel ch, String title, String templateId, Map<String, Object> paramMap,
                         String... recipients) {
        IMessageResponse iMessageResponse = doSend(ch, new SimpleEnvelop(title, templateId,
                                                                         IEnvelop.LEVEL.LEVEL_NORMAL, paramMap,
                                                                         recipients));
        if (iMessageResponse != DefaultMessageResponse.SUCCESS) {
            return false;
        }

        return true;
    }

    private IMessageResponse doSend(MessageChannel ch, IEnvelop envelop) {
        DefaultMessageRequest request = new DefaultMessageRequest();
        request.setTemplateId(envelop.getTemplateId());
        request.addEnvelop(ch, envelop);

        return messageProcessor.process(request);
    }

    public boolean sendSms(IEnvelop envelop) {
        IMessageResponse iMessageResponse = doSend(MessageChannel.SMS, envelop);

        if (iMessageResponse != DefaultMessageResponse.SUCCESS) {
            return false;
        }

        return true;
    }

    public boolean sendEmail(String title, String templateId, Map<String, Object> paramMap, String... recipients) {
        IMessageResponse iMessageResponse = doSend(MessageChannel.EMAIL, new SimpleEnvelop(title, templateId,
                                                                                           IEnvelop.LEVEL.LEVEL_NORMAL,
                                                                                           paramMap, recipients));

        if (iMessageResponse != DefaultMessageResponse.SUCCESS) {
            return false;
        }

        return true;
    }

    public boolean sendEmail(IEnvelop envelop) {
        IMessageResponse iMessageResponse = doSend(MessageChannel.EMAIL, envelop);

        if (iMessageResponse != DefaultMessageResponse.SUCCESS) {
            return false;
        }

        return true;
    }

    public IMessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(IMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
