package com.bearcurb.orange.server.handle;

import com.bearcurb.orange.common.exception.CodecException;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class ServerErrorNettyHandler extends ChannelDuplexHandler {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    if (cause instanceof CodecException) {
      System.out.println("是解码异常");
    }
  }
}
