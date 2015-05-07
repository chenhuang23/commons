package com.github.commons.message.server.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.commons.message.*;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-commons-server.xml");
        IMessageProcessor processor = context.getBean("messageProcessor", IMessageProcessor.class);
        System.out.println(processor);

        DefaultMessageRequest request = new DefaultMessageRequest();
        request.addEnvelop(MessageChannel.SMS, new SimpleEnvelop("test", "test_teid", IEnvelop.LEVEL.LEVEL_HIGH, null,
                                                                 null));

        processor.process(request);

        while (true) {
            Thread.sleep(1000);
        }
    }
}
