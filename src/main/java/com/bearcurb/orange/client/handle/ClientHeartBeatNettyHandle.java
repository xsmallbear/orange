package com.bearcurb.orange.client.handle;

import com.bearcurb.orange.client.util.ClientProtocolGenerator;
import com.bearcurb.orange.common.protocol.Procotol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHeartBeatNettyHandle extends SimpleChannelInboundHandler<Procotol> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Procotol msg) throws Exception {
    if (msg.getEvent() != Procotol.EventType.HEART) {
      ctx.fireChannelRead(msg);
      return;
    }
    System.out.println("客户端收到心跳回复");
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      System.out.println("向服务器发一个心跳");
      IdleState state = ((IdleStateEvent) evt).state();
      if (state == IdleState.WRITER_IDLE) {
        //发送心跳回复
        Procotol protocol = ClientProtocolGenerator.getSimpleResultProtocol();
        protocol.setEvent(Procotol.EventType.HEART);
        ctx.writeAndFlush(protocol);
      } else {
        super.userEventTriggered(ctx, evt);
      }
      if (state == IdleState.READER_IDLE) {
        //服务器长时间没有响应哦
      }
    }
  }
}
