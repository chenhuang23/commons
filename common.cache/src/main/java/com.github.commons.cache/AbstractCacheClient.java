package com.github.commons.cache;

import java.net.InetSocketAddress;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <p>文件名称: AbstractCacheClient.java</p>
 * 
 * <p>文件功能: 缓存基类</p>
 * 
 * <p>编程者: xiaofeng.zhou</p>
 * 
 * <p>初作时间: 2014年7月8日 下午4:54:28</p>
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
public abstract class AbstractCacheClient implements CacheClient {

    private static Logger         log     = LoggerFactory.getLogger(AbstractCacheClient.class);
    protected Map<String, String> config;
    protected String[]            servers;
    protected volatile boolean    isInitd = false;

    public Map<String, String> getConfigs() {
        return config;
    }

    public void setConfigs(Map<String, String> config) {
        this.config = config;
    }

    public void setServers(String server) {
        if (StringUtils.isNotBlank(server)) {
            server = server.replace(";", ",");
            servers = server.split(",");
        }
    }

    /**
     * <pre>
     * Description：获取配置的服务器数组
     * @return
     * @return String[]
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No： 
     * Modified By： 
     * Modified Date： 
     * Modified Description: 
     * <p>============================================</p>
     * </pre>
     */
    public String[] getServer() {
        return servers;
    }

    /**
     * <pre>
     * Description：获取配置的服务器地址列表
     * @return
     * @return List<InetSocketAddress>
     * @author name：xiaofeng.zhou
     * <p>============================================</p>
     * Modified No： 
     * Modified By： 
     * Modified Date： 
     * Modified Description: 
     * <p>============================================</p>
     * </pre>
     */
    public List<InetSocketAddress> getAddressWithString() {
        if (servers == null || servers.length == 0) {
            return null;
        }
        return getAddresses(Arrays.asList(servers));
    }

    @SuppressWarnings("unchecked")
    public <C> C getConfigVal(String key, C defValue) {

        if (defValue.getClass().isAssignableFrom(Integer.class)) {
            String ret = this.getStrConfigVal(key, null);

            if (StringUtils.isBlank(ret)) {
                return defValue;
            } else {
                try {
                    return (C) Integer.valueOf(ret);
                } catch (NumberFormatException e) {
                    log.error("Format config exception.", e);
                    return defValue;
                }
            }
        } else if (defValue.getClass().isAssignableFrom(Long.class)) {
            String ret = this.getStrConfigVal(key, null);

            if (StringUtils.isBlank(ret)) {
                return defValue;
            } else {
                try {
                    return (C) Long.valueOf(ret);
                } catch (NumberFormatException e) {
                    log.error("Format config exception.", e);
                    return defValue;
                }
            }
        }
        return defValue;

    }

    public String getStrConfigVal(String key, String defValue) {
        return config == null || !config.containsKey(key) ? defValue : config.get(key);
    }

    private List<InetSocketAddress> getAddresses(List<String> servers) {
        ArrayList addrs = new ArrayList(servers.size());
        Iterator i$ = servers.iterator();

        while (i$.hasNext()) {
            String server = (String) i$.next();
            int finalColon = server.lastIndexOf(58);
            if (finalColon < 1) {
                throw new IllegalArgumentException("Invalid server ``" + server + "\'\' in list:  " + server);
            }

            String hostPart = server.substring(0, finalColon);
            String portNum = server.substring(finalColon + 1);
            addrs.add(new InetSocketAddress(hostPart, Integer.parseInt(portNum)));
        }

        if (addrs.isEmpty()) {
            throw new IllegalArgumentException("servers cannot be empty");
        } else {
            return addrs;
        }
    }
}
