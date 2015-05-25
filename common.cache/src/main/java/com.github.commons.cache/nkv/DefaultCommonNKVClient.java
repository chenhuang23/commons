/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.cache.nkv;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.commons.cache.AbstractCacheClient;
import com.github.commons.cache.Assert;
import com.github.commons.cache.CacheException;
import com.netease.backend.nkv.client.NkvClient;
import com.netease.backend.nkv.client.Result;
import com.netease.backend.nkv.client.error.NkvException;
import com.netease.backend.nkv.client.impl.DefaultNkvClient;

/**
 * DefaultNKVClient.java
 *
 * @author zhouxiaofeng 5/22/15
 */
public class DefaultCommonNKVClient extends AbstractCacheClient implements NKVClient {

    public static final int MIN_COMPRESS_THRESHOLD = 1024;
    public static final String UTF_8 = "utf-8";
    public static final int DEFAULT_VAL = 0;
    private DefaultNkvClient client;

    private String nameSpace;
    private String masterUrl;
    private String slaveUrl;
    private String group;
    private boolean compressEnable = true;
    private boolean fastCompressed = true;
    private int compressThreshold;
    private long timeOut = 2000;

    private NkvClient.NkvOption option = null;

    @Override
    public void init() {

        if (!isInitd) {
            // 配置参数检查
            config();
            isInitd = true;
        }
    }

    private void config() {

        Assert.isNotBlank("masterUrl is blank.", masterUrl);
        Assert.isNotBlank("slaveUrl is blank.", slaveUrl);
        Assert.isNotBlank("group is blank.", group);
        Assert.isNotBlank("nameSpace is blank.", nameSpace);

        client = new DefaultNkvClient();
        // 提供nkv服务的master
        client.setMaster(masterUrl);
        // 提供nkv服务的slave
        client.setSlave(slaveUrl);
        client.setGroup(group); // 目前提供的测试Group的名称为：group_1
        client.setCompressEnabled(compressEnable); // true表示启用压缩。默认为false
        client.setUseFastCompressed(fastCompressed); // true使用快速压缩算法，false表示使用高压缩比算法。默认为true
        client.setTimeout(300);

        if (compressThreshold > MIN_COMPRESS_THRESHOLD) {
            client.setCompressThreshold(compressThreshold); // 设置压缩阈值，当value长度大于该阈值时采用压缩，否则不压缩。默认为1000000
        }

        option = new NkvClient.NkvOption(timeOut);

        try {
            client.init();
        } catch (NkvException e) {
            throw new CacheException("Init nkv cache exceptin.", e);
        }

    }

    @Override
    public void destroy() {

        if (isInitd() && client != null) {
            client.close();
        }
    }

    @Override
    public void put(String key, Serializable val) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("val is null.", val);

        try {
            client.put(nameSpace, getByteArray(key), getByteArray(String.valueOf(val)), option);

        } catch (Throwable ex) {
            throw new CacheException("Put cache exception.", ex);
        }
    }

    @Override
    public void put(String key, Serializable val, int expiredTime) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);
        Assert.isNotNull("val is null.", val);
        Assert.isTrue("expiredTime must large 0.", expiredTime > 0);

        try {
            client.put(nameSpace, getByteArray(key), getByteArray(String.valueOf(val)),
                    new NkvClient.NkvOption(expiredTime));

        } catch (Throwable ex) {
            throw new CacheException("Put cache exception.", ex);
        }
    }

    @Override
    public Future<java.lang.Object> asyncGet(String key) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        try {
            final Future<Result<byte[]>> async = client.getAsync(nameSpace, getByteArray(key), option);

            return new Future<java.lang.Object>() {

                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return async.cancel(mayInterruptIfRunning);
                }

                @Override
                public boolean isCancelled() {
                    return async.isCancelled();
                }

                @Override
                public boolean isDone() {
                    return async.isDone();
                }

                @Override
                public java.lang.Object get() throws InterruptedException, ExecutionException {
                    Result<byte[]> result = async.get();

                    if (result.getCode().equals(Result.ResultCode.OK)) {

                        byte[] ret = result.getResult();

                        if (ret != null) {
                            try {
                                return new String(ret, UTF_8);
                            } catch (UnsupportedEncodingException e) {
                                throw new ExecutionException("new String exception.", e);
                            }
                        }
                    }

                    return null;
                }

                @Override
                public java.lang.Object get(long timeout, TimeUnit unit) throws InterruptedException,
                        ExecutionException, TimeoutException {
                    Result<byte[]> result = async.get(timeout, unit);

                    if (result.getCode().equals(Result.ResultCode.OK)) {

                        byte[] ret = result.getResult();

                        if (ret != null) {
                            try {
                                return new String(ret, UTF_8);
                            } catch (UnsupportedEncodingException e) {
                                throw new ExecutionException("new String exception.", e);
                            }
                        } else {
                            return null;
                        }
                    }

                    return null;
                }
            };

        } catch (Throwable ex) {
            throw new CacheException("Put cache exception.", ex);
        }

    }

    @Override
    public Serializable get(String key) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        Result<byte[]> result = null;
        try {
            result = client.get(nameSpace, getByteArray(key), option);

        } catch (Throwable ex) {
            throw new CacheException("get cache exception.", ex);
        }

        if (result != null && result.getCode().equals(Result.ResultCode.OK)) {

            byte[] ret = result.getResult();

            if (ret != null) {
                try {
                    return new String(ret, UTF_8);
                } catch (UnsupportedEncodingException e) {
                    throw new CacheException("new String exception.", e);
                }
            } else {
                return null;
            }

        }

        throw new CacheException(MessageFormat.format("get cache failed. {1}",
                result != null ? result.getCode() : "Null code"));
    }

    @Override
    public void delete(String key) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        Result<byte[]> result = null;
        try {
            client.remove(nameSpace, getByteArray(key), option);

        } catch (Throwable ex) {
            throw new CacheException("get cache exception.", ex);
        }

    }

    @Override
    public void incr(String key, int by, int expiredTime) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        Result<byte[]> result = null;
        try {
            client.incr(nameSpace, getByteArray(key), by, DEFAULT_VAL, option);

        } catch (Throwable ex) {
            throw new CacheException("get cache exception.", ex);
        }

    }

    @Override
    public void decr(String key, int by, int expiredTime) throws CacheException {
        checkInited();
        Assert.isNotBlank("key is blank.", key);

        Result<byte[]> result = null;
        try {
            client.decr(nameSpace, getByteArray(key), by, DEFAULT_VAL, option);

        } catch (Throwable ex) {
            throw new CacheException("get cache exception.", ex);
        }

    }

    private byte[] getByteArray(String value) {

        try {
            return value.getBytes(UTF_8);
        } catch (UnsupportedEncodingException e) {

            throw new CacheException("Get bytes exception.", e);
        }

    }

    // =====================================================
    public void setCompressEnable(boolean compressEnable) {
        this.compressEnable = compressEnable;
    }

    public void setCompressThreshold(int compressThreshold) {
        this.compressThreshold = compressThreshold;
    }

    public void setFastCompressed(boolean fastCompressed) {
        this.fastCompressed = fastCompressed;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    public void setSlaveUrl(String slaveUrl) {
        this.slaveUrl = slaveUrl;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}