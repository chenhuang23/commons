package test;
/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */

import java.io.Serializable;
import java.util.concurrent.Future;

import com.github.commons.cache.nkv.DefaultCommonNKVClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.netease.backend.nkv.client.error.NkvException;

/**
 * NkvClientTest.java
 *
 * @author zhouxiaofeng 5/22/15
 */
public class NkvClientTest {

    DefaultCommonNKVClient client = null;

    String                 key    = "test";
    String                 value  = "123";

    @Before
    public void init() throws NkvException {
        client = new DefaultCommonNKVClient();



        // nkv保证系统的高可用
//        ssh -L 3200:10.120.148.135:3200 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 3500:10.120.148.135:3500 hzzhouxiaofeng@10.165.124.82 -p 1046


//        ssh -L 4091:10.120.148.135:4091 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4191:10.120.148.135:4191 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4291:10.120.148.135:4291 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4391:10.120.148.135:4391 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4491:10.120.148.135:4491 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4591:10.120.148.135:4591 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4691:10.120.148.135:4691 hzzhouxiaofeng@10.165.124.82 -p 1046
//        ssh -L 4791:10.120.148.135:4791 hzzhouxiaofeng@10.165.124.82 -p 1047

//    master    10.120.148.135:3200
//    slave     10.120.148.135:3500

        client.setMasterUrl("127.0.0.1:3200"); // 提供nkv服务的master configserver url为：10.120.148.135:8200

        client.setSlaveUrl("127.0.0.1:3500"); // 提供nkv服务的slave configserver url为：10.120.148.135:8500

        client.setGroup("group_1"); // 目前提供的测试Group的名称为：group_1

        client.setNameSpace("loan");

        client.setCompressEnable(true); // true表示启用压缩。默认为false

        client.setFastCompressed(true); // true使用快速压缩算法，false表示使用高压缩比算法。默认为true

        client.setCompressThreshold(10240); // 设置压缩阈值，当value长度大于该阈值时采用压缩，否则不压缩。默认为1000000

        client.setTimeOut(3000);

        client.init();
    }

    private void assertEquals(Object ok, Object code) {
        Assert.assertEquals(ok, code);
    }

    public void destroy() {
        if (client != null) client.destroy();
    }

    // ============================

    /**
     * Method: put(String key, Serializable val)
     */
    @Test
    public void testPutForKeyVal() throws Exception {
        client.put(key, value);

        Serializable serializable = client.get(key);

        Assert.assertEquals(serializable.toString(), value);

    }

    /**
     * Method: put(String key, Serializable val, int expiredTime)
     */
    @Test
    public void testPutForKeyValExpiredTime() throws Exception {
        client.put(key, value, 1000);

        Serializable serializable = client.get(key);

        Assert.assertEquals(serializable.toString(), value);
    }

    /**
     * Method: asyncGet(String key)
     */
    @Test
    public void testAsyncGet() throws Exception {
        client.put(key, value, 1000);
        Future<Object> objectFuture = client.asyncGet(key);

        Assert.assertEquals(objectFuture.get().toString(), value);
    }

    /**
     * Method: delete(String key)
     */
    @Test
    public void testDelete() throws Exception {
        client.put(key, value);

        Serializable serializable = client.get(key);

        Assert.assertEquals(serializable.toString(), value);

        client.delete(key);
        serializable = client.get(key);
        Assert.assertEquals(serializable, null);
    }

    /**
     * Method: incr(String key, int by, int expiredTime)
     */
    @Test
    public void testIncr() throws Exception {
        // TODO: Test goes here...
    }

    /**
     * Method: decr(String key, int by, int expiredTime)
     */
    @Test
    public void testDecr() throws Exception {
        // TODO: Test goes here...
    }
}
