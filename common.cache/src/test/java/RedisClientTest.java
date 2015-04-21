import java.io.Serializable;

import com.github.commons.cache.CacheException;
import com.github.commons.cache.DefaultRedisClient;
import com.github.commons.cache.RedisClient;
import org.junit.Before;
import org.junit.Test;

public class RedisClientTest implements Serializable {

    private static final long serialVersionUID = 1L;
    private RedisClient<User> redisClient;

    @Before
    public void init() {
        redisClient = new DefaultRedisClient<>();

        //redisClient.setServers("redis://:xdcsrftest@10.165.124.77:6379");

        redisClient.setServers("10.240.176.240");

        redisClient.init();
    }

    @Test
    public void putAndGet() throws CacheException {
        int age = 25;
        String name = "mike";
        String key = "test";

        User val = new User();
        val.setAge(age);
        val.setName(name);
        this.getRedisClient().put(key, val);

        User user = this.getRedisClient().get(key);
        org.junit.Assert.assertNotNull(user);
        org.junit.Assert.assertEquals(25, user.getAge());
        org.junit.Assert.assertEquals(name, user.getName());
    }


    public RedisClient<User> getRedisClient() {
        return redisClient;
    }

}