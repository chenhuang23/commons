package com.github.commons.message.server;

import com.github.commons.message.MessageChannel;
import com.github.commons.message.server.template.ResolvedEnvelop;

/**
 * Created by Yang Tengfei on 4/23/15.
 */
public interface IMessageSender {
    MessageChannel getMessageChannel();

    boolean send(ResolvedEnvelop envelop);
}
