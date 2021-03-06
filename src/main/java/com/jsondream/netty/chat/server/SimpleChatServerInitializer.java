package com.jsondream.netty.chat.server;

import com.jsondream.netty.chat.business.MessageDecoder;
import com.jsondream.netty.chat.business.MessageEncoder;
import com.jsondream.netty.chat.client.SimpleChatClientHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //        pipeline.addLast("decode", new StringDecoder());
        //pipeline.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,1,0,1));

        //pipeline.addLast("LengthFieldPrepender", new LengthFieldPrepender(1));
        //       pipeline.addLast("encode", new StringEncoder());
        //        pipeline.addLast("decoder", new FixedLengthFrameDecoder(20));
        //        pipeline.addLast("handler", new SimpleChatServerHandler());
        //        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");

        pipeline.addLast("HBeat", new IdleStateHandler(70, 60, 0));// 心跳
        pipeline.addLast("frameDecode", new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        pipeline.addLast("decode", new MessageDecoder());
        pipeline.addLast("FrameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("encode", new MessageEncoder());
        pipeline.addLast("handler", new SimpleChatServerHandler());
    }
}
