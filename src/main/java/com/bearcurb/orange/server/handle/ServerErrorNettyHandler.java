package com.bearcurb.orange.server.handle;

import com.bearcurb.orange.common.logger.OrangeLogger;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;

import java.net.SocketException;

@Sharable
public class ServerErrorNettyHandler extends ChannelDuplexHandler {
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    if (cause instanceof CodecException) {
      OrangeLogger.getLogger().info("解码错误");
      cause.printStackTrace();
      ctx.close();
      return;
    }
    if (cause instanceof SocketException) {
      //突然断开连接
      OrangeLogger.getLogger().info("客户端异常断开连接");
      ctx.close();
      return;
    }
    OrangeLogger.getLogger().info("默认的异常处理" + cause);
  }
}
