package com.github.commons.message;

import java.util.Collections;
import java.util.List;

/**
 * Created by Yang Tengfei on 4/23/15.
 *
 *
 */
public class Recipient {

    private final MessageChannel channel;

    private final List<String> recipients;

    public Recipient(MessageChannel ch, List<String> recipients) {
        channel = ch;
        this.recipients = Collections.<String>unmodifiableList(recipients);
    }

    public MessageChannel getMessageChannel() {
        return channel;
    }

    List<String> getRecipients() {
        return recipients;
    }
}
