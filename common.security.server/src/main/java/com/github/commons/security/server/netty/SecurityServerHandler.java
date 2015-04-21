/**
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */
package com.github.commons.security.server.netty;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.commons.security.support.remote.RemoteReq;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.commons.utils.format.JsonUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class SecurityServerHandler extends SimpleChannelInboundHandler<String> {

    private ConcurrentHashMap<String /* client address */, ChannelHandlerContext> channels = new ConcurrentHashMap<String, ChannelHandlerContext>();

    private static final Logger                                                   logger   = LoggerFactory.getLogger(SecurityServerHandler.class);

    private final Charset                                                         charset  = Charset.forName("UTF-8");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress() + " 连接到服务器。");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        logger.info(ctx.channel().remoteAddress() + " 断开连接。");
    }

    private void sendMessage(ChannelHandlerContext ctx, String config) {
        byte[] bytes = config.getBytes(charset);
        ByteBuf message = Unpooled.buffer(4 + bytes.length);
        message.writeInt(bytes.length);
        message.writeBytes(bytes);
        ctx.writeAndFlush(message);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String request) throws Exception {

        if (StringUtils.isNotBlank(request)) {

            RemoteReq remoteReq = JsonUtils.fromJson(request, RemoteReq.class);

            if (remoteReq == null) {

            }

        }

        String config;
        if (request != null && request.startsWith("secReq=")) {
            request = request.substring("superdiamond=".length());

            Map<String, String> params = (Map<String, String>) JSONUtils.parse(request);
            String projCode = params.get("projCode");
            String modules = params.get("modules");
            String[] moduleArr = StringUtils.split(modules, ",");
            String profile = params.get("profile");
            ClientKey key = new ClientKey();
            key.setProjCode(projCode);
            key.setProfile(profile);
            key.setModuleArr(moduleArr);
            // String version = params.get("version");

            List<ClientInfo> addrs = clients.get(key);
            if (addrs == null) {
                addrs = new ArrayList<ClientInfo>();
            }

            String clientAddr = ctx.channel().remoteAddress().toString();
            ClientInfo clientInfo = new ClientInfo(clientAddr, new Date());
            addrs.add(clientInfo);
            clients.put(key, addrs);
            channels.put(clientAddr, ctx);

            if (StringUtils.isNotBlank(modules)) {
                config = configService.queryConfigs(projCode, moduleArr, profile, "");
            } else {
                config = configService.queryConfigs(projCode, profile, "");
            }
        } else {
            config = "";
        }

        sendMessage(ctx, config);

    }

}
