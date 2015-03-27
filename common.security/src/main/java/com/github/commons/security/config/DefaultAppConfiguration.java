/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.commons.security.constants.EncryptType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.security.support.ReqParams;
import com.github.commons.security.constants.Constants;
import com.github.commons.security.spi.AppConfigurationSpi;
import com.github.commons.utils.format.JsonUtils;

/**
 * DefaultAppConfiguration.java 默认的配置,从配置文件中获取
 *
 * @author zhouxiaofeng 3/19/15
 */
public class DefaultAppConfiguration implements AppConfigurationSpi {

    private static final Logger  logger = LoggerFactory.getLogger(DefaultAppConfiguration.class);

    private Map<String, AppInfo> appMap = null;

    public DefaultAppConfiguration(){

        InputStream resourceAsStream = DefaultAppConfiguration.class.getClassLoader().getResourceAsStream(Constants.DEFAULT_APP_CONF_FILE);
        Properties pro = new Properties();
        try {
            pro.load(resourceAsStream);
        } catch (IOException e) {

            logger.error("Load " + Constants.DEFAULT_APP_CONF_FILE + " exception.", e);
        }

        String appinfoStr = pro.getProperty("appinfo");

        logger.warn("[LOAD APP INFO:]" + appinfoStr);

        AppInfo[] appInfos = null;
        if (StringUtils.isNotBlank(appinfoStr)) {
            appInfos = JsonUtils.fromJson(appinfoStr, AppInfo[].class);
        }

        if (appInfos != null) {

            appMap = new HashMap<String, AppInfo>(appInfos.length);
            for (AppInfo appinfo : appInfos) {
                appMap.put(appinfo.getAppCode(), appinfo);
                logger.info("[LOAD APP CONF]" + appinfo);
            }
        }

    }

    @Override
    public AppInfo[] findAll() {
        return appMap != null ? appMap.values().toArray(new AppInfo[0]) : null;
    }

    @Override
    public AppInfo lookup(String appCode) {
        return appMap != null ? appMap.get(appCode) : null;
    }

    @Override
    public AppInfo lookup(ReqParams params) {
        return lookup(params.appCode);
    }

    // ==================================

    public static void main(String[] args) {
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcc/zw9IqnDYSXIbiRnssB43cPMmQoUzZgRuRj\n"
                        + "mN4buhMv7l7OcnWw4/tP0hFHP+WOrrynDo1JyBMH5j8BYO2Xg5ma/FxwKHAr+mrvXbU9Y0vDzCWr\n"
                        + "rbMSeU0RK0zPyGVrKlOfeLAXBUQLQImwtJawB0BcoZ1671pJg7KMLAlhowIDAQAB";

        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJxz/PD0iqcNhJchuJGeywHjdw8y\n"
                        + "ZChTNmBG5GOY3hu6Ey/uXs5ydbDj+0/SEUc/5Y6uvKcOjUnIEwfmPwFg7ZeDmZr8XHAocCv6au9d\n"
                        + "tT1jS8PMJautsxJ5TRErTM/IZWsqU594sBcFRAtAibC0lrAHQFyhnXrvWkmDsowsCWGjAgMBAAEC\n"
                        + "gYEAk76Q0fcM7L6+RH5qrtGXAjyNVYOPw+j5A6hOy6MztFle/zeKvkimzZot3G4TNQapQLnQm64J\n"
                        + "TPCs0BvlyeZ/1scX9nyLNQEoWKhRezKKiYI6vyX016QO/43aSDxwLHFS6zNWF+YcOJsmDge4WKhH\n"
                        + "sPy0mpMsHeEA7qm7NgfpxskCQQDYtfWh0UESkioCfi01skMnYs43RM8aHZyPhn+QTYuJ+b0g1FVQ\n"
                        + "AY4Nv1ITnKZS9mC+ESm7sDikw8WDbtqjgzodAkEAuNFU3WJ2arIiHTXKC10fZqA9OdO/JFtEQ56o\n"
                        + "+S2Q87cOVAORXvjWytvWAvOy2xqKU+dQjI9xv4rZHi8Nu+Y+vwJACqko5ET/BoLaPjUm1DVoyE88\n"
                        + "BcwExCwgv47GR7sI2kjG3Q6VA9KPwm1fBEf4hqxIJhGCFBGfN7vJw6V4bALQoQJAAWEGehRm/8rO\n"
                        + "eFtTY5xRRKnDazAKSBIqQzrm1d0iLL9b6wKPzh6bM65KPYFl/z6Gc7PRJQtY9O3rSs9dEd7Y7wJA\n"
                        + "RAQFzFJFcStJZp0g3Z3FnNDuicJYzkrApG9hV5rI9W7yz+uevbxVQOTTUaHMH33XGNTfMpsc7Q23\n"
                        + "mJdGXc/CLw==";

        String desKey = "xsdfasdfasdf3egfadaa";

        AppInfo[] apparr = new AppInfo[1];
        apparr[0] = new AppInfo();
        apparr[0].setAppCode("test-code");

        SecKey secKey = new SecKey();
        secKey.setPriKey(priKey);
        secKey.setPubKey(pubKey);
        secKey.setVersion(1);
        secKey.setType(EncryptType.RSA.getType());

        SecKey secKey2 = new SecKey();
        secKey2.setPriKey(desKey);
        secKey2.setPubKey(desKey);
        secKey2.setVersion(1);
        secKey2.setType(EncryptType.DES.getType());

        SecKey secKey3 = new SecKey();
        secKey3.setPriKey(desKey);
        secKey3.setPubKey(desKey);
        secKey3.setVersion(1);
        secKey3.setType(EncryptType.XDES.getType());

        SecKey secKey4 = new SecKey();
        secKey4.setPriKey(priKey);
        secKey4.setPubKey(pubKey);
        secKey4.setVersion(1);
        secKey4.setType(EncryptType.XRSA.getType());

        apparr[0].setKeys(new SecKey[] { secKey, secKey2, secKey3, secKey4 });
        apparr[0].setLastVersion(1);
        apparr[0].setPolicy(Constants.VERSION_POLICY);

        System.out.println(JsonUtils.toJson(apparr));
    }
}
