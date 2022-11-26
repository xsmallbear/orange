package com.bearcurb.orange.client.handle;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class ClientErrorNettyHandler extends ChannelDuplexHandler {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("异常到了最下游了 要捕获了");
    cause.printStackTrace();
  }
}
