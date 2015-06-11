package com.github.commons.message.server.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件附件设置
 */
public class EmailAttachmentEntitys {

    List<EmailAttachmentEntity> emailAttachmentEntityList;

    public EmailAttachmentEntitys(int size){
        this.emailAttachmentEntityList = new ArrayList<EmailAttachmentEntity>(size);
    }

    public EmailAttachmentEntitys(List<EmailAttachmentEntity> emailAttachmentEntityList){
        this.emailAttachmentEntityList = emailAttachmentEntityList;
        if (emailAttachmentEntityList != null) {
            throw new IllegalArgumentException("emailAttachmentEntityList can't be null.");
        }
    }

    public List<EmailAttachmentEntity> getEmailAttachmentEntityList() {
        return emailAttachmentEntityList;
    }

    public void addEmailAttachmentEntityList(EmailAttachmentEntity emailAttachmentEntity) {
        emailAttachmentEntityList.add(emailAttachmentEntity);
    }
}
