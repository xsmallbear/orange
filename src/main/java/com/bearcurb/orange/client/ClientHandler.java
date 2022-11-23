package com.bearcurb.orange.client;


import com.bearcurb.orange.protocol.NewProcotol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<NewProcotol> {
  private ChannelHandlerContext ctx;

  public void sendMessage(NewProcotol message) throws InterruptedException {
    if (ctx != null) {
      ctx.writeAndFlush(message);
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.ctx = ctx;
//    OrangeProtocol message = new OrangeProtocol("hello", true, "", "");
//    sendMessage(message);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, NewProcotol msg) throws Exception {
    System.out.println(msg.getService());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
