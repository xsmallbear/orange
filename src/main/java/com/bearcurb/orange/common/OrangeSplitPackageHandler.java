package com.bearcurb.orange.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class OrangeSplitPackageHandler extends DelimiterBasedFrameDecoder {

  public OrangeSplitPackageHandler(int maxFrameLength, ByteBuf delimiter) {
    super(maxFrameLength, true, delimiter);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) {
    try {
      return super.decode(ctx, buffer);
    } catch (Exception e) {
      ctx.fireExceptionCaught(e);
    }
    return null;
  }
}
