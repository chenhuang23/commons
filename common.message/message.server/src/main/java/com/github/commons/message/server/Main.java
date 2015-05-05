package com.github.commons.message.server;

import com.github.commons.message.DefaultMessageRequest;
import com.github.commons.message.IMessageProcessor;
import com.github.commons.message.MessageChannel;
import com.github.commons.message.SimpleEnvelop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-commons-server.xml");
        IMessageProcessor processor = context.getBean("messageProcessor", IMessageProcessor.class);
        System.out.println(processor);

        DefaultMessageRequest request = new DefaultMessageRequest();
        request.addEnvelop(MessageChannel.SMS, new SimpleEnvelop("test_teid", null, null));

        processor.process(request);

        while (true) {
            Thread.sleep(1000);
        }
    }
}
