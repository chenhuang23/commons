/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package nos;

import com.github.commons.fs.UserMetadata;
import com.github.commons.fs.contants.FileType;
import com.github.commons.fs.nos.NosFileSystem;
import com.github.commons.fs.utils.ImageUtils;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * TestNosFileSystem.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class TestNosFileSystem {

    NosFileSystem system;
    String        testBucketName = "loan-xproject-test";

    String        accessKey      = "64d19464c55d4f80b467d332e7391dc8";
    String        secretKey      = "967921571806489e8065bf28802d9d6a";

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

        String urlPath = "http://e.hiphotos.baidu.com/baike/w%3D268/sign=06657b30ec50352ab161220e6b42fb1a/7aec54e736d12f2e85aedae74ac2d56284356889.jpg";

        system.writeFile("test_shenfen__yyyy1.jpg", FileType.IMG, urlPath);

    }

    @Test
    public void testTestWriteFile2() throws Exception {

        InputStream resourceAsStream = TestNosFileSystem.class.getResourceAsStream("/back.jpg");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);

        system.writeFile("test_shenfen__yyyy1.jpg", FileType.IMG, bufferedInputStream);

    }

    @Test
    public void testTestReadFile() throws Exception {
        InputStream test_file = system.getFile("test_file_jpg_yyyy.jpg", FileType.IMG);

        FileUtils.copyInputStreamToFile(test_file, new File("test_file2"));
    }

    @Test
    public void testTestGenerateUrl() throws Exception {
        String generateUrl = system.generateUrl("test_shenfen__yyyy1.jpg", FileType.IMG, 10000000);

        System.out.println(generateUrl);
        Assert.assertNotNull(generateUrl);
    }

    // =====convert
    @Test
    public void testConvert() throws Exception {

        String[] FORMAT_TYP = { "bmp", "png", "jpg", "gif" };

        for (String type : FORMAT_TYP) {
            InputStream resourceAsStream = TestNosFileSystem.class.getResourceAsStream("/11.jpg");
            InputStream convert = ImageUtils.convert(resourceAsStream, type);
            FileUtils.copyInputStreamToFile(convert, new File("test_file_" + type + "." + type));
        }
    }
}
