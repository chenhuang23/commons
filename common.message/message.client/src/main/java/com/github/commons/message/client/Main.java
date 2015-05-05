package com.github.commons.message.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-commons-client.xml");
        SimpleMessageCenter messageCenter = context.getBean("messageCenter", SimpleMessageCenter.class);
        messageCenter.sendEmail(null, "", null);
    }
}
