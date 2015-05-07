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
        param.put("username", "hi.....mike");

        //System.out.println(messageCenter.sendEmail("this is a test", "test.email", param, "jason_zhouxf@163.com"));

        System.out.println(messageCenter.sendSms("test.sms", param, "13588461183"));

    }
}
