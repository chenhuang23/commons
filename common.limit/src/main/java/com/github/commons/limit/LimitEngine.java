/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.limit;

import com.github.commons.limit.report.Statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 限制器引擎,提供Mbean方式获取
 * 
 * @author zhouxiaofeng 4/29/15
 */
public class LimitEngine {

    private boolean                              on                   = false;

    private static ServiceLoader<Statistics>     statisticses;
    private static Map<String, Map<String, Spy>> classLimitRepository = new HashMap<String, Map<String, Spy>>();

    static {
        statisticses = ServiceLoader.load(Statistics.class);

        configStatistics(new InnerHandler() {

            @Override
            public void stat(Statistics statistics) {
                statistics.setSpys(classLimitRepository);
            }
        });

    }

    public void setOn(boolean switchon) {

        boolean isChange = (on != switchon);

        if (isChange) {
            this.on = switchon;

            configStatistics(new InnerHandler() {

                @Override
                public void stat(Statistics statistics) {

                    if (on) {
                        statistics.start();
                    } else {
                        statistics.stop();
                    }
                }
            });
        }
    }

    public boolean isOn() {
        return on;
    }

    public void add(String className, Map<String, Spy> spyMap) {
        classLimitRepository.put(className, spyMap);
    }

    public Map<String, Map<String, Spy>> getClassLimitRepository() {
        return classLimitRepository;
    }

    private static void configStatistics(InnerHandler handler) {
        Iterator<Statistics> iterator = statisticses.iterator();
        while (iterator.hasNext()) {

            Statistics statistics = iterator.next();
            handler.stat(statistics);
        }
    }

    interface InnerHandler {

        void stat(Statistics statistics);

    }

}
