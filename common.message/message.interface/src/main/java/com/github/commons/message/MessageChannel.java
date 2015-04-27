package com.github.commons.message;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 消息下行渠道
 */
public enum MessageChannel {
    ALL("all"), SMS("sms"), EMAIL("email"), WEIXIN("wx"), YIXIN("yx");

    public final String suffix;

    private MessageChannel(String suffix) {
        this.suffix = suffix;
    }
}
