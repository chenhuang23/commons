package com.github.commons.cache;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * <pre>
 * <p>文件名称: SimpleCache.java</p>
 * 
 * <p>文件功能: 缓存服务基础服务接口</p>
 * 
 * <p>编程者: xiaofeng.zhou</p>
 * 
 * <p>初作时间: 2014年7月14日 上午8:55:04</p>
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
public interface SimpleCache<T extends Serializable> {

    /**
     * Description： 添加缓存项到缓存服务器，缓存项超时时间为默认值（配置文件中超时项值）
     * 
     * @param key 缓存键
     * @param val 缓存值
     * @throws CacheException ,IllegalArgumentException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>=
     * ===========================================
     * </p>
     * Modified No： Modified By： Modified Date： Modified Description:
     * <p>=
     * ===========================================
     * </p>
     */
    public void put(String key, T val) throws CacheException;

    /**
     * Description： 添加缓存项到缓存服务器
     * 
     * @param key 缓存键
     * @param val 缓存值
     * @param expiredTime 超时时间，单位秒
     * @throws CacheException ,IllegalArgumentException
     * @return void
     * @author name：xiaofeng.zhou
     * <p>=
     * ===========================================
     * </p>
     * Modified No： Modified By： Modified Date： Modified Description:
     * <p>=
     * ===========================================
     * </p>
     */
    public void put(String key, T val, int expiredTime) throws CacheException;

    /**
     * 异步获取缓存项值 Description：
     * 
     * @param key
     * @throws CacheException ,IllegalArgumentException
     * @return Future<Object>
     * @author name：xiaofeng.zhou
     * <p>=
     * ===========================================
     * </p>
     * Modified No： Modified By： Modified Date： Modified Description:
     * <p>=
     * ===========================================
     * </p>
     */
    public Future<Object> asyncGet(String key) throws CacheException;

    /**
     * <pre>
     * Description：获取缓存项
     * @param key
     * @return
     * @throws CacheException,IllegalArgumentException
     * @return Object
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No： 
     * Modified By： 
     * Modified Date： 
     * Modified Description: 
     * <p>============================================</p>
     * </pre>
     */
    public T get(String key) throws CacheException;

    /**
     * <pre>
     * Description：获取缓存项
     * @param key
     * @return
     * @throws CacheException,IllegalArgumentException
     * @return Object
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No： 
     * Modified By： 
     * Modified Date： 
     * Modified Description: 
     * <p>============================================</p>
     * </pre>
     */
    public byte[] getBytes(String key) throws CacheException;

    /**
     * <pre>
     * Description：
     * @param key
     * @throws CacheException,IllegalArgumentException
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
    public void delete(String key) throws CacheException;

    /**
     * <pre>
     * Description：
     * @param key
     * @param by
     * @param expiredTime
     * @throws CacheException,IllegalArgumentException
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
    public Integer incr(String key, int by, int expiredTime) throws CacheException;

    /**
     * <pre>
     *  Description：
     *  @param key
     *  @param expiredTime
     * @throws CacheException,IllegalArgumentException
     *  @return void
     *  @author name：xiaofeng.zhou
     *  <p>============================================</p>
     *  Modified No： 
     *  Modified By： 
     *  Modified Date： 
     *  Modified Description: 
     *  <p>============================================</p>
     * </pre>
     */
    public Integer decr(String key, int by, int expiredTime) throws CacheException;

    /**
     * 返回incr,decr结果值,如果key不存在返回null
     * 
     * @param key
     * @return
     * @throws CacheException
     */
    public Integer getIntForIncrAndDecr(String key) throws CacheException;

}
