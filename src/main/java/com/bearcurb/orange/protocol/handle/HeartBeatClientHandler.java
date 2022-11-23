package com.bearcurb.orange.protocol.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;

public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

  private final String PING = "PING";
  private final String PONG = "PONG";

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      System.out.println("向服务器发一个心跳");
      IdleState state = ((IdleStateEvent) evt).state();
      if (state == IdleState.WRITER_IDLE) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeCharSequence(PING, StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);
      } else {
        super.userEventTriggered(ctx, evt);
      }
      if (state == IdleState.READER_IDLE) {
        //服务器长时间没有响应哦
      }
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;
    byte[] heartBuf = new byte[4];
    for (int i = 0; i < 4 && buf.isReadable(); i++) {
      heartBuf[i] = buf.readByte();
    }
    if ((new String(heartBuf, "UTF-8")).equals(PONG)) {
      // YES
    } else {
      buf.resetReaderIndex();
      ctx.fireChannelRead(msg);
    }
  }
}
