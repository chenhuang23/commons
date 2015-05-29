/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import com.github.commons.limit.annotation.LimitFlag;
import com.github.commons.limit.annotation.LimitMethod;

/**
 * LimitDemo.java
 *
 * @author zhouxiaofeng 4/29/15
 */
@LimitFlag
public class LimitDemo {

    @LimitMethod(threshold = 5)
    public void sayHello() {
        // System.out.println("--------hello--------");

    }
}
