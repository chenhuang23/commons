package test;

/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import com.netease.backend.nkv.client.NkvClient;
import com.netease.backend.nkv.client.error.NkvException;
import com.netease.backend.nkv.client.error.NkvFlowLimit;
import com.netease.backend.nkv.client.error.NkvRpcError;
import com.netease.backend.nkv.client.error.NkvTimeout;
import com.netease.backend.nkv.client.impl.AbstractNkvClient;
import com.netease.backend.nkv.client.impl.DefaultNkvClient;

/**
 * Main.java
 *
 * @author zhouxiaofeng 5/22/15
 */
public class Main {

    public static void main(String[] args) throws NkvTimeout, NkvRpcError, InterruptedException, NkvFlowLimit {
        final DefaultNkvClient client = new DefaultNkvClient();
        client.setMaster("127.0.0.1:3200"); // 提供nkv服务的master configserver url为：10.120.148.135:8200

        client.setSlave("127.0.0.1:3500"); // 提供nkv服务的slave configserver url为：10.120.148.135:8500

        client.setGroup("group_1"); // 目前提供的测试Group的名称为：group_1

        client.setCompressEnabled(true); // true表示启用压缩。默认为false

        client.setUseFastCompressed(true); // true使用快速压缩算法，false表示使用高压缩比算法。默认为true

        client.setCompressThreshold(10240); // 设置压缩阈值，当value长度大于该阈值时采用压缩，否则不压缩。默认为1000000

        client.setTimeout(300);

        try {
            client.init();
        } catch (NkvException e) {
            e.printStackTrace();
        }

        client.put("loan", "test".getBytes(), "111".getBytes(), new NkvClient.NkvOption(1));


        System.out.println(2222);

        client.close();
        client.shutdown();

    }
}
