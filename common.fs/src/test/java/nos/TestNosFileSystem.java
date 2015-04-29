/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package nos;

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
        InputStream resourceAsStream = TestNosFileSystem.class.getResourceAsStream("/11.jpg");

        system.writeFile("test_file_jpg_yyyy.jpg", FileType.IMG, resourceAsStream);
    }

    @Test
    public void testTestReadFile() throws Exception {
        InputStream test_file = system.getFile("test_file_jpg_yyyy.jpg", FileType.IMG);

        FileUtils.copyInputStreamToFile(test_file, new File("test_file2"));
    }

    @Test
    public void testTestGenerateUrl() throws Exception {
        String generateUrl = system.generateUrl("test_file_jpg_yyyy.jpg", FileType.IMG, 10000);

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
