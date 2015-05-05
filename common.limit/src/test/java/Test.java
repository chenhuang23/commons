/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

/**
 * Test.java
 *
 * @author zhouxiaofeng 4/29/15
 */
public class Test {

    CyclicBarrier barrier = new CyclicBarrier(1);
    LimitDemo     demo;

    public void start() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:bean.xml");

        demo = ctx.getBean("demo", LimitDemo.class);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Work(i));
        }

        // executorService.shutdown();
    }

    class Work implements Runnable {

        int index = 0;

        public Work(int index){
            this.index = index;
        }

        @Override
        public void run() {

            while (true) {
                // System.out.println("wait--" + index);

                // try {
                // barrier.await();
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // } catch (BrokenBarrierException e) {
                // e.printStackTrace();
                // }

                try {
                    demo.sayHello();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                // System.out.println(index + ":" + " work.....");

                try {
                    TimeUnit.MILLISECONDS.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        Test t = new Test();
        t.start();
    }
}
