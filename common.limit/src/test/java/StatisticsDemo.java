/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.commons.limit.Spy;
import com.github.commons.limit.report.Statistics;

/**
 * StatisticsDemo.java
 *
 * @author zhouxiaofeng 4/30/15
 */
public class StatisticsDemo implements Statistics {

    private Map<String, Map<String, Spy>> classLimitRepository;
    private volatile boolean              isStop = false;

    @Override
    public void start() {

        new Thread() {

            @Override
            public void run() {

                while (!isStop) {

                    if (classLimitRepository != null) {
                        for (Map.Entry<String, Map<String, Spy>> entry : classLimitRepository.entrySet()) {

                            System.out.print(entry.getKey() + "-->");
                            Map<String, Spy> value = entry.getValue();

                            for (Spy spy : value.values()) {

                                System.out.println(" stat: " + spy.toString());
                            }
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();

    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public void setSpys(Map<String, Map<String, Spy>> classLimitRepository) {
        this.classLimitRepository = classLimitRepository;
    }

    @Override
    public Map<String, Map<String, Spy>> getSpys() {
        return classLimitRepository;
    }
}
