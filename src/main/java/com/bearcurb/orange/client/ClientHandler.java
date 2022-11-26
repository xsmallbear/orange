package com.bearcurb.orange.client;


import com.bearcurb.orange.common.protocol.Procotol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Procotol> {
  private ChannelHandlerContext ctx;

  public void sendMessage(Procotol message) throws InterruptedException {
    while (ctx == null) {
    }
    System.out.println("发了一个消息");
    ctx.writeAndFlush(message);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.ctx = ctx;
//    OrangeProtocol message = new OrangeProtocol("hello", true, "", "");
//    sendMessage(message);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Procotol msg) throws Exception {
    System.out.println(msg.getService());
  }

//  @Override
//  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//    cause.printStackTrace();
//    ctx.close();
//  }
}
