/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.nos;

import java.io.InputStream;

import com.github.commons.fs.FileReader;
import com.github.commons.fs.FileWriter;
import com.github.commons.fs.UserMetadata;
import com.github.commons.fs.exception.FileSystemException;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.NOSObject;
import com.netease.cloud.services.nos.model.ObjectMetadata;
import com.sun.tools.javac.util.Assert;
import org.apache.commons.lang.StringUtils;

/**
 * NosAbstractFile.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class NosFileSystem implements FileReader, FileWriter {

    private String             bucketName;
    private String             accessKey;
    private String             secretKey;

    private volatile NosClient nosClient = null;

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
    public InputStream getFile(String filename) {
        Assert.checkNonNull(bucketName);
        Assert.checkNonNull(nosClient);

        NOSObject nosObject = nosClient.getObject(bucketName, filename);

        if (nosObject != null) {
            return nosObject.getObjectContent();
        }

        return null;
    }

    @Override
    public void writeFile(String filename, InputStream inputStream, UserMetadata... userMetadatas) {
        Assert.checkNonNull(bucketName);
        Assert.checkNonNull(nosClient);

        ObjectMetadata objectMetadata = new ObjectMetadata();

        if (userMetadatas != null) {
            for (UserMetadata entry : userMetadatas) {
                if (entry != null && StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
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
