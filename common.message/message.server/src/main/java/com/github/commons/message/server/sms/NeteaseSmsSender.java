package com.github.commons.message.server.sms;

import com.github.commons.message.MessageChannel;
import com.github.commons.message.server.IMessageSender;
import com.github.commons.message.server.template.ResolvedEnvelop;
import org.springframework.stereotype.Service;

/**
 * Created by Yang Tengfei on 4/24/15.
 * <p/>
 * 基于网易的消息处理中心来发送短信
 */
@Service
public class NeteaseSmsSender implements IMessageSender {

    private final MobileUtil mobileUtil = new MobileUtil();

    private final String MSG_GROUP = "12345";

    @Override
    public MessageChannel getMessageChannel() {
        return MessageChannel.SMS;
    }

    @Override
    public boolean send(ResolvedEnvelop envelop) {
        return mobileUtil.sendTxtSms(envelop.getContent(), MSG_GROUP, "5",
                envelop.getRecipients().toArray(new String[envelop.getRecipients().size()]));
    }
}
