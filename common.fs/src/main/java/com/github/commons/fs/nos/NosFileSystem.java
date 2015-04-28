/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.nos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.fs.FileReader;
import com.github.commons.fs.FileWriter;
import com.github.commons.fs.UserMetadata;
import com.github.commons.fs.contants.FileType;
import com.github.commons.fs.exception.FileSystemException;
import com.github.commons.fs.utils.ImageUtils;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.NOSObject;
import com.netease.cloud.services.nos.model.ObjectMetadata;

/**
 * NosAbstractFile.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class NosFileSystem implements FileReader, FileWriter {

    private static final Logger logger    = LoggerFactory.getLogger(NosFileSystem.class);

    private String              bucketName;
    private String              accessKey;
    private String              secretKey;

    private volatile NosClient  nosClient = null;

    public void init() {
        nosClient = new NosClient(new BasicCredentials(accessKey, secretKey));

    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public InputStream getFile(String filename, FileType type) {
        isNotNUll(bucketName, "bucketName");
        isNotNUll(nosClient, "filename");
        isNotNUll(type, "fileType");

        switch (type) {
            case IMG:
                return nosClient.getImage(bucketName, filename);

            case ALL:
            default:
                NOSObject nosObject = nosClient.getObject(bucketName, filename);

                if (nosObject != null) {
                    return nosObject.getObjectContent();
                }
        }

        return null;
    }

    @Override
    public String generateUrl(String filename, FileType type, int millis) {

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND, millis);

        try {

            URL url = nosClient.generatePresignedUrl(bucketName, filename, instance.getTime());

            if (url != null) {
                return url.toExternalForm();
            }

            return null;

        } catch (Throwable e) {
            throw new FileSystemException("Nos file system write file exception.", e);
        }

    }

    @Override
    public void writeFile(String filename, FileType type, InputStream inputStream, UserMetadata... userMetadatas) {
        isNotNUll(bucketName, "bucketName");
        isNotNUll(nosClient, "filename");
        isNotNUll(type, "fileType");
        isNotNUll(inputStream, "inputStream");

        switch (type) {
            case IMG:
                // first check the img
                inputStream.mark(0);
                try {
                    if (!ImageUtils.check(inputStream)) {
                        throw new IllegalArgumentException("The image content is not legal.");
                    }
                } finally {
                    // not break;
                    try {
                        inputStream.reset();
                    } catch (IOException e) {
                        logger.error("Inputstream reset exception.", e);
                    }
                }

                // convert
                try {
                    inputStream = ImageUtils.convert(inputStream, ImageUtils.FORMAT_TYP_JPG);
                } catch (IOException e) {
                    throw new FileSystemException("Convert image exception.", e);
                }

            case ALL:
            default:
                ObjectMetadata objectMetadata = new ObjectMetadata();

                if (userMetadatas != null) {
                    for (UserMetadata entry : userMetadatas) {
                        if (entry != null && StringUtils.isNotBlank(entry.getKey())
                            && StringUtils.isNotBlank(entry.getValue())) {
                            objectMetadata.addUserMetadata(entry.getKey(), entry.getValue());
                        }
                    }
                }

                try {
                    nosClient.putObject(bucketName, filename, inputStream, objectMetadata);
                } catch (Throwable e) {
                    throw new FileSystemException("Nos file system write file exception.", e);
                }
        }

    }

    private void isNotNUll(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Params " + paramName + " can't be null.");
        }
    }
}
