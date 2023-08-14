package com.bearcurb.orange.client;


import com.bearcurb.orange.common.protocol.Protocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Protocol> {
  private ChannelHandlerContext ctx;

  public void sendMessage(Protocol message) throws InterruptedException {
    while (ctx == null) {
    }
    ctx.writeAndFlush(message);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.ctx = ctx;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Protocol msg) throws Exception {
    System.out.println(msg.getService());
  }
}
