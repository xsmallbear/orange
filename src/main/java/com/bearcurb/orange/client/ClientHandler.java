package com.bearcurb.orange.client;


import com.bearcurb.orange.protocol.OrangeRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<OrangeRequest> {
  private ChannelHandlerContext ctx;

  public void sendMessage(OrangeRequest message) throws InterruptedException {
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
  protected void channelRead0(ChannelHandlerContext ctx, OrangeRequest msg) throws Exception {
    System.out.println(msg.getServiceName());
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    System.out.println("客户端已经五秒没有受到服务器的消息了");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
