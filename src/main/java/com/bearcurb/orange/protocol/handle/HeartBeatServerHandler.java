package com.bearcurb.orange.protocol.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

  private final String PING = "PING";
  private final String PONG = "PONG";
  private int idleReaderTriggernumber = 0;

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
        // long time no request
        idleReaderTriggernumber++;
        if (idleReaderTriggernumber > 3) {
          ctx.close();
        }
      } else {
        super.userEventTriggered(ctx, evt);
      }
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    idleReaderTriggernumber = 0; //reset counter
    ByteBuf buf = (ByteBuf) msg;
    byte[] heartBuf = new byte[4];
    for (int i = 0; i < 4 && buf.isReadable(); i++) {
      heartBuf[i] = buf.readByte();
    }
    if ((new String(heartBuf, "UTF-8")).equals("PING")) {
      // send PONG
      ByteBuf pong = Unpooled.buffer();
      pong.writeCharSequence(PONG, StandardCharsets.UTF_8);
      ctx.writeAndFlush(pong);
    } else {
      buf.resetReaderIndex();
      ctx.fireChannelRead(msg);
    }
  }
}
