package com.github.commons.message.server.mail;

import com.github.commons.message.server.constants.EmailEnum;
import org.apache.commons.mail.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.commons.message.MessageChannel;
import com.github.commons.message.server.IMessageSender;
import com.github.commons.message.server.template.ResolvedEnvelop;

import java.io.File;
import java.util.List;

/**
 * Created by Yang Tengfei on 4/24/15.
 */
@Service
public class NeteaseMailSender implements IMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(NeteaseMailSender.class);

    @Autowired
    private MailSender          internalMailSender;

    @Override
    public MessageChannel getMessageChannel() {
        return MessageChannel.EMAIL;
    }

    @Override
    public boolean send(ResolvedEnvelop envelop) {

        try {

            if (envelop != null && envelop.getParameters() != null) {

                if (envelop.getParameters().containsKey(EmailEnum.__Attachment_ENTITY)) {

                    Object attachment = envelop.getParameters().get(EmailEnum.__Attachment_ENTITY);

                    if (attachment != null && attachment instanceof EmailAttachmentEntity) {

                        try {
                            internalMailSender.sendHtmlWithAttachment(null, envelop.getRecipients(), null, null,
                                                                      envelop.getTitle(), envelop.getContent(),
                                                                      (EmailAttachmentEntity) attachment);

                            return true;
                        } catch (Throwable throwable) {
                            logger.error("Send attachement Exception", throwable);
                            return false;
                        }
                    }

                } else if (envelop.getParameters().containsKey(EmailEnum.__Attachment_ENTITYS.getKey())) {

                    Object attachment = envelop.getParameters().get(EmailEnum.__Attachment_ENTITYS.getKey());

                    if (attachment != null && attachment instanceof EmailAttachmentEntitys) {

                        try {
                            internalMailSender.sendHtmlWithAttachment(null,
                                                                      envelop.getRecipients(),
                                                                      null,
                                                                      null,
                                                                      envelop.getTitle(),
                                                                      envelop.getContent(),
                                                                      ((EmailAttachmentEntitys) attachment).getEmailAttachmentEntityList().toArray(new EmailAttachmentEntity[((EmailAttachmentEntitys) attachment).getEmailAttachmentEntityList().size()]));

                            return true;
                        } catch (Throwable throwable) {
                            logger.error("Send attachement Exception", throwable);
                            return false;
                        }
                    }

                } else if (envelop.getParameters().containsKey(EmailEnum.__Attachment_FILE.getKey())) {

                    Object attachment = envelop.getParameters().get(EmailEnum.__Attachment_FILE.getKey());

                    if (attachment != null && attachment instanceof File) {
                        try {
                            internalMailSender.sendHtmlWithAttachment(null, envelop.getRecipients(), null, null,
                                                                      envelop.getTitle(), envelop.getContent(),
                                                                      (File) attachment);

                            return true;
                        } catch (Throwable throwable) {
                            logger.error("Send attachement Exception", throwable);
                            return false;
                        }
                    }

                } else if (envelop.getParameters().containsKey(EmailEnum.__Attachment_FILES.getKey())) {

                    Object attachment = envelop.getParameters().get(EmailEnum.__Attachment_FILES.getKey());

                    if (attachment != null && attachment instanceof List) {

                        try {
                            List<File> attachmentList = (List) attachment;

                            internalMailSender.sendHtmlWithAttachment(null,
                                                                      envelop.getRecipients(),
                                                                      null,
                                                                      null,
                                                                      envelop.getTitle(),
                                                                      envelop.getContent(),
                                                                      attachmentList.toArray(new File[attachmentList.size()]));

                            return true;

                        } catch (Throwable throwable) {
                            logger.error("Send attachement Exception", throwable);
                            return false;
                        }

                    }
                } else {
                    try {
                        internalMailSender.sendHtml(null, envelop.getRecipients(), null, null, envelop.getTitle(),
                                                    envelop.getContent());
                    } catch (Throwable throwable) {
                        logger.error("Send attachement Exception", throwable);
                        return false;
                    }
                }
            }

        } catch (Throwable e) {

            logger.error("Send mail exception.", e);

            return false;
        }
        return true;
    }

    public void setInternalMailSender(MailSender internalMailSender) {
        this.internalMailSender = internalMailSender;
    }
}
