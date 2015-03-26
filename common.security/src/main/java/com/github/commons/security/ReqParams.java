package com.github.commons.security;

import com.github.commons.security.constants.EncryptType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * �����������
 */
public class ReqParams implements Serializable {

    private static final long serialVersionUID = -6253593483223395584L;
    // Ӧ��Id
    public String             appCode;

    // Ӧ�õĽ�������
    public String             appKey;

    // �汾
    public int                version          = 1;

    // ǩ����Կ��Ƭ��Ϣ
    public int                shareKey;

    // ��������
    public EncryptType        secType;

    // ���ʵ�ʱ��
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
