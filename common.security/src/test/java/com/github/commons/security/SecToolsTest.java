/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.security;

import com.github.commons.security.config.AppInfo;
import com.github.commons.security.constants.EncryptType;
import com.github.commons.security.constants.SecPolicy;
import com.github.commons.security.spi.AppConfigurationSpi;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SecToolsTest.java
 *
 * @author zhouxiaofeng 3/27/15
 */
public class SecToolsTest {

    private final String plaintext     = "this is a test.";

    private final String encodeCode    = "WQEPC2BBF65166C1105519561A922389BE67";

    private final String xencodeCode   = "WQEP;GE6<<G<84EE7=E:I8I4J=8=78E58EHG";

    private final String aesencodeCode = "WQEP7zoQllHCEphT8LPHu+EBrg==";

    private final String signCode      = "WQEPN0nIty/2b9PB+uy3kAovCNVJ83XAkC9oh3R46R4iqQjpJhcePe5vngWDcs7WrqaXoTfthv7ctA1J\n"
                                         + "OA3mM+QiFVGgSRLmlzgCgq6bBGR48R5vIsel3NP/kbbRHeBTzt04/vV8bW8lIns9TT7lkqh26+oM\n"
                                         + "G5S6F83/UCN2fB7NkdM=";

    private final String xsignCode     = "WQEPR4rMx}36f=TF/y}7oEszGRZN<7\\EoG=sl7V8:V8muUntNlgiTi9zrk[Hgw;[vue\\sXjxlz;gxE5N\u000ESE7qQ/UmJZKkWVPqp~kGku:fFKV8<V9zMwip7RT3offVLiFX~x483zZ<f[<pMrw=XX;poul6:/sQ\u000EK9W:J<73YGR6jF;RohQA";

    SecTools             tools         = new SecTools("test-code", SecPolicy.LOCAL, 1);

    @Test
    public void testDes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String encode = tools.codecTool().encode(plaintext, EncryptType.DES);

        System.out.println(encode);

        Assert.assertEquals(encode, encodeCode);

        String decode = tools.codecTool().decode(encode, EncryptType.DES);

        Assert.assertEquals(decode, plaintext);
    }

    @Test
    public void testAes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String encode = tools.codecTool().encode(plaintext, EncryptType.AES);

        System.out.println(encode);

        Assert.assertEquals(encode, aesencodeCode);

        String decode = tools.codecTool().decode(encode, EncryptType.AES);

        Assert.assertEquals(decode, plaintext);
    }

    @Test
    public void testSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String sign = tools.signTool().sign(plaintext, EncryptType.RSA);

        System.out.println(sign);

        Assert.assertEquals(sign, signCode);

        Assert.assertTrue(tools.signTool().validateSign(plaintext, sign, EncryptType.RSA));
    }

    @Test
    public void testBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:beans.xml");
        AppConfigurationSpi bean = ctx.getBean(AppConfigurationSpi.class);

        AppInfo[] all = bean.findAll();

        for (AppInfo info : all) {
            System.out.println(info);
        }

    }
}
