package com.github.commons.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * <pre>
 * <p>文件名称: DefaultRedisClient.java</p>
 * 
 * <p>文件功能: redis 封装</p>
 * 
 * <p>编程者: xiaofeng.zhou</p>
 * 
 * <p>初作时间: 2014年7月8日 下午5:39:32</p>
 * 
 * <p>版本: version 1.0 </p>
 * 
 * <p>输入说明: </p>
 * 
 * <p>输出说明: </p>
 * 
 * <p>程序流程: </p>
 * 
 * <p>============================================</p>
 * <p>修改序号:</p>
 * <p>时间:	 </p>
 * <p>修改者:  </p>
 * <p>修改内容:  </p>
 * <p>============================================</p>
 * </pre>
 */
public class DefaultRedisClient<T extends Serializable, Object> extends AbstractCacheClient implements RedisClient<T> {

    private static final int    VAL_INT_ZERO        = 0;

    private static final String POOL_MAX_WAIT_MILLS = "poolMaxWaitMills";

    private static final String POOL_IDLE_SIZE      = "poolIdleSize";

    private static final String POOL_MAX_SIZE       = "poolMaxSize";

    private static final int    MAX_TOTAL           = 100;
    private static final long   MAX_WAIT_MILLIS     = 1000L * 10;
    private static final int    MAX_IDLE            = 1000 * 60;

    private ShardedJedisPool    pool;

    public void init() {
        if (!isInitd) {
            // 配置参数检查
            checkConfig();
            // 初始化缓存池
            initPool();
            isInitd = true;
        }
    }

    private void initPool() {
        List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(getServer().length);

        for (String server : getServer()) {
            //
            jdsInfoList.add(buildConfig(server));
        }

        pool = new ShardedJedisPool(configRedisPool(), jdsInfoList, Hashing.MURMUR_HASH,
                                    Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    private void checkConfig() {
        Assert.isNotNull("init error, cache server config not found.", this.getServer());
    }

    private JedisShardInfo buildConfig(String server) {
        Assert.isNotBlank("init error, cache server config error.", server);

        return new JedisShardInfo(server);
    }

    private JedisPoolConfig configRedisPool() {
        JedisPoolConfig redisConfig = new JedisPoolConfig();// Jedis池配置

        redisConfig.setMaxTotal(this.<Integer> getConfigVal(POOL_MAX_SIZE, MAX_TOTAL));

        redisConfig.setMaxIdle(this.<Integer> getConfigVal(POOL_IDLE_SIZE, MAX_IDLE));// 对象最大空闲时间

        redisConfig.setMaxWaitMillis(this.<Long> getConfigVal(POOL_MAX_WAIT_MILLS, MAX_WAIT_MILLIS));// 获取对象时最大等待时间
        return redisConfig;
    }

    public void destroy() {
        if (isInitd) {
            pool.destroy();
            isInitd = false;
        }
    }

    // =====================function================

    public void put(String key, T val) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);

        try {
            getJdies(key).set(key.getBytes(), SerializationUtils.serialize(val));
        } catch (Throwable e) {
            throw new CacheException("Put cache exception.", e);
        }

    }

    public void put(String key, T val, int expiredTime) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);

        try {
            getJdies(key).setex(key.getBytes(), expiredTime, SerializationUtils.serialize(val));
        } catch (Throwable e) {
            throw new CacheException("Put cache exception.", e);
        }
    }

    public Future<java.lang.Object> asyncGet(String key) throws CacheException {
        throw new CacheException("Not support.");
    }

    public T get(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            byte[] ret = getJdies(key).get(key.getBytes());
            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("get cache exception.", e);
        }
    }

    public void delete(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            getJdies(key).del(key);

        } catch (Throwable e) {
            throw new CacheException("delete cache exception.", e);
        }
    }

    public void incr(String key, int by, int expiredTime) throws CacheException {

        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotEqual("incr value is zero.", by, VAL_INT_ZERO);
        try {

            getJdies(key).incrBy(key, by);

        } catch (Throwable e) {
            throw new CacheException("incr cache exception.", e);
        }

    }

    public void incr(String key, int expiredTime) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            getJdies(key).incr(key);
            getJdies(key).expire(key, expiredTime);

        } catch (Throwable e) {
            throw new CacheException("incr cache exception.", e);
        }

    }

    public void expire(String key, int expiredTime) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            getJdies(key).expire(key, expiredTime);

        } catch (Throwable e) {
            throw new CacheException("expire cache exception.", e);
        }
    }

    // ========[list]==========>
    public void lpush(String key, T val) throws CacheException {

        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);
        try {
            getJdies(key).lpush(key.getBytes(), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            throw new CacheException("lpush cache exception.", e);
        }
    }

    public void rpush(String key, T val) throws CacheException {

        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);
        try {
            getJdies(key).rpush(key.getBytes(), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            throw new CacheException("rpush cache exception.", e);
        }
    }

    public T lpop(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            byte[] ret = getJdies(key).lpop(key.getBytes());

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("lpop cache exception.", e);
        }
    }

    public T rpop(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            byte[] ret = getJdies(key).rpop(key.getBytes());

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("rpop cache exception.", e);
        }
    }

    public T rindex(String key, long index) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            byte[] ret = getJdies(key).lindex(key.getBytes(), index);

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("rpop cache exception.", e);
        }
    }

    public Long rindex(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            return getJdies(key).llen(key);
        } catch (Throwable e) {
            throw new CacheException("rpop cache exception.", e);
        }
    }

    // =======[map]=============>>>>
    public void hset(String key, String field, T v) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        try {
            getJdies(key).hset(key.getBytes(), field.getBytes(), SerializationUtils.serialize(v));

        } catch (Throwable e) {
            throw new CacheException("hset cache exception.", e);
        }
    }

    public T hget(String key, String field) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        try {
            byte[] ret = getJdies(key).hget(key.getBytes(), field.getBytes());

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("hget cache exception.", e);
        }
    }

    public void hdel(String key, String field) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        try {
            getJdies(key).hdel(key.getBytes(), field.getBytes());
        } catch (Throwable e) {
            throw new CacheException("hget cache exception.", e);
        }
    }

    public Long hlen(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            return getJdies(key).hlen(key.getBytes());
        } catch (Throwable e) {
            throw new CacheException("hget cache exception.", e);
        }
    }

    // ======[set]========>>>
    public void sAdd(String key, T val) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null.", val);

        try {
            getJdies(key).sadd(key.getBytes(), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            throw new CacheException("sAdd cache exception.", e);
        }
    }

    public T sPop(String key) throws CacheException {
        // 1. 检查参数
        Assert.isNotBlank("key is blank.", key);

        try {
            byte[] ret = getJdies(key).spop(key.getBytes());
            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            throw new CacheException("sAdd cache exception.", e);
        }
    }

    private Jedis getJdies(String key) {
        return pool.getResource().getShard(key);
    }
}
