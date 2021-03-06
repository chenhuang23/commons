/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.message.server.mail;

import com.github.commons.message.server.MessageException;
import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.HTML;
import java.io.File;

/**
 * MailSender.java
 *
 * @author zhouxiaofeng 3/31/15
 */
public class MailSender {

    private static final Logger logger  = LoggerFactory.getLogger(MailSender.class);
    public static final String  GB_2312 = "GB2312";

    private String              smtp;

    private String              username;

    private String              password;

    private int                 port;

    private boolean             isDebug;

    /**
     * 发送邮件 带附件
     *
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     * @return
     */
    public void sendHtmlWithAttachment(String from, String[] to, String[] cc, String[] bcc, String subject, String msg,
                                       EmailAttachmentEntity... attachments) throws MessageException {

        HtmlEmail email = new HtmlEmail();

        try {
            if (attachments != null) {
                for (EmailAttachmentEntity attachmentEntity : attachments) {

                    if (attachmentEntity != null) {
                        EmailAttachment ea = new EmailAttachment();
                        ea.setDescription(attachmentEntity.getDescription());
                        ea.setDisposition(attachmentEntity.getDisposition());
                        ea.setName(attachmentEntity.getName());
                        ea.setPath(attachmentEntity.getPath());
                        ea.setURL(attachmentEntity.getUrl());

                        email.attach(ea);
                    }
                }
            }
        } catch (EmailException e) {
            logger.error("attach file exception.", e);
        }

        sendHtmlReal(email, from, to, cc, bcc, subject, msg);
    }

    /**
     * 发送邮件 带附件
     *
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     * @return
     */
    public void sendHtmlWithAttachment(String from, String[] to, String[] cc, String[] bcc, String subject, String msg,
                                       File... files) throws MessageException {

        HtmlEmail email = new HtmlEmail();

        try {
            for (File file : files) {
                if (file != null) {
                    email.attach(file);
                }
            }
        } catch (EmailException e) {
            logger.error("attach file exception.", e);
        }

        sendHtmlReal(email, from, to, cc, bcc, subject, msg);
    }

    /**
     * 发送邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     * @return
     */
    public void sendHtml(String from, String[] to, String[] cc, String[] bcc, String subject, String msg)
                                                                                                         throws MessageException {

        HtmlEmail email = new HtmlEmail();
        sendHtmlReal(email, from, to, cc, bcc, subject, msg);
    }

    /**
     * 发送邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     * @return
     */
    private void sendHtmlReal(HtmlEmail email, String from, String[] to, String[] cc, String[] bcc, String subject,
                              String msg) throws MessageException {

        // email.setTLS(true); //是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
        // email.setSSL(true);
        email.setDebug(isDebug);

        email.setHostName(smtp);
        email.setSmtpPort(port);

        email.setAuthenticator(new DefaultAuthenticator(username, password));

        try {
            email.setFrom(from == null ? username : from); // 发送方,这里可以写多个
            if (to != null && to.length > 0) email.addTo(to); // 接收方
            if (cc != null && cc.length > 0) email.addCc(cc); // 抄送方
            if (bcc != null && bcc.length > 0) email.addBcc(bcc); // 秘密抄送方
            email.setSubject(subject); // 标题
            email.setMsg(msg);// 内容
            email.setCharset(GB_2312); // 默认
            email.send();
        } catch (Throwable e) {
            logger.error("Send mail exception.", e);
            throw new MessageException("Send mail exception ", e);
        }
    }

    /**
     * 发送邮件
     *
     * @param from
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param msg
     * @return
     */
    public void send(String from, String[] to, String[] cc, String[] bcc, String subject, String msg)
                                                                                                     throws MessageException {

        MultiPartEmail email = new MultiPartEmail();
        // email.setTLS(true); //是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
        // email.setSSL(true);
        email.setDebug(isDebug);

        email.setHostName(smtp);
        email.setSmtpPort(port);

        email.setAuthenticator(new DefaultAuthenticator(username, password));

        try {
            email.setFrom(from == null ? username : from); // 发送方,这里可以写多个
            if (to != null && to.length > 0) email.addTo(to); // 接收方
            if (cc != null && cc.length > 0) email.addCc(cc); // 抄送方
            if (bcc != null && bcc.length > 0) email.addBcc(bcc); // 秘密抄送方
            email.setSubject(subject); // 标题
            email.setMsg(msg);// 内容
            email.setCharset(GB_2312); // 默认
            email.send();
        } catch (Throwable e) {
            logger.error("Send mail exception.", e);
            throw new MessageException("Send mail exception ", e);
        }
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static void main(String[] args) {

        MailSender sender = new MailSender();

        sender.setDebug(true);
        sender.setPort(25);
        sender.setSmtp("service.netease.com");
        sender.setUsername("");
        sender.setPassword("");

        sender.send("", new String[] { "" }, new String[] {}, new String[] {}, "test", "test11111111");
    }
}
