package com.bearcurb.orange.common;

import com.bearcurb.orange.common.protocol.OrangeProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

public class OrangeSplitPackageHandler extends DelimiterBasedFrameDecoder {


  public OrangeSplitPackageHandler(int maxFrameLength) {
    super(maxFrameLength, true, Unpooled.copiedBuffer(OrangeProtocolCodec.splitFlag.getBytes(StandardCharsets.US_ASCII)));
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
