/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");

        LimitDemo demo = ctx.getBean("demo", LimitDemo.class);

        demo.sayHello();
    }
}
