package com.github.commons.security.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.github.commons.security.constants.EncryptType;

/**
 * �����������
 */
public class ReqParams implements Serializable {

    private static final long serialVersionUID = -6253593483223395584L;
    // Ӧ��Id
    public String             appCode;

    // �汾
    public int                version          = 1;

    // ǩ����Կ��Ƭ��Ϣ
    public int                shareKey;

    // ��������
    public EncryptType        secType;

    // ���ʵ�ʱ��
    private long              currentTime;

    public ReqParams(String appCode, int version, EncryptType type){
        this.appCode = appCode;
        this.secType = type;
        this.version = version;
    }

    public boolean validate() {
        return notBlank(appCode) && secType != null;

    }

    protected boolean notBlank(String val) {
        return StringUtils.isNotBlank(val);
    }

}
