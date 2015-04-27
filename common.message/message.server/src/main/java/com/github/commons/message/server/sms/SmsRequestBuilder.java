package com.github.commons.message.server.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang Tengfei on 4/27/15.
 * <p/>
 * 构建一个短信请求
 */
public class SmsRequestBuilder {
    private static final String BASE_URL = "http://smsknl.163.com:8089/servlet/CorpIdentifyNotCheck";

    private final Map<String, String> params = new HashMap<>();

    public SmsRequestBuilder() {
        // default settings
        add("format", "15");
        add("send_time", String.valueOf(System.currentTimeMillis() / 1000));
        add("msgtype", "0");
        add("msgprop", "60214"); // todo: not set yet.
        add("corpinfo", "1");
    }

    private SmsRequestBuilder add(String key, String val) {
        params.put(key, val);
        return this;
    }

    public SmsRequestBuilder setRecipients(List<String> recipients) {
        if (recipients == null || recipients.isEmpty())
            throw new IllegalArgumentException("sms recipients cannot be empty!");

        String phone = "", fromPhone = "";

        for (String p : recipients) {
            p = p.trim();

            if (fromPhone.isEmpty())
                fromPhone = p;

            phone += p + "|";
        }

        params.put("phone", phone);
        params.put("frmphone", fromPhone);
        return this;
    }

    public SmsRequestBuilder setContent(String content) {
        params.put("message", Tools.HexToStr(content.getBytes()));
        return this;
    }

    public String toUrl() {
        String queryStr = "";

        for (Map.Entry<String, String> p : params.entrySet()) {
            queryStr += p.getKey() + "=" + p.getValue() + "&";
        }

        return BASE_URL + "?" + queryStr;
    }
}
