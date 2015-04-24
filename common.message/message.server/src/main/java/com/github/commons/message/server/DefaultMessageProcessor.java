package com.github.commons.message.server;

import com.github.commons.message.*;
import com.github.commons.message.server.template.ITemplateResolver;
import com.github.commons.message.server.template.ResolvedEnvelop;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yang Tengfei on 4/23/15.
 * <p/>
 * 默认的消息处理器
 */

@Service
public class DefaultMessageProcessor implements IMessageProcessor, InitializingBean {

    @Autowired
    private ITemplateResolver templateResolver;

    @Autowired
    private List<IMessageSender> messageSenders;

    private final Map<MessageChannel, IMessageSender> messageSenderMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        for (IMessageSender s : messageSenders)
            messageSenderMap.put(s.getMessageChannel(), s);
    }

    @Override
    public IMessageResponse process(IMessageRequest request) {

        if (StringUtils.isEmpty(request.getTemplateId())) {
            return new DefaultMessageResponse(201, "template id cannot be empty!");
        }

        if (request.getEnvelops() == null || request.getEnvelops().isEmpty())
            return new DefaultMessageResponse(202, "envelop cannot be empty!");

        for (Map.Entry<MessageChannel, IEnvelop> entry : request.getEnvelops().entrySet()) {
            getMessageSender(entry.getKey()).send(createEnvelop(entry.getKey(), entry.getValue()));
        }

        return DefaultMessageResponse.SUCCESS;
    }

    private ResolvedEnvelop createEnvelop(MessageChannel ch, IEnvelop request) {
        String message = templateResolver.resolve(ch, request.getTemplateId(), request.getParameters());

        return new ResolvedEnvelop(request, message);
    }

    private IMessageSender getMessageSender(MessageChannel ch) {
        return messageSenderMap.get(ch);
    }

}
