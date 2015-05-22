package com.github.commons.cache.redis;

import java.io.Serializable;

import com.github.commons.cache.CacheClient;
import com.github.commons.cache.CacheException;
import com.github.commons.cache.SimpleCache;

/**
 * <pre>
 * <p>文件名称: RedisClient.java</p>
 * 
 * <p>文件功能: redis 客户端封装</p>
 * 
 * <p>编程者: xiaofeng.zhou</p>
 * 
 * <p>初作时间: 2014年7月14日 上午11:07:52</p>
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
public interface RedisClient<T extends Serializable> extends CacheClient, SimpleCache<T> {

    /**
     * <pre>
     * Description：缓存过期
     * @param key 过期key
     * @param expiredTime 单位秒
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void expire(String key, int expiredTime) throws CacheException;

    // ========[list]==========>
    /**
     * <pre>
     * Description：添加一个项到列表左边
     * @param key
     * @param val
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void lpush(String key, T val) throws CacheException;

    /**
     * <pre>
     * Description：添加一个项到列表右边
     * @param key
     * @param val
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void rpush(String key, T val) throws CacheException;

    /**
     * <pre>
     * Description：从列表左边获取并删除一个列表项
     * @param key
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return T
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public T lpop(String key) throws CacheException;

    /**
     * <pre>
     * Description：从列表右边获取并删除一个列表项
     * @param key
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return T
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public T rpop(String key) throws CacheException;

    /**
     * <pre>
     * Description：获取一个元素，通过其索引列表
     * @param key
     * @param index
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return T
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public T rindex(String key, long index) throws CacheException;

    /**
     * <pre>
     * Description：获取列表长度
     * @param key
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return Long
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public Long rindex(String key) throws CacheException;

    // =======[map]=============>>>>
    /**
     * <pre>
     * Description：设置hash里面一个字段的值
     * @param key
     * @param field
     * @param v
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void hset(String key, String field, T v) throws CacheException;

    /**
     * <pre>
     * Description：读取哈希域的的值
     * @param key
     * @param field
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return T
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public T hget(String key, String field) throws CacheException;

    /**
     * <pre>
     * Description：删除map中key的所有项
     * @param key
     * @param field
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void hdel(String key, String field) throws CacheException;

    /**
     * <pre>
     * Description：获取hash里所有字段的数量
     * @param key
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return Long
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public Long hlen(String key) throws CacheException;

    // ======[set]========>>>
    /**
     * <pre>
     * Description：添加一个或者多个元素到集合(set)里
     * @param key
     * @param val
     * @throws com.github.commons.cache.CacheException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No：
     * Modified By：
     * Modified Date：
     * Modified Description:
     * <p>============================================</p>
     * </pre>
     */
    public void sAdd(String key, T val) throws CacheException;

    /**
     * <pre>
     * Description：删除并获取一个集合里面的元素
     * @param key
     * @return
     * @throws com.github.commons.cache.CacheException
     * @return T
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No： 
     * Modified By： 
     * Modified Date： 
     * Modified Description: 
     * <p>============================================</p>
     * </pre>
     */
    public T sPop(String key) throws CacheException;
}
