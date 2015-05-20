package com.github.commons.message.server.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.commons.message.MessageChannel;
import com.github.commons.message.server.IMessageSender;
import com.github.commons.message.server.template.ResolvedEnvelop;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
@Service
public class NeteaseMailSender implements IMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(NeteaseMailSender.class);

    @Autowired
    private MailSender          internalMailSender;

    @Override
    public MessageChannel getMessageChannel() {
        return MessageChannel.EMAIL;
    }

    @Override
    public boolean send(ResolvedEnvelop envelop) {

        try {

            internalMailSender.sendHtml(null, envelop.getRecipients(), null, null, envelop.getTitle(), envelop.getContent());

        } catch (Throwable e) {

            logger.error("Send mail exception.", e);

            return false;
        }
        return true;
    }
}
