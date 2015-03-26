package com.github.commons.security;

import com.github.commons.security.constants.EncryptType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 加密请求参数
 */
public class ReqParams implements Serializable {

    private static final long serialVersionUID = -6253593483223395584L;
    // 应用Id
    public String             appCode;

    // 应用的接入密码
    public String             appKey;

    // 版本
    public int                version          = 1;

    // 签名秘钥分片信息
    public int                shareKey;

    // 加密类型
    public EncryptType        secType;

    // 访问的时间
    private long              currentTime;

    public ReqParams(String appCode, String appKey, int version, EncryptType type){
        this.appCode = appCode;
        this.appKey = appKey;
        this.secType = type;
        this.version = version;
    }

    public boolean validate() {
        return notBlank(appCode) && notBlank(appKey) && secType != null;

    }

    protected boolean notBlank(String val) {
        return StringUtils.isNotBlank(val);
    }

}
