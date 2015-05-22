package com.github.commons.cache.nkv;

import com.github.commons.cache.CacheClient;
import com.github.commons.cache.SimpleCache;

/**
 * netease mkv配置
 * 
 * @param <T>
 */
public interface NKVClient<T extends String> extends CacheClient, SimpleCache<T> {

}
