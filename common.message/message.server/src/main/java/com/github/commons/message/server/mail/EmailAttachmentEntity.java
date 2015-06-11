package com.github.commons.message.server.mail;

import java.net.URL;

/**
 * 邮件附件设置
 */
public class EmailAttachmentEntity {

    /**
     * Definition of the part being an attachment.
     */
    public static final String ATTACHMENT  = javax.mail.Part.ATTACHMENT;

    /**
     * Definition of the part being inline.
     */
    public static final String INLINE      = javax.mail.Part.INLINE;

    /**
     * The name of this attachment.
     */
    private String             name        = "";

    /**
     * The description of this attachment.
     */
    private String             description = "";

    /**
     * The path to this attachment (ie c:/path/to/file.jpg).
     */
    private String             path        = "";

    /**
     * The HttpURI where the file can be got.
     */
    private URL                url;

    /**
     * The disposition.
     */
    private String             disposition = EmailAttachmentEntity.ATTACHMENT;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
