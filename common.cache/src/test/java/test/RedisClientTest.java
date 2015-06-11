package test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.github.commons.cache.CacheException;
import com.github.commons.cache.redis.DefaultRedisClient;
import com.github.commons.cache.redis.RedisClient;

public class RedisClientTest implements Serializable {

    private static final long serialVersionUID = 1L;
    private RedisClient<User> redisClient;

    @Before
    public void init() {
        redisClient = new DefaultRedisClient();

        // redisClient.setServers("redis://:xdcsrftest@10.165.124.109:6379");

        // redisClient.setServers("10.240.176.240");

        redisClient.setServers("redis://:xdcsrftest@127.0.0.1:6479");

        redisClient.init();
    }

    @Test
    public void putAndGet() throws CacheException {

        // int age = 25;
        // String name = "mike";
        String key = "test";
        //
        // User val = new User();
        // val.setAge(age);
        // val.setName(name);
        // this.getRedisClient().put(key, val);

        User user = (User) this.getRedisClient().get(key);

        System.out.println(user);
        org.junit.Assert.assertNotNull(user);
        org.junit.Assert.assertEquals(25, user.getAge());
        // org.junit.Assert.assertEquals(name, user.getName());

    }

    @Test
    public void incr() throws CacheException {

        Integer test = this.getRedisClient().incr("testINcr", 1, 10);

        System.out.println(test);

    }

    @Test
    public void get() throws CacheException, UnsupportedEncodingException {

        byte[] testINcrs = this.getRedisClient().getBytes("owVPosyg9fnn3YBaKe6tGs0DickM_LOGIN_FAILED");

        System.out.println(Integer.valueOf(new String(testINcrs, "utf-8")));

    }

    @Test
    public void del() throws CacheException, UnsupportedEncodingException {

        this.getRedisClient().delete("owVPosyg9fnn3YBaKe6tGs0DickM_LOGIN_FAILED");

    }

    @Test
    public void decr() throws CacheException {

        Integer test = this.getRedisClient().decr("OPENID123456_LOGIN_FAILED", 1, 100);

        System.out.println(test);

    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

}
