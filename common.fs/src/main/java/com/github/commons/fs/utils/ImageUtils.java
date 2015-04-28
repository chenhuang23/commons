/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.fs.utils;

import static java.util.concurrent.Executors.newFixedThreadPool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.devlib.schmidt.imageinfo.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImageUtils.java
 *
 * @author zhouxiaofeng 4/1/15
 */
public class ImageUtils {

    private static final Logger    logger                                   = LoggerFactory.getLogger(ImageUtils.class);

    public static final String     FORMAT_TYP_BMP                           = "bmp";
    public static final String     FORMAT_TYP_PNG                           = "png";
    public static final String     FORMAT_TYP_JPG                           = "jpg";
    public static final String     FORMAT_TYP_GIF                           = "gif";

    public static final String     GCF_IMAGE_POOLSIZE                       = "gcf.image.poolsize";
    public static final int        DEFAULT_SIZE                             = 10;
    public static final String     IMAGE_DEAL_DATA_HANDLER_GET_INPUT_STREAM = "ImageDeal.DataHandler.getInputStream";
    private static int             threadPoolSize                           = DEFAULT_SIZE;

    // 执行线程池
    private static ExecutorService executorService                          = newFixedThreadPool(threadPoolSize,
                                                                                                 new NamedThreadFactory(
                                                                                                                        IMAGE_DEAL_DATA_HANDLER_GET_INPUT_STREAM));

    static {

        if (System.getProperty(GCF_IMAGE_POOLSIZE) != null) {
            try {
                threadPoolSize = Integer.parseInt(System.getProperty(GCF_IMAGE_POOLSIZE));
            } catch (NumberFormatException e) {
                logger.error("Format poolsize exception.", e);
            }
        }
    }

    /**
     * check image
     * 
     * @param inputStream
     * @return
     */
    public static boolean check(InputStream inputStream) {

        isNotNUll(inputStream, "inputStream");

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setInput(inputStream);
        imageInfo.setCollectComments(false);
        imageInfo.setDetermineImageNumber(false);

        try {
            return imageInfo.check();

        } catch (Throwable e) {
            logger.error("check image exception.", e);
            return false;
        }

    }

    /**
     * <pre>
     * !!! 方法会启动一个线程，线程会等到流读完才结束，所以注意调用次数!!!
     * 
     * 图片格式转换
     * 
     * @param source
     * @param format 见 @code{ImageUtils} 公有常量
     * @see @code{javax.imageio.spi.IIORegistry.registerStandardSpis}
     * </pre>
     * 
     * @return
     */
    public static InputStream convert(final InputStream source, final String format) throws IOException {
        isNotNUll(source, "source");
        isNotNUll(format, "format");

        final BufferedImage read = ImageIO.read(source);

        final PipedInputStream pipedInputStream = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        executorService.submit(new Runnable() {

            public void run() {
                try {

                    ImageIO.write(read, format, pipedOutputStream);

                } catch (IOException e) {
                    logger.error("format image exception.", e);
                } finally {
                    try {
                        pipedInputStream.close();
                        pipedOutputStream.close();
                    } catch (IOException e) {
                        logger.error("format image exception.", e);
                    }

                }
            }
        });

        return pipedInputStream;
    }

    /**
     * *
     * 
     * <pre>
     * !!! 方法会启动一个线程，线程会等到流读完才结束，所以注意调用次数!!!
     * 
     * 图片格式转换
     * 
     * @param source
     * @param executorService
     * @param format 见 @code{ImageUtils} 公有常量
     * @see @code{javax.imageio.spi.IIORegistry.registerStandardSpis}
     * </pre>
     * 
     * @return
     * @throws IOException
     */
    public static InputStream convert(final InputStream source, final ExecutorService executorService,
                                      final String format) throws IOException {
        isNotNUll(source, "source");
        isNotNUll(executorService, "executorService");
        isNotNUll(format, "format");

        final BufferedImage read = ImageIO.read(source);

        final PipedInputStream pipedInputStream = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        executorService.submit(new Runnable() {

            public void run() {
                try {

                    ImageIO.write(read, format, pipedOutputStream);

                } catch (IOException e) {
                    logger.error("format image exception.", e);
                } finally {
                    try {
                        pipedInputStream.close();
                        pipedOutputStream.close();
                    } catch (IOException e) {
                        logger.error("format image exception.", e);
                    }

                }
            }
        });

        return pipedInputStream;
    }

    private static void isNotNUll(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Params " + paramName + " can't be null.");
        }
    }

    static class NamedThreadFactory implements ThreadFactory {

        private static final AtomicInteger POOL_SEQ   = new AtomicInteger(1);

        private final AtomicInteger        mThreadNum = new AtomicInteger(1);

        private final String               mPrefix;

        private final boolean              mDaemo;

        private final ThreadGroup          mGroup;

        public NamedThreadFactory(){
            this("pool-" + POOL_SEQ.getAndIncrement(), false);
        }

        public NamedThreadFactory(String prefix){
            this(prefix, false);
        }

        public NamedThreadFactory(String prefix, boolean daemo){
            mPrefix = prefix + "-thread-";
            mDaemo = daemo;
            SecurityManager s = System.getSecurityManager();
            mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        }

        public Thread newThread(Runnable runnable) {
            String name = mPrefix + mThreadNum.getAndIncrement();
            Thread ret = new Thread(mGroup, runnable, name, 0);
            ret.setDaemon(mDaemo);
            return ret;
        }

        public ThreadGroup getThreadGroup() {
            return mGroup;
        }
    }
}
