package com.github.commons.cache.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang.SerializationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import com.github.commons.cache.AbstractCacheClient;
import com.github.commons.cache.Assert;
import com.github.commons.cache.CacheException;

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

    private static final Logger logger              = LoggerFactory.getLogger(DefaultRedisClient.class);

    private static final int    VAL_INT_ZERO        = 0;

    private static final String POOL_MAX_WAIT_MILLS = "poolMaxWaitMills";

    private static final String POOL_IDLE_SIZE      = "poolIdleSize";

    private static final String POOL_MAX_SIZE       = "poolMaxSize";

    private static final String UTF_8               = "utf-8";

    private static final int    MAX_TOTAL           = 100;
    private static final long   MAX_WAIT_MILLIS     = 1000L * 10;
    private static final int    MAX_IDLE            = 1000 * 60;

    private ShardedJedisPool    pool;

    private boolean             throwException;

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
        if (isInitd && pool != null) {
            pool.destroy();
            isInitd = false;
        }
    }

    // =====================function================

    @Override
    public void setThrowException(boolean isThrow) {
        this.throwException = isThrow;
    }

    public void put(String key, T val) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).set(key.getBytes(UTF_8), SerializationUtils.serialize(val));
        } catch (Throwable e) {
            failDealForVoid("Put cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

    }

    public void put(String key, T val, int expiredTime) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).setex(key.getBytes(UTF_8), expiredTime, SerializationUtils.serialize(val));
        } catch (Throwable e) {
            failDealForVoid("Put cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public Future<java.lang.Object> asyncGet(String key) throws CacheException {
        throw new CacheException("Not support.");
    }

    public T get(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            byte[] ret = getJdies(shareJedis, key).get(key.getBytes(UTF_8));
            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("get cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
        return null;
    }

    public byte[] getBytes(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            return getJdies(shareJedis, key).get(key.getBytes(UTF_8));

        } catch (Throwable e) {
            failDealForVoid("getBytes cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    public void delete(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).del(key);

        } catch (Throwable e) {
            failDealForVoid("delete cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    /**
     * 如果key不存在默认值就是0
     *
     * @param key
     * @param by
     * @param expiredTime
     * @throws CacheException
     */
    public Integer incr(String key, int by, int expiredTime) throws CacheException {

        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotEqual("incr value is zero.", by, VAL_INT_ZERO);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            Long aLong = getJdies(shareJedis, key).incrBy(key, (long) by);
            getJdies(shareJedis, key).expire(key, expiredTime);

            if (aLong != null) {
                return aLong.intValue();
            }

        } catch (Throwable e) {
            failDealForVoid("incr cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    /**
     * 如果key不存在默认值就是0
     * 
     * @param key
     * @param by
     * @param expiredTime
     * @throws CacheException
     */
    public Integer decr(String key, int by, int expiredTime) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            Long aLong = getJdies(shareJedis, key).decrBy(key, by);
            getJdies(shareJedis, key).expire(key, expiredTime);

            if (aLong != null) {
                return aLong.intValue();
            }

        } catch (Throwable e) {
            failDealForVoid("decr cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    @Override
    public Integer getIntForIncrAndDecr(String key) throws CacheException {
        byte[] bytes = this.getBytes(key);
        if (bytes == null || bytes.length == 0) return 0;
        try {
            return Integer.valueOf(new String(bytes, "utf-8"));
        } catch (Throwable e) {
        }
        return null;
    }

    public void expire(String key, int expiredTime) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).expire(key, expiredTime);

        } catch (Throwable e) {
            failDealForVoid("expire cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    // ========[list]==========>
    public void lpush(String key, T val) throws CacheException {

        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).lpush(key.getBytes(UTF_8), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            failDealForVoid("lpush cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public void rpush(String key, T val) throws CacheException {

        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null", val);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).rpush(key.getBytes(UTF_8), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            failDealForVoid("rpush cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public T lpop(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            byte[] ret = getJdies(shareJedis, key).lpop(key.getBytes(UTF_8));

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("lpop cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    public T rpop(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            byte[] ret = getJdies(shareJedis, key).rpop(key.getBytes(UTF_8));

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("rpop cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    public T rindex(String key, long index) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            byte[] ret = getJdies(shareJedis, key).lindex(key.getBytes(UTF_8), index);

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("rpop cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    public Long rindex(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            return getJdies(shareJedis, key).llen(key);
        } catch (Throwable e) {
            failDealForVoid("rpop cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    // =======[map]=============>>>>
    public void hset(String key, String field, T v) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).hset(key.getBytes(UTF_8), field.getBytes(), SerializationUtils.serialize(v));

        } catch (Throwable e) {
            failDealForVoid("hset cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public T hget(String key, String field) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        ShardedJedis shareJedis = getShareJedis();

        try {
            byte[] ret = getJdies(shareJedis, key).hget(key.getBytes(UTF_8), field.getBytes(UTF_8));

            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("hget cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    public void hdel(String key, String field) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotBlank("field is blank.", field);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).hdel(key.getBytes(UTF_8), field.getBytes(UTF_8));
        } catch (Throwable e) {
            failDealForVoid("hdel cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public Long hlen(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            return getJdies(shareJedis, key).hlen(key.getBytes(UTF_8));
        } catch (Throwable e) {
            failDealForVoid("hlen cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    // ======[set]========>>>
    public void sAdd(String key, T val) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("value is null.", val);
        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            getJdies(shareJedis, key).sadd(key.getBytes(UTF_8), SerializationUtils.serialize(val));

        } catch (Throwable e) {
            failDealForVoid("sAdd cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }
    }

    public T sPop(String key) throws CacheException {
        // 1. 检查参数
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        ShardedJedis shareJedis = null;
        try {
            shareJedis = getShareJedis();

            byte[] ret = getJdies(shareJedis, key).spop(key.getBytes(UTF_8));
            if (ret == null) {
                return null;
            }

            return (T) SerializationUtils.<T> deserialize(ret);

        } catch (Throwable e) {
            failDealForVoid("sPop cache exception.", e);
        } finally {
            retShareJedis(shareJedis);
        }

        return null;
    }

    private void failDealForVoid(String message, Throwable ex) {
        if (throwException) {
            throw new CacheException(message, ex);
        } else {
            logger.error(message, ex);
            return;
        }
    }

    private Jedis getJdies(ShardedJedis shardedJedis, String key) {
        return shardedJedis.getShard(key);
    }

    private ShardedJedis getShareJedis() {
        return pool.getResource();
    }

    private void retShareJedis(ShardedJedis shareJedis) {
        if (shareJedis != null) {
            pool.returnBrokenResource(shareJedis);
        }
    }
}
