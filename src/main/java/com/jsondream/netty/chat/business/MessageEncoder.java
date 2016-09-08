package com.jsondream.netty.chat.business;

import org.msgpack.MessagePack;

import com.jsondream.netty.chat.SerializeUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //		byte[] data = SerializeUtil.serialize(msg);
        MessagePack messagePack = new MessagePack();
        byte[] data = messagePack.write(msg);
        //        out.writeInt(data.length);
        out.writeBytes(data);
    }

}
