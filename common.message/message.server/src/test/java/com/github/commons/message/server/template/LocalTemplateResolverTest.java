package com.github.commons.message.server.template;

import com.github.commons.message.MessageChannel;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class LocalTemplateResolverTest {

    @org.testng.annotations.Test
    public void testResolve() throws Exception {
        URL url = LocalTemplateResolver.class.getResource("/welcome.sms");
        File f = new File(url.getFile());

        LocalTemplateResolver templateResolver = new LocalTemplateResolver(f.getParent());

        Map<String, Object> params = new HashMap<>();
        params.put("username", "evans");

        String content = templateResolver.resolve(MessageChannel.SMS, "welcome", params);
        assertEquals(content, "welcome evans");
    }
}