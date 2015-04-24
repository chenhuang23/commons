package com.github.commons.message.server.mail;

import com.github.commons.message.MessageChannel;
import com.github.commons.message.server.IMessageSender;
import com.github.commons.message.server.template.ResolvedEnvelop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
@Service
public class NeteaseMailSender implements IMessageSender {

    @Autowired
    private MailSender internalMailSender;

    @Override
    public MessageChannel getMessageChannel() {
        return MessageChannel.EMAIL;
    }

    @Override
    public boolean send(ResolvedEnvelop envelop) {
        // todo: not impl yet
        throw new UnsupportedOperationException("not impl yet");
    }
}
