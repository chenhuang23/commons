package com.github.commons.message.server.template;

import com.github.commons.message.server.constants.EmailEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.commons.message.client.SimpleMessageCenter;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-commons-client.xml");
        SimpleMessageCenter messageCenter = context.getBean("messageCenter", SimpleMessageCenter.class);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", "<html><head></head><body><h1>hello!!chao.wang</h1></body></html>");

        URL resource = Main.class.getResource("/AttachFile.txt");

        // param.put(EmailEnum.__Attachment_FILE.getKey(), new File(resource.toURI()));

        System.out.println(messageCenter.sendEmail("网易小贷,个人邮箱验证", "test.email", param,
                                                   "hzzhouxiaofeng@corp.netease.com"));

        // System.out.println(messageCenter.sendSms("test.sms", param, "13588461183"));

    }
}
