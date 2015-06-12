package com.github.commons.cache.nkv;

import com.github.commons.cache.CacheClient;
import com.github.commons.cache.SimpleCache;

import java.io.Serializable;

/**
 * netease mkv配置
 * 
 * @param <T>
 */
public interface NKVClient<T extends Serializable> extends CacheClient, SimpleCache<T> {

}
