package com.github.commons.message.server.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.commons.message.client.SimpleMessageCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-commons-client.xml");
        SimpleMessageCenter messageCenter = context.getBean("messageCenter", SimpleMessageCenter.class);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "<html><head></head><body><h1>hello!!chao.wang</h1></body></html>");

        System.out.println(messageCenter.sendEmail("网易小贷,个人邮箱验证", "test.email", param, "hzyangtengfei@corp.netease.com"));

        //System.out.println(messageCenter.sendSms("test.sms", param, "13588461183"));

    }
}
