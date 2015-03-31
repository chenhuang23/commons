/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package nos;

import com.github.commons.fs.nos.NosFileSystem;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * TestNosFileSystem.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class TestNosFileSystem {

    NosFileSystem system;
    String        testBucketName = "";

    String        accessKey      = "";
    String        secretKey      = "";

    @Before
    public void init() {

        system = new NosFileSystem();
        system.setAccessKey(accessKey);
        system.setSecretKey(secretKey);
        system.setBucketName(testBucketName);

        system.init();
    }

    @Test
    public void testTestWriteFile() throws Exception {
        InputStream resourceAsStream = TestNosFileSystem.class.getResourceAsStream("/img.jpg");
        system.writeFile("test_file", resourceAsStream);
    }

    @Test
    public void testTestReadFile() throws Exception {
        InputStream test_file = system.getFile("test_file");

        FileUtils.copyInputStreamToFile(test_file, new File("test_file2"));
    }
}
